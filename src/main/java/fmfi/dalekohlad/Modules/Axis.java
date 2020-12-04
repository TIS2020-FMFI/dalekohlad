package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import fmfi.dalekohlad.InputHandling.InputConfirmation;


public class Axis implements GUIModule {
    private Pane pane;
    private Label[] info_polar;
    private Label[] info_declination;

    private final int ENABLE_MOTORS_CODE = 91;
    private final int DISABLE_MOTORS_CODE = 93;
    private final int STOP_RA_CODE = 87;
    private final int STOP_DE_CODE = 119;
    private final int CALIBRATION_CODE = 99;
    private final int SLEW_RA_POSITIVE_CODE = 77;
    private final int SLEW_DE_POSITIVE_CODE = 72;
    private final int SLEW_RA_NEGATIVE_CODE = 75;
    private final int SLEW_DE_NEGATIVE_CODE = 80;

    private final List<String> INFO_NAMES_POLAR = List.of("PAEncoder","PAHAApparent","PAHARAJ2000","PAAzimuth","PAStatus");
    private final List<String> INFO_NAMES_DECLINATION = List.of("DEEncoder","DEApparent","DEDEJ2000","DEElevation","DEStatus");

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
    }

    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int integerValue = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void EnableDisableMotors() {
        Button button = (Button) GUIModule.GetById(pane, "EnableDisableMotors");
        if(button.getText().equals("Enable Motors")) {
            Platform.runLater(() -> {button.setText("Disable Motors");});
            Communication.sendData(String.valueOf(ENABLE_MOTORS_CODE));
        }
        else {
            Platform.runLater(() -> {button.setText("Enable Motors");});
            Communication.sendData(String.valueOf(DISABLE_MOTORS_CODE));
        }
    }

    public void StopRA(){
        Communication.sendData(String.valueOf(STOP_RA_CODE));
    }

    public void StopDE(){
        Communication.sendData(String.valueOf(STOP_DE_CODE));
    }

    public void StopRAandDE(){
        Communication.sendData(String.valueOf(STOP_RA_CODE));
        Communication.sendData(String.valueOf(STOP_DE_CODE));
    }

    public void Calibrate(){
        Communication.sendData(String.valueOf(CALIBRATION_CODE));
    }

    public void  Correction(){
        // TO DOOOO
        System.out.println("Correction");
    }

    public void SlewRA() {
        TextField slew_ra = ((TextField)GUIModule.GetById(pane,"SlewRAField"));
        String slew_ra_text = slew_ra.getText();

        if(isInteger(slew_ra_text)) {
            int input_value = Integer.parseInt(slew_ra_text);
            if(input_value >= 0) Communication.sendData(SLEW_RA_POSITIVE_CODE+";"+input_value);
            else Communication.sendData(SLEW_RA_NEGATIVE_CODE+";"+(input_value*-1));
        }
        else {
            InputConfirmation.warn("Data was entered incorrectly!");
        }

        slew_ra.setText("");
    }

    public void SlewDE() {
        TextField slew_de = ((TextField)GUIModule.GetById(pane,"SlewDEField"));
        String slew_de_text = slew_de.getText();

        if(isInteger(slew_de_text)) {
            int input_value = Integer.parseInt(slew_de_text);
            if(input_value >= 0) Communication.sendData(SLEW_DE_POSITIVE_CODE+";"+input_value);
            else Communication.sendData(SLEW_DE_NEGATIVE_CODE+";"+(input_value*-1));
        }
        else {
            InputConfirmation.warn("Data was entered incorrectly!");
        }

        slew_de.setText("");
    }

    public void update(JsonObject jo) {
        for(int i = 0; i < 5;i++) {
            int finalI = i;
            if(jo.get(INFO_NAMES_POLAR.get(i)) != null) {
                Platform.runLater(() -> {info_polar[finalI].setText(jo.get(INFO_NAMES_POLAR.get(finalI)).getAsString());});
            }
            if(jo.get(INFO_NAMES_DECLINATION.get(i)) != null) {
                Platform.runLater(() -> {info_declination[finalI].setText(jo.get(INFO_NAMES_DECLINATION.get(finalI)).getAsString());});
            }
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