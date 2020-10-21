package fmfi.dalekohlad;

import fmfi.dalekohlad.LockInstance.LockInstance;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main extends Application {
    private static final Logger lgr = LogManager.getLogger(Main.class);
    private static final String main_screen = "/fxml/main_screen.fxml";

    @Override
    public void start(Stage stage) {
        LockInstance.lock_instance();
        load_scene(stage);
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

}
