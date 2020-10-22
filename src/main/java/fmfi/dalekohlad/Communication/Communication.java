package fmfi.dalekohlad.Communication;

import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import fmfi.dalekohlad.Modules.GUIModule;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Communication {
    private static final Logger lgr = LogManager.getLogger(Communication.class);
    private static Socket sock = null;
    private static ArrayList<GUIModule> modules = null;

    private static void periodic_update() {
        // caka na data zo serveru a posuva ich vsetkym modulom
        String data = "";

        for (GUIModule module: modules) {
            module.update(data);
        }
    }

    public static void init(InetSocketAddress host, ArrayList<GUIModule> modules) {
        Communication.modules = modules;
        lgr.debug(String.format("Connecting to %s:%d", host.getAddress(), host.getPort()));
        try {
            sock = new Socket(host.getAddress(), host.getPort());
        }
        catch (Exception e) {
            // TODO retry?
            lgr.error(String.format("Failed to initialize connection to %s:%d", host.getAddress(), host.getPort()), e);
            System.exit(4);
        }
        Runnable runnable = Communication::periodic_update;
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public static void send_data() {
        // zbiera data z modulov a odosiela ich na server, mal by byt volany tlacitkom send
        String data = "";
        for (GUIModule module: modules) {
            data = data.concat(module.collect_data());
        }
    }

}
