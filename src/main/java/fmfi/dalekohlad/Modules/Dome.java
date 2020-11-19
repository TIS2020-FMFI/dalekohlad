package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
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
            info[i] = (Label) GetById(pane, "dome" + (i+1));
            info[i].setText("...");
        }

        ((Button)GetById(pane,"Frequency")).setOnAction(actionEvent -> Frequency());
        ((Button)GetById(pane,"CalibrateAzimuth")).setOnAction(actionEvent -> Frequency());
        ((Button)GetById(pane,"DomeWest")).setOnAction(actionEvent -> DomeWest());
        ((Button)GetById(pane,"DomeStop")).setOnAction(actionEvent -> DomeStop());
        ((Button)GetById(pane,"DomeEast")).setOnAction(actionEvent -> DomeEast());
        ((Button)GetById(pane,"Synchronize")).setOnAction(actionEvent -> Synchronize());
    }

    public void Frequency() {
        TextField frequency = ((TextField)GetById(pane,"FrequencyField"));
        System.out.println("Frequency: " + frequency.getText());
        //Communication.send_data("Prikaz123 25");
        frequency.setText("");
    }

    public void CalibrateAzimuth() {
        TextField calibrate_azimuth = ((TextField)GetById(pane,"CalibrateAzimuthField"));
        System.out.println("Calibrate azimuth: " + calibrate_azimuth.getText());
        //Communication.send_data("Prikaz123 25");
        calibrate_azimuth.setText("");
    }

    public void DomeWest(){
        System.out.println("Dome West");
        //Communication.send_data("Dome West");
    }

    public void DomeStop(){
        System.out.println("Dome Stop");
        //Communication.send_data("Dome Stop");
    }

    public void DomeEast(){
        System.out.println("Dome East");
        //Communication.send_data("Dome East");
    }

    public void Synchronize(){
        System.out.println("Synchronize");
        //Communication.send_data("Synchronize");
    }

    public void update(JsonObject jo) {

    }

    @Override
    public void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {
        // f - pouzivatel zadava double hodnotu, nastavuje sa rýchlosť kupoly (0 - 60)
        Pair<Boolean, KeyCode> frequency = new Pair<>(false, KeyCode.F);
        shortcuts.put(frequency, () -> FocusTextField(false,"FrequencyField", pane));
        // a -
        Pair<Boolean, KeyCode> calibrate_azimuth = new Pair<>(false, KeyCode.A);
        shortcuts.put(calibrate_azimuth, () -> FocusTextField(false,"CalibrateAzimuthField", pane));

    }
}
