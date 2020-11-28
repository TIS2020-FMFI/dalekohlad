package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
import fmfi.dalekohlad.InputHandling.InputConfirmation;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Map;

public class Dome implements GUIModule {
    private Pane pane;
    private Label[] info;

    public void init(Pane p) {
        pane = p;
        info = new Label[5];

        for(int i = 0; i < 5; i++){
            info[i] = (Label) GUIModule.GetById(pane, "dome" + (i+1));
            info[i].setText("...");
        }

        ((Button)GUIModule.GetById(pane,"Frequency")).setOnAction(actionEvent -> Frequency());
        ((Button)GUIModule.GetById(pane,"CalibrateAzimuth")).setOnAction(actionEvent -> CalibrateAzimuth());
        ((Button)GUIModule.GetById(pane,"DomeWest")).setOnAction(actionEvent -> DomeWest());
        ((Button)GUIModule.GetById(pane,"DomeStop")).setOnAction(actionEvent -> DomeStop());
        ((Button)GUIModule.GetById(pane,"DomeEast")).setOnAction(actionEvent -> DomeEast());
        ((Button)GUIModule.GetById(pane,"Synchronize")).setOnAction(actionEvent -> Synchronize());
    }

    public void Frequency() {
        TextField frequency = ((TextField)GUIModule.GetById(pane,"FrequencyField"));

        try{
            double value = Double.parseDouble(frequency.getText());
            Communication.send_data(102 + ";" + value);
        }
        catch(Exception e){ InputConfirmation.warn(e.getMessage()); }

        System.out.println("Frequency: " + frequency.getText());
        frequency.setText("");
    }

    public void CalibrateAzimuth() {
        TextField calibrate_azimuth = ((TextField)GUIModule.GetById(pane,"CalibrateAzimuthField"));

        try{
            // To Do - skontrolovať uhol
            double value = Double.parseDouble(calibrate_azimuth.getText());
            Communication.send_data(97 + ";" + value);
        }
        catch(Exception e){ InputConfirmation.warn(e.getMessage()); }

        System.out.println("Calibrate azimuth: " + calibrate_azimuth.getText());
        calibrate_azimuth.setText("");
    }

    public void DomeWest(){
        Communication.send_data(String.valueOf(297));
        System.out.println("Dome West");
    }

    public void DomeStop(){
        Communication.send_data(String.valueOf(295));
        System.out.println("Dome Stop");
    }

    public void DomeEast(){
        Communication.send_data(String.valueOf(306));
        System.out.println("Dome East");
    }

    public void Synchronize(){
        Communication.send_data(String.valueOf(121));
        System.out.println("Synchronize");
    }

    public void update(JsonObject jo){
        if(jo.get("DOMEEncoder") != null) Platform.runLater(() -> info[0].setText(jo.get("DOMEEncoder").getAsString()));
        if(jo.get("DOMEAzimuth") != null) Platform.runLater(() -> info[1].setText(jo.get("DOMEAzimuth").getAsString()));
        if(jo.get("DOMETargetAzimuth") != null) Platform.runLater(() -> info[2].setText(jo.get("DOMETargetAzimuth").getAsString()));
        if(jo.get("DOMESynch") != null) Platform.runLater(() -> info[3].setText(jo.get("DOMESynch").getAsString()));
        if(jo.get("DOMEStatus") != null) Platform.runLater(() -> info[4].setText(jo.get("DOMEStatus").getAsString()));
    }

    @Override
    public void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {
        // f - frequency  (0 - 60)
        Pair<Boolean, KeyCode> frequency = new Pair<>(false, KeyCode.F);
        shortcuts.put(frequency, () -> FocusTextField(false,"FrequencyField", pane));
        // a - azimuth
        Pair<Boolean, KeyCode> calibrate_azimuth = new Pair<>(false, KeyCode.A);
        shortcuts.put(calibrate_azimuth, () -> FocusTextField(false,"CalibrateAzimuthField", pane));
        // y - synchronize
        Pair<Boolean, KeyCode> synchronize = new Pair<>(false, KeyCode.Y);
        shortcuts.put(synchronize, this::Synchronize);

        // Insert - dome east
        Pair<Boolean, KeyCode> dome_east = new Pair<>(false, KeyCode.INSERT);
        shortcuts.put(dome_east, this::DomeEast);
        // Page Up - dome west
        Pair<Boolean, KeyCode> dome_west = new Pair<>(false, KeyCode.PAGE_UP);
        shortcuts.put(dome_west, this::DomeWest);
        // Home - dome stop
        Pair<Boolean, KeyCode> dome_stop = new Pair<>(false, KeyCode.HOME);
        shortcuts.put(dome_stop, this::DomeStop);
    }
}
