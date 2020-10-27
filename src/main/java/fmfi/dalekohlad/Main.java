package fmfi.dalekohlad;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import fmfi.dalekohlad.Communication.Communication;
import fmfi.dalekohlad.LockInstance.LockInstance;
import fmfi.dalekohlad.Modules.GUIModule;
import fmfi.dalekohlad.InputHandling.Key;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {
    private static final Logger lgr = LogManager.getLogger(Main.class);
    private static final String main_screen = "/fxml/main_screen.fxml";
    private static final String module_prefix = "module_";
    private static final String config = "/config.json";

    @Override
    public void start(Stage stage) {
        LockInstance.lock_instance();
        load_scene(stage);
        ArrayList<GUIModule> modules = bind_modules(stage);
        InetSocketAddress host = load_host_from_config();
        Communication.init(host, modules);
    }

    public static void main() {
        launch();
    }

    private void load_scene(Stage stage) {
        Scene scene = null;
        try {
            scene = FXMLLoader.load(getClass().getResource(main_screen));
        }
        catch (Exception e) {
            lgr.fatal("Unable to load FXML resource: " + main_screen, e);
            System.exit(3);
        }
        stage.setScene(scene);
        stage.show();
        lgr.debug("Loaded FXML " + main_screen);
    }

    private InetSocketAddress load_host_from_config() {
        String address = null;
        short port = 0;
        try {
            URL url = getClass().getResource(config);
            Path config_path = Paths.get(url.toURI());
            JsonReader reader = new JsonReader(new FileReader(config_path.toString()));
            JsonObject jo = JsonParser.parseReader(reader).getAsJsonObject();
            address = jo.getAsJsonPrimitive("address").getAsString();
            port = jo.getAsJsonPrimitive("port").getAsShort();
        }
        catch (Exception e) {
            lgr.fatal("Failed to load network config", e);
            System.exit(2);
        }
        lgr.debug("Loaded network config");
        return new InetSocketAddress(address, port);
    }

    private ArrayList<GUIModule> bind_modules(Stage stage) {
        // initializes and collects correct modules
        ArrayList<GUIModule> modules = new ArrayList<>();
        HashMap<Pair<Boolean, KeyCode>, Runnable> shortcuts = new HashMap<>();
        Scene scene = stage.getScene();
        Parent root = scene.getRoot();
        for (Node node: root.getChildrenUnmodifiable()) {
            if (!(node instanceof Pane)) {
                continue;
            }
            Pane pane = (Pane) node;
            String id = pane.getId();
            if (!id.startsWith(module_prefix)) {
                continue;
            }
            String module_name = id.substring(module_prefix.length());
            String package_name = Main.class.getPackageName();
            String module_path = package_name + ".Modules." + module_name;
            try {
                GUIModule module = (GUIModule) Class.forName(module_path).getConstructor().newInstance();
                module.init(pane);
                module.registerShortcuts(shortcuts);
                modules.add(module);
                lgr.debug("Loaded module: " + module_name);
            }
            catch (Exception e) {
                lgr.error("Failed to load module " + module_name, e);
            }
        }
        stage.getScene().setOnKeyPressed(new Key<KeyEvent>(shortcuts));
        return modules;
    }

}
