package fmfi.dalekohlad.Communication;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import fmfi.dalekohlad.Modules.GUIModule;
import fmfi.dalekohlad.Operations;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Communication {
    private static final Logger lgr = LogManager.getLogger(Communication.class);
    private static Socket sock = null;
    private static OutputStreamWriter client_out;
    private static BufferedReader client_in;
    private static InetSocketAddress host;

    private static ArrayList<GUIModule> modules = null;
    private static boolean run = true;
    private static final int DEFAULT_BUF_LEN = 51200;

    private static String readString() throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] tmp = new char[DEFAULT_BUF_LEN];
        int sz = client_in.read(tmp, 0, DEFAULT_BUF_LEN);
        if (sz == -1) {
            // -1 is returned when the other side exits unexpectedly, we'll treat this case in the same way as a timeout
            throw new SocketTimeoutException();
        }
        for (int i = 0; i < sz; i++) {
            sb.append(tmp[i]);
        }
        return sb.toString();
    }

    private static void sleep(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        }
        catch (Exception e) {
            lgr.debug("Failed to sleep", e);
        }
    }

    private static void periodicUpdate() {
        // caka na data zo serveru a posuva ich vsetkym modulom
        String data = null;
        lgr.debug("Entering read loop");
        while (run) {
            boolean read = false;
            while (!read && run) {
                try {
                    data = readString();
                    read = true;
                }
                catch (SocketTimeoutException e) {
                    reconnect();
                }
                catch (Exception e) {
                    lgr.error("Failed to read data, repeating...");
                    sleep(1000);
                }
            }
            if (read) {
                if (data.equals("")) {
                    continue;
                }
                if (data.startsWith("{")) {
                    JsonReader reader = new JsonReader(new StringReader(data.trim()));
                    reader.setLenient(true);
                    JsonObject json_object = JsonParser.parseReader(reader).getAsJsonObject();
                    modules.forEach(x -> x.update(json_object));
                }
                else {
                    Operations.add(data);
                }
            }
        }
    }

    private static void reconnect() {
        lgr.debug("Trying to reconnect");
        closeSock();
        connect();
    }

    private static void connect() {
        boolean success = false;
        while (!success) {
            lgr.debug(String.format("Connecting to %s:%d", host.getAddress(), host.getPort()));
            try {
                sock = new Socket();
                sock.connect(host, 2000);
                client_out = new OutputStreamWriter(sock.getOutputStream());
                client_in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                success = true;
            } catch (Exception e) {
                lgr.error(String.format("Connection attempt %s:%d failed, repeating",
                        host.getAddress(), host.getPort()), e);
                sleep(2000);
            }
        }
    }

    public static Thread init(InetSocketAddress host, ArrayList<GUIModule> modules) {
        Communication.modules = modules;
        Communication.host = host;
        connect();
        Runtime.getRuntime().addShutdownHook(new Thread(Communication::close));
        Runnable runnable = Communication::periodicUpdate;
        Thread periodic_thread = new Thread(runnable);
        periodic_thread.start();
        return periodic_thread;
    }

    public static void sendData(String data) {
        // kazdy modul odosiela informacie ked bude potrebovat
        boolean sent = false;
        while (!sent) {
            try {
                client_out.write(data);
                client_out.flush();
                sent = true;
            } catch (Exception e) {
                lgr.error("Failed to send data, repeating...");
                sleep(2500);
            }
        }
    }

    private static void closeSock() {
        try {
            sock.close();
        }
        catch (Exception e) {
            lgr.error("Failed to close socket", e);
        }
    }

    public static void close() {
        if (run) {
            run = false;
            closeSock();
        }
    }

}
