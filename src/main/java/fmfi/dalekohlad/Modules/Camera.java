package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Map;

public class Camera implements GUIModule {
    private Pane pane;
    private Label[] info;

    public void init(Pane p) {
        pane = p;
        info = new Label[17];

        for(int i = 0; i < 17; i++){
            info[i] = (Label) GetById(pane, "camera" + (i+1));
            info[i].setText("...");
        }

        ((ChoiceBox)GetById(pane, "ImageType")).setOnAction(actionEvent -> ImageType());
        ((ChoiceBox)GetById(pane, "CameraMode")).setOnAction(actionEvent -> CameraMode());
        ((Button)GetById(pane,"ExposureTime")).setOnAction(actionEvent -> ExposureTime());
        ((Button)GetById(pane,"CoolerSetpoint")).setOnAction(actionEvent -> CoolerSetPoint());
        ((Button)GetById(pane,"ExposureDelay")).setOnAction(actionEvent -> ExposureDelay());
        ((Button)GetById(pane,"SequenceRepeats")).setOnAction(actionEvent -> SequenceRepeats());
        ((Button)GetById(pane,"Observer")).setOnAction(actionEvent -> Observer());
        ((Button)GetById(pane,"Object")).setOnAction(actionEvent -> Object());
        ((Button)GetById(pane,"Notes")).setOnAction(actionEvent -> Notes());
        ((Button)GetById(pane,"TurnCameraOn")).setOnAction(actionEvent -> TurnCameraOn());
    }

    public void update(JsonObject jo) {

    }

    public void ImageType(){
        ChoiceBox choiceBox = (ChoiceBox)GetById(pane, "ImageType");
        String data = (String) choiceBox.getValue();
        System.out.println("Set image type: " + data);
        //Communication.send_data("Prikaz123 25");
    }

    public void CameraMode(){
        ChoiceBox choiceBox = (ChoiceBox)GetById(pane, "CameraMode");
        String data = (String) choiceBox.getValue();
        System.out.println("Set camera mode: " + data);
        //Communication.send_data("Prikaz123 25");
    }

    public void ExposureTime(){
        TextField exposure_time = ((TextField)GetById(pane,"ExposureTimeField"));
        System.out.println("Exposure time: " + exposure_time.getText());
        //Communication.send_data("Prikaz123 25");
        exposure_time.setText("");
    }

    public void CoolerSetPoint(){
        TextField cooler_setpoint = ((TextField)GetById(pane,"CoolerSetPointField"));
        System.out.println("Cooler setpoint: " + cooler_setpoint.getText());
        //Communication.send_data("Prikaz123 25");
        cooler_setpoint.setText("");
    }

    public void ExposureDelay(){
        TextField exposure_delay = ((TextField)GetById(pane,"ExposureDelayField"));
        System.out.println("Exposure delay: " + exposure_delay.getText());
        //Communication.send_data("Prikaz123 25");
        exposure_delay.setText("");
    }

    public void SequenceRepeats(){
        TextField sequence_repeats = ((TextField)GetById(pane,"SequenceRepeatsField"));
        System.out.println("Sequence repeats: " + sequence_repeats.getText());
        //Communication.send_data("Prikaz123 25");
        sequence_repeats.setText("");
    }

    public void Observer(){
        TextArea observer = ((TextArea)GetById(pane,"ObserverField"));
        System.out.println("Observer: " + observer.getText());
        //Communication.send_data("Prikaz123 25");
        observer.setText("");
    }

    public void Object(){
        TextArea object = ((TextArea)GetById(pane,"ObjectField"));
        System.out.println("Object: " + object.getText());
        //Communication.send_data("Prikaz123 25");
        object.setText("");
    }

    public void Notes(){
        TextArea notes = ((TextArea)GetById(pane,"NotesField"));
        System.out.println("Notes: " + notes.getText());
        //Communication.send_data("Prikaz123 25");
        notes.setText("");
    }

    public void TurnCameraOn(){
        System.out.println("Turn Camera On");
        //Communication.send_data("TurnCameraOn");
    }

    @Override
    public void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {
        Pair<Boolean, KeyCode> shortcut_id = new Pair<>(false, KeyCode.N);
        shortcuts.put(shortcut_id, () -> FocusTextField(true,"NotesField", pane));
    }
}