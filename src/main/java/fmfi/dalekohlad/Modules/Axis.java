package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
import fmfi.dalekohlad.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import java.lang.reflect.Method;
import java.util.Map;
import fmfi.dalekohlad.InputHandling.InputConfirmation;


public class Axis implements GUIModule {
    private Pane pane;
    private Label[] info_polar;
    private Label[] info_declination;

    public void init(Pane p) {
        pane = p;
        info_polar = new Label[5];
        info_declination = new Label[5];

        for(int i = 0; i < 5; i++){
            info_polar[i] = (Label) GUIModule.GetById(pane, "polar" + (i+1));
            info_declination[i] = (Label) GUIModule.GetById(pane, "declination" + (i+1));
            info_polar[i].setText("...");
            info_declination[i].setText("...");
        }

       ((Button)GUIModule.GetById(pane,"EnableDisableMotors")).setOnAction(actionEvent -> EnableDisableMotors());
       ((Button)GUIModule.GetById(pane,"StopRA")).setOnAction(actionEvent -> StopRA());
       ((Button)GUIModule.GetById(pane,"StopDE")).setOnAction(actionEvent -> StopDE());
       ((Button)GUIModule.GetById(pane,"StopRAandDE")).setOnAction(actionEvent -> StopRAandDE());
       ((Button)GUIModule.GetById(pane,"Calibrate")).setOnAction(actionEvent -> Calibrate());
       ((Button)GUIModule.GetById(pane,"Correction")).setOnAction(actionEvent -> Correction());
       ((Button)GUIModule.GetById(pane,"SlewRA")).setOnAction(actionEvent -> SlewRA());
       ((Button)GUIModule.GetById(pane,"SlewDE")).setOnAction(actionEvent -> SlewDE());
       //((Button)GUIModule.GetById(pane,"GoTo")).setOnAction(actionEvent -> GoTo());
    }

    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void EnableDisableMotors() {
        Button button = (Button) GUIModule.GetById(pane, "EnableDisableMotors");
        if(button.getText().equals("Enable Motors")) {
            Platform.runLater(() -> {button.setText("Disable Motors");});
            Communication.send_data(String.valueOf(91));
        }
        else {
            Platform.runLater(() -> {button.setText("Enable Motors");});
            Communication.send_data(String.valueOf(93));
        }
        System.out.println("Enable/Disable motors");
    }

    public void StopRA(){
        Communication.send_data(String.valueOf(87));
        System.out.println("Stop RA");
    }

    public void StopDE(){
        Communication.send_data(String.valueOf(119));
        System.out.println("Stop DE");
    }

    public void StopRAandDE(){
        Communication.send_data(String.valueOf(87));
        Communication.send_data(String.valueOf(119));
        System.out.println("Stop RA and DE");
    }

    public void Calibrate(){
        Communication.send_data(String.valueOf(99));
        System.out.println("Calibrate");
    }

    public void  Correction(){
        // TO DOOOO ???
        System.out.println("Correction");
    }

    public void SlewRA() {
        TextField slew_ra = ((TextField)GUIModule.GetById(pane,"SlewRAField"));
        String slew_ra_text = slew_ra.getText();

        if(isInteger(slew_ra_text)) {
            int input_value = Integer.parseInt(slew_ra_text);
            if(input_value >= 0) Communication.send_data(77+";"+input_value);
            else Communication.send_data(75+";"+(input_value*-1));
        }
        else {
            InputConfirmation.warn("Data was entered incorrectly!");
        }

        System.out.println("Slew RA: " + slew_ra.getText());
        slew_ra.setText("");
    }

    public void SlewDE() {
        TextField slew_de = ((TextField)GUIModule.GetById(pane,"SlewDEField"));
        String slew_de_text = slew_de.getText();

        if(isInteger(slew_de_text)) {
            int input_value = Integer.parseInt(slew_de_text);
            if(input_value >= 0) Communication.send_data(72+";"+input_value);
            else Communication.send_data(80+";"+(input_value*-1));
        }
        else {
            InputConfirmation.warn("Data was entered incorrectly!");
        }

        System.out.println("Slew DE: " + slew_de.getText());
        slew_de.setText("");
    }

    public void update(JsonObject jo) {
        if(jo.get("PAEncoder") != null) {
            Platform.runLater(() -> {
                info_polar[0].setText(jo.get("PAEncoder").getAsString());
            });
        }
        if(jo.get("PAHAApparent") != null) {
            Platform.runLater(() -> {
                info_polar[1].setText(jo.get("PAHAApparent").getAsString());
            });
        }
        if(jo.get("PAHARAJ2000") != null) {
            Platform.runLater(() -> {
                info_polar[2].setText(jo.get("PAHARAJ2000").getAsString());
            });
        }
        if(jo.get("PAAzimuth") != null) {
            Platform.runLater(() -> {
                info_polar[3].setText(jo.get("PAAzimuth").getAsString());
            });
        }
        if(jo.get("PAStatus") != null) {
            Platform.runLater(() -> {
                info_polar[4].setText(jo.get("PAStatus").getAsString());
            });
        }
        if(jo.get("DEEncoder") != null) {
            Platform.runLater(() -> {
                info_declination[0].setText(jo.get("DEEncoder").getAsString());
            });
        }
        if(jo.get("DEApparent") != null) {
            Platform.runLater(() -> {
                info_declination[1].setText(jo.get("DEApparent").getAsString());
            });
        }
        if(jo.get("DEDEJ2000") != null) {
            Platform.runLater(() -> {
                info_declination[2].setText(jo.get("DEDEJ2000").getAsString());
            });
        }
        if(jo.get("DEElevation") != null) {
            Platform.runLater(() -> {
                info_declination[3].setText(jo.get("DEElevation").getAsString());
            });
        }
        if(jo.get("DEStatus") != null) {
            Platform.runLater(() -> {
                info_declination[4].setText(jo.get("DEStatus").getAsString());
            });
        }
    }

    @Override
    public void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {
        // Left - slew east
        Pair<Boolean, KeyCode> slew_east = new Pair<>(false, KeyCode.LEFT);
        shortcuts.put(slew_east, () -> FocusTextField(false,"SlewRAField", pane));
        //Right - slew west
        Pair<Boolean, KeyCode> slew_west = new Pair<>(false, KeyCode.RIGHT);
        shortcuts.put(slew_west, () -> FocusTextField(false,"SlewRAField", pane));
        // Up - slew up
        Pair<Boolean, KeyCode> slew_up = new Pair<>(false, KeyCode.UP);
        shortcuts.put(slew_up, () -> FocusTextField(false,"SlewDEField", pane));
        //Down - slew down
        Pair<Boolean, KeyCode> slew_down = new Pair<>(false, KeyCode.DOWN);
        shortcuts.put(slew_down, () -> FocusTextField(false,"SlewDEField", pane));

        // PageDown - stop RA
        Pair<Boolean, KeyCode> stop_ra = new Pair<>(false, KeyCode.PAGE_DOWN);
        shortcuts.put(stop_ra, this::StopRA);
        // Delete - stop DE
        Pair<Boolean, KeyCode> stop_de = new Pair<>(false, KeyCode.DELETE);
        shortcuts.put(stop_de, this::StopDE);
        // End - stop RA and DE
        Pair<Boolean, KeyCode> stop_ra_and_de = new Pair<>(false, KeyCode.END);
        shortcuts.put(stop_ra_and_de, this::StopRAandDE);
        // [ - enable motors
        Pair<Boolean, KeyCode> enable_motors = new Pair<>(false, KeyCode.OPEN_BRACKET);
        shortcuts.put(enable_motors, this::EnableDisableMotors);
        // ] - enable motors
        Pair<Boolean, KeyCode> disable_motors = new Pair<>(false, KeyCode.CLOSE_BRACKET);
        shortcuts.put(disable_motors, this::EnableDisableMotors);

        // c - calibrate
        Pair<Boolean, KeyCode> calibrate = new Pair<>(false, KeyCode.C);
        shortcuts.put(calibrate, this::Calibrate);
        // T - correction
        Pair<Boolean, KeyCode> correction = new Pair<>(true, KeyCode.T);
        shortcuts.put(correction, this::Correction);
    }
}