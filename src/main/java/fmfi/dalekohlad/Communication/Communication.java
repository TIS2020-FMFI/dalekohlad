package fmfi.dalekohlad.Communication;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fmfi.dalekohlad.Modules.GUIModule;
import fmfi.dalekohlad.Mediator;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Communication {
    private static final Logger lgr = LogManager.getLogger(Communication.class);
    private static Socket sock = null;
    private static OutputStreamWriter client_out;
    private static BufferedReader client_in;

    private static ArrayList<GUIModule> modules = null;
    private static boolean run = true;
    private static final int DEFAULT_BUF_LEN = 51200;

    private static String readString() throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] tmp = new char[DEFAULT_BUF_LEN];
        int sz = client_in.read(tmp, 0, DEFAULT_BUF_LEN);
        for (int i = 0; i < sz; i++) {
            sb.append(tmp[i]);
        }
        return sb.toString();
    }

    private static void periodic_update() {
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
                catch (Exception e) {
                    lgr.error("Failed to read data, repeating...");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    }
                    catch (Exception f) {
                        lgr.debug("Failed to sleep", e);
                    }
                }
            }
            if (read) {
                if (data.equals("")) {
                    continue;
                }
                if (data.startsWith("{")) {
                    JsonObject json_object = JsonParser.parseString(data).getAsJsonObject();
                    modules.forEach(x -> x.update(json_object));
                }
                else {
                    lgr.debug(data);
                }
            }
        }
    }

    public static Thread init(InetSocketAddress host, ArrayList<GUIModule> modules) {
        Communication.modules = modules;
        lgr.debug(String.format("Connecting to %s:%d", host.getAddress(), host.getPort()));
        try {
            sock = new Socket(host.getAddress(), host.getPort());
            client_out = new OutputStreamWriter(sock.getOutputStream());
            client_in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        }
        catch (Exception e) {
            lgr.fatal(String.format("Failed to initialize connection to %s:%d", host.getAddress(), host.getPort()), e);
            System.exit(Mediator.EXIT_CONNECTION_INITIALIZATION_ERROR);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(Communication::close));
        Runnable runnable = Communication::periodic_update;
        Thread periodic_thread = new Thread(runnable);
        periodic_thread.start();
        return periodic_thread;
    }

    public static void send_data(String data) {
        // kazdy modul odosiela informacie ked bude potrebovat
        boolean sent = false;
        while (!sent) {
            try {
                client_out.write(data);
                client_out.flush();
                sent = true;
            } catch (Exception e) {
                lgr.error("Failed to send data, repeating...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                }
                catch (Exception f) {
                    lgr.debug("Failed to sleep", e);
                }
            }
        }
    }

    public static void close() {
        if (run) {
            run = false;
            try {
                sock.close();
            }
            catch (Exception e) {
                lgr.fatal("Failed to close socket, forcing exit", e);
                System.exit(Mediator.EXIT_SOCKET_CLOSE_ERROR);
            }
        }
    }

}
