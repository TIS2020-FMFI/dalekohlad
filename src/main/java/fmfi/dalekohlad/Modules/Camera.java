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

    public void update(JsonObject jo) {

    }

    @Override
    public void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {
        // O - observer
        Pair<Boolean, KeyCode> observer = new Pair<>(true, KeyCode.O);
        shortcuts.put(observer, () -> FocusTextField(true,"ObserverField", pane));
        // o - object
        Pair<Boolean, KeyCode> object = new Pair<>(false, KeyCode.O);
        shortcuts.put(object, () -> FocusTextField(true,"ObjectField", pane));
        // n - notes
        Pair<Boolean, KeyCode> notes = new Pair<>(false, KeyCode.N);
        shortcuts.put(notes, () -> FocusTextField(true,"NotesField", pane));

        // e - exposure time
        Pair<Boolean, KeyCode> exposure_time = new Pair<>(false, KeyCode.E);
        shortcuts.put(exposure_time, () -> FocusTextField(false,"ExposureTimeField", pane));
        // s - cooler setpoint
        Pair<Boolean, KeyCode> cooler_setpoint = new Pair<>(false, KeyCode.S);
        shortcuts.put(cooler_setpoint, () -> FocusTextField(false,"CoolerSetPointField", pane));
        // d - exposure delay
        Pair<Boolean, KeyCode> exposure_delay = new Pair<>(false, KeyCode.D);
        shortcuts.put(exposure_delay, () -> FocusTextField(false,"ExposureDelayField", pane));
        // r - sequence repeats
        Pair<Boolean, KeyCode> sequence_repeats = new Pair<>(false, KeyCode.R);
        shortcuts.put(sequence_repeats, () -> FocusTextField(false,"SequenceRepeatsField", pane));

        // m - camera mode
        Pair<Boolean, KeyCode> camera_mode = new Pair<>(false, KeyCode.M);
        shortcuts.put(camera_mode, this::CameraMode);
        // t - image type
        Pair<Boolean, KeyCode> image_type = new Pair<>(false, KeyCode.T);
        shortcuts.put(image_type, this::ImageType);

        // E - turn on camera
        Pair<Boolean, KeyCode> turn_on_camera = new Pair<>(true, KeyCode.E);
        shortcuts.put(turn_on_camera, this::TurnCameraOn);
        // X
        // A
    }
}