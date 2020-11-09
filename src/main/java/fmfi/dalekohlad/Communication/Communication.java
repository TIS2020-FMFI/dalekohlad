package fmfi.dalekohlad.Communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fmfi.dalekohlad.Modules.GUIModule;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Communication {
    private static final Logger lgr = LogManager.getLogger(Communication.class);
    private static Socket sock = null;
    private static DataOutputStream client_out;
    private static DataInputStream client_in;

    private static ArrayList<GUIModule> modules = null;

    private static void periodic_update() {
        // caka na data zo serveru a posuva ich vsetkym modulom
        String data = null;
        while (true) {
            boolean read = false;
            while (!read) {
                try {
                    data = client_in.readUTF();
                    read = true;
                }
                catch (Exception e) {
                    lgr.error("Failed to read data, repeating...");
                    try { TimeUnit.SECONDS.sleep(1); } catch (Exception f) {}
                }
            }
            JsonObject json_object = JsonParser.parseString(data).getAsJsonObject();
            modules.forEach(x -> x.update(json_object));
        }
    }

    public static void init(InetSocketAddress host, ArrayList<GUIModule> modules) {
        Communication.modules = modules;
        lgr.debug(String.format("Connecting to %s:%d", host.getAddress(), host.getPort()));
        try {
            sock = new Socket(host.getAddress(), host.getPort());
            client_out = new DataOutputStream(sock.getOutputStream());
            client_in = new DataInputStream(sock.getInputStream());
        }
        catch (Exception e) {
            lgr.fatal(String.format("Failed to initialize connection to %s:%d", host.getAddress(), host.getPort()), e);
            System.exit(4);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                sock.close();
            } catch (Exception e) {
                lgr.error("Unable to close connection: ", e);
            }
        }));
        Runnable runnable = Communication::periodic_update;
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public static void send_data(String data) {
        // kazdy modul odosiela informacie ked bude potrebovat
        boolean sent = false;
        while (!sent) {
            try {
                client_out.writeUTF(data);
                client_out.flush();
                sent = true;
            } catch (Exception e) {
                lgr.error("Failed to send data, repeating...");
                try { TimeUnit.SECONDS.sleep(1); } catch (Exception f) {}
            }
        }
    }

}
