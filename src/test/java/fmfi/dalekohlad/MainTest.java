package fmfi.dalekohlad;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class MainTest extends ApplicationTest {
    private FxRobot robot = new FxRobot();
    private Boolean big_f_set = false;
    private Boolean lava_sipka_set = false;
    private static final String main_screen = "/fxml/main_screen.fxml";

    @Override
    public void start(Stage stage) throws IOException {
        HashMap<Pair<Boolean, KeyCode>, Runnable> shortcuts = new HashMap<>();

        // velke f shortcut
        Pair<Boolean, KeyCode> shortcut_id_velke_f = new Pair<>(true, KeyCode.F);
        shortcuts.put(shortcut_id_velke_f, () -> big_f_set = true);

        // lava sipka shortcut
        Pair<Boolean, KeyCode> shortcut_id_left = new Pair<>(false, KeyCode.LEFT);
        shortcuts.put(shortcut_id_left, () -> lava_sipka_set = true);

        Scene scene = FXMLLoader.load(getClass().getResource(main_screen));
        stage.setScene(scene);
        stage.show();

        Main.registerShortcuts(stage, shortcuts);
    }

    @Test
    public void velke_f() {
        big_f_set = false;
        robot.press(KeyCode.SHIFT);
        robot.push(KeyCode.F);
        robot.release(KeyCode.SHIFT);
        assertTrue(big_f_set);
    }

    @Test
    public void lava_sipka() {
        lava_sipka_set = false;
        robot.push(KeyCode.LEFT);
        assertTrue(lava_sipka_set);
    }
}