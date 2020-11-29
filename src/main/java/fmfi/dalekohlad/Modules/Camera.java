package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
import fmfi.dalekohlad.InputHandling.InputConfirmation;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Camera implements GUIModule {
    private Pane pane;
    private HashMap<String, Label> info;

    public void init(Pane p) {
        pane = p;
        info = new HashMap<>();

        for(String s:new String[]{
                "CAMType","CAMExposure","CAMMode","CAMRBIFlushCount","CAMRBIFloodTime","CAMTDIMode","CAMBGFlush",
                "CAMBinning","CAMSubframe1","CAMSubframe2","CAMObserver","CAMObject","CAMNotes","CAMSetpoint",
                "CAMCooler1","CAMCooler2","CAMDelay","CAMRemaining1","CAMRemaining2","CAMStatus"}){
            info.put(s, (Label) GUIModule.GetById(pane, s));
            info.get(s).setText("...");
        }

        ((ChoiceBox) Objects.requireNonNull(GUIModule.GetById(pane, "ImageTypeChoiceBox"))).setOnAction(actionEvent -> ImageType());
        ((ChoiceBox) Objects.requireNonNull(GUIModule.GetById(pane, "CameraModeChoiceBox"))).setOnAction(actionEvent -> CameraMode());
        ((Button) Objects.requireNonNull(GUIModule.GetById(pane, "ExposureTimeButton"))).setOnAction(actionEvent -> ExposureTime());
        ((Button) Objects.requireNonNull(GUIModule.GetById(pane, "CoolerSetpointButton"))).setOnAction(actionEvent -> CoolerSetPoint());
        ((Button) Objects.requireNonNull(GUIModule.GetById(pane, "ExposureDelayButton"))).setOnAction(actionEvent -> ExposureDelay());
        ((Button) Objects.requireNonNull(GUIModule.GetById(pane, "SequenceRepeatsButton"))).setOnAction(actionEvent -> SequenceRepeats());
        ((Button) Objects.requireNonNull(GUIModule.GetById(pane, "ObserverButton"))).setOnAction(actionEvent -> Observer());
        ((Button) Objects.requireNonNull(GUIModule.GetById(pane, "ObjectButton"))).setOnAction(actionEvent -> Object());
        ((Button) Objects.requireNonNull(GUIModule.GetById(pane, "NotesButton"))).setOnAction(actionEvent -> Notes());
        ((Button) Objects.requireNonNull(GUIModule.GetById(pane, "TurnCameraOn"))).setOnAction(actionEvent -> TurnCameraOn());

        ((TextField) Objects.requireNonNull(GUIModule.GetById(pane, "ExposureTimeField"))).setOnAction(actionEvent -> ExposureTime());
        ((TextField) Objects.requireNonNull(GUIModule.GetById(pane, "CoolerSetPointField"))).setOnAction(actionEvent -> CoolerSetPoint());
        ((TextField) Objects.requireNonNull(GUIModule.GetById(pane, "ExposureDelayField"))).setOnAction(actionEvent -> ExposureDelay());
        ((TextField) Objects.requireNonNull(GUIModule.GetById(pane, "SequenceRepeatsField"))).setOnAction(actionEvent -> SequenceRepeats());
    }

    public void ImageType(){
        ChoiceBox choiceBox = (ChoiceBox)GUIModule.GetById(pane, "ImageTypeChoiceBox");
        String data = (String) choiceBox.getValue();

        int actual = choiceBox.getItems().indexOf(info.get("ImageType").getText());
        int wanted = choiceBox.getItems().indexOf(choiceBox.getValue());
        int num = wanted - actual;
        if(num < 0) num += 3;
        if(actual == -1) num = wanted;
        for(int i = 0; i < num; i++) Communication.send_data(String.valueOf(116));

        System.out.println("Set image type: " + data);
    }

    public void CameraMode(){
        ChoiceBox choiceBox = (ChoiceBox)GUIModule.GetById(pane, "CameraModeChoiceBox");
        String data = (String) choiceBox.getValue();

        int actual = choiceBox.getItems().indexOf(info.get("CameraMode").getText());
        int wanted = choiceBox.getItems().indexOf(choiceBox.getValue());
        int num = wanted - actual;
        if(num < 0) num += 3;
        if(actual == -1) num = wanted;
        for(int i = 0; i < num; i++) Communication.send_data(String.valueOf(109));

        System.out.println("Set camera mode: " + data);
    }

    public void ExposureTime(){
        TextField exposure_time = ((TextField)GUIModule.GetById(pane,"ExposureTimeField"));
        try{
            double value = Double.parseDouble(exposure_time.getText());
            Communication.send_data(101 + ";" + value);
        }
        catch(Exception e){ InputConfirmation.warn(e.getMessage()); }

        System.out.println("Exposure time: " + exposure_time.getText());
        exposure_time.setText("");
    }

    public void CoolerSetPoint(){
        TextField cooler_setpoint = ((TextField)GUIModule.GetById(pane,"CoolerSetPointField"));
        try{
            double value = Double.parseDouble(cooler_setpoint.getText());
            Communication.send_data(115 + ";" + value);
        }
        catch(Exception e){ InputConfirmation.warn(e.getMessage()); }

        System.out.println("Cooler setpoint: " + cooler_setpoint.getText());
        cooler_setpoint.setText("");
    }

    public void ExposureDelay(){
        TextField exposure_delay = ((TextField)GUIModule.GetById(pane,"ExposureDelayField"));
        try{
            double value = Double.parseDouble(exposure_delay.getText());
            Communication.send_data(100 + ";" + value);
        }
        catch(Exception e){ InputConfirmation.warn(e.getMessage()); }

        System.out.println("Exposure delay: " + exposure_delay.getText());
        exposure_delay.setText("");
    }

    public void SequenceRepeats(){
        TextField sequence_repeats = ((TextField)GUIModule.GetById(pane,"SequenceRepeatsField"));
        try{
            double value = Integer.parseInt(sequence_repeats.getText());
            Communication.send_data(114 + ";" + value);
        }
        catch(Exception e){ InputConfirmation.warn(e.getMessage()); }

        System.out.println("Sequence repeats: " + sequence_repeats.getText());
        sequence_repeats.setText("");
    }

    public void Observer(){
        TextArea observer = ((TextArea)GUIModule.GetById(pane,"ObserverField"));
        Communication.send_data(79 + ";" + observer.getText());
        System.out.println("Observer: " + observer.getText());
        observer.setText("");
    }

    public void Object(){
        TextArea object = ((TextArea)GUIModule.GetById(pane,"ObjectField"));
        Communication.send_data(111 + ";" + object.getText());
        System.out.println("Object: " + object.getText());
        object.setText("");
    }

    public void Notes(){
        TextArea notes = ((TextArea)GUIModule.GetById(pane,"NotesField"));
        Communication.send_data(110 + ";" + notes.getText());
        System.out.println("Notes: " + notes.getText());
        notes.setText("");
    }

    public void TurnCameraOn(){
        Communication.send_data(String.valueOf(69));
        System.out.println("Turn Camera On");
    }

    public void update(JsonObject jo){
        for(String s:info.keySet()){
            if(jo.get(s) != null) Platform.runLater(() -> info.get(s).setText(jo.get(s).getAsString()));
        }
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