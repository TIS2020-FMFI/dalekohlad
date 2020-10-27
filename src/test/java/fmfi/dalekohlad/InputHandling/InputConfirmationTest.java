package fmfi.dalekohlad.InputHandling;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.testfx.util.WaitForAsyncUtils.asyncFx;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class InputConfirmationTest extends ApplicationTest {
    private FxRobot robot = new FxRobot();

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new StackPane(), 100, 100));
        stage.show();
    }

    @Test
    public void confirm() throws ExecutionException, InterruptedException {
        Callable cal = () -> InputConfirmation.confirm("Fungujem?", "Zasadna otazka");
        Future<Boolean> future = asyncFx(cal);
        waitForFxEvents();
        robot.clickOn("OK");
        assert (future.get() == true);
    }
}