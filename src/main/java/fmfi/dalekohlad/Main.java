package fmfi.dalekohlad;

import fmfi.dalekohlad.LockInstance.LockInstance;
import fmfi.dalekohlad.Modules.GUIModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Main extends Application {
    private static final Logger lgr = LogManager.getLogger(Main.class);
    private static final String main_screen = "/fxml/main_screen.fxml";
    private static final String module_prefix = "module_";

    @Override
    public void start(Stage stage) {
        LockInstance.lock_instance();
        load_scene(stage);
        ArrayList<GUIModule> modules = bind_modules(stage);

    }

    public static void main() {
        launch();
    }

    private void load_scene(Stage stage) {
        Scene scene;
        try {
            scene = FXMLLoader.load(getClass().getResource(main_screen));
        }
        catch (Exception e) {
            lgr.fatal("Unable to load FXML resource: " + main_screen, e);
            return;
        }
        stage.setScene(scene);
        stage.show();
        lgr.debug("Loaded FXML " + main_screen);
    }

    private ArrayList<GUIModule> bind_modules(Stage stage) {
        // initializes and collects correct modules
        ArrayList<GUIModule> modules = new ArrayList<>();
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
            Object module_obj;
            Class module_class;
            try {
                module_class = Class.forName(module_path);
                Method get_instance = module_class.getDeclaredMethod("getInstance");
                module_obj = get_instance.invoke(module_class);
                GUIModule module = (GUIModule) module_class.cast(module_obj);
                module.init(pane);
                modules.add(module);
                lgr.debug("Loaded module: " + module_name);
            }
            catch (Exception e) {
                lgr.error("Failed to load module " + module_name, e);
            }
        }
        return modules;
    }

}
