package fmfi.dalekohlad.Communication;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Modules.GUIModule;
import javafx.scene.layout.Pane;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class CommunicationTest {
    private static final int PORT = 8887;
    private static DataOutputStream serverOut;
    private static DataInputStream serverIn;

    private void listen(ServerSocket server) {
        new Thread(() -> {
            try {
                Socket socket = server.accept();
                System.out.println("Incoming connection: " + socket);

                serverOut = new DataOutputStream(socket.getOutputStream());
                serverIn = new DataInputStream(socket.getInputStream());

                serverOut.writeUTF("{ \"name\":\"John\", \"age\":30, \"car\":null }");
                String received = serverIn.readUTF();
                System.out.println(received);
                assert received.equals("Prikaz123 25");
                server.close();
                Communication.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private class MyModule implements GUIModule {
        private Pane pane;

        public void init(Pane p) {
            pane = p;
        }

        public void update(JsonObject jo) {
            String name = jo.get("name").getAsString();
            System.out.println(name);
            assert name.equals("John");
            Communication.sendData("Prikaz123 25");
        }
    }

    @Test
    public void testClientServer() throws IOException, InterruptedException {
        ServerSocket server = new ServerSocket(PORT);
        listen(server);

        MyModule my_module = new MyModule();
        ArrayList<GUIModule> modules = new ArrayList<>();
        modules.add(my_module);
        Thread thread = Communication.init(new InetSocketAddress("localhost", PORT), modules);
        thread.join();
    }
}