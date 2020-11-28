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

import java.util.Map;

public class Camera implements GUIModule {
    private Pane pane;
    private Label[] info;

    public void init(Pane p) {
        pane = p;
        info = new Label[17];

        for(int i = 0; i < 17; i++){
            info[i] = (Label) GUIModule.GetById(pane, "camera" + (i+1));
            info[i].setText("...");
        }

        ((ChoiceBox)GUIModule.GetById(pane, "ImageType")).setOnAction(actionEvent -> ImageType());
        ((ChoiceBox)GUIModule.GetById(pane, "CameraMode")).setOnAction(actionEvent -> CameraMode());
        ((Button)GUIModule.GetById(pane,"ExposureTime")).setOnAction(actionEvent -> ExposureTime());
        ((Button)GUIModule.GetById(pane,"CoolerSetpoint")).setOnAction(actionEvent -> CoolerSetPoint());
        ((Button)GUIModule.GetById(pane,"ExposureDelay")).setOnAction(actionEvent -> ExposureDelay());
        ((Button)GUIModule.GetById(pane,"SequenceRepeats")).setOnAction(actionEvent -> SequenceRepeats());
        ((Button)GUIModule.GetById(pane,"Observer")).setOnAction(actionEvent -> Observer());
        ((Button)GUIModule.GetById(pane,"Object")).setOnAction(actionEvent -> Object());
        ((Button)GUIModule.GetById(pane,"Notes")).setOnAction(actionEvent -> Notes());
        ((Button)GUIModule.GetById(pane,"TurnCameraOn")).setOnAction(actionEvent -> TurnCameraOn());
    }

    public void ImageType(){
        ChoiceBox choiceBox = (ChoiceBox)GUIModule.GetById(pane, "ImageType");
        String data = (String) choiceBox.getValue();

        int actual = choiceBox.getItems().indexOf(info[0].getText());
        int wanted = choiceBox.getItems().indexOf(choiceBox.getValue());
        int num = wanted - actual;
        if(num < 0) num += 3;
        if(actual == -1) num = wanted;
        for(int i = 0; i < num; i++) Communication.send_data(String.valueOf(116));

        System.out.println("Set image type: " + data);
    }

    public void CameraMode(){
        ChoiceBox choiceBox = (ChoiceBox)GUIModule.GetById(pane, "CameraMode");
        String data = (String) choiceBox.getValue();

        int actual = choiceBox.getItems().indexOf(info[2].getText());
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
        if(jo.get("CAMType") != null) Platform.runLater(() -> info[0].setText(jo.get("CAMType").getAsString()));
        if(jo.get("CAMExposure") != null) Platform.runLater(() -> info[1].setText(jo.get("CAMExposure").getAsString()));
        if(jo.get("CAMMode") != null) Platform.runLater(() -> info[2].setText(jo.get("CAMMode").getAsString()));
        if(jo.get("CAMRBIFlushCount") != null) Platform.runLater(() -> info[3].setText(jo.get("CAMRBIFlushCount").getAsString()));
        if(jo.get("CAMRBIFloodTime") != null) Platform.runLater(() -> info[4].setText(jo.get("CAMRBIFloodTime").getAsString()));
        if(jo.get("CAMTDIMode") != null) Platform.runLater(() -> info[5].setText(jo.get("CAMTDIMode").getAsString()));
        if(jo.get("CAMBGFlush") != null) Platform.runLater(() -> info[6].setText(jo.get("CAMBGFlush").getAsString()));
        if(jo.get("CAMBinning") != null) Platform.runLater(() -> info[7].setText(jo.get("CAMBinning").getAsString()));
        if(jo.get("CAMSubframe1") != null) Platform.runLater(() -> info[8].setText(jo.get("CAMSubframe1").getAsString()));
        if(jo.get("CAMObserver") != null) Platform.runLater(() -> info[9].setText(jo.get("CAMObserver").getAsString()));
        if(jo.get("CAMObject") != null) Platform.runLater(() -> info[10].setText(jo.get("CAMObject").getAsString()));
        if(jo.get("CAMNotes") != null) Platform.runLater(() -> info[11].setText(jo.get("CAMNotes").getAsString()));
        if(jo.get("CAMSetpoint") != null) Platform.runLater(() -> info[12].setText(jo.get("CAMSetpoint").getAsString()));
        if(jo.get("CAMCooler1") != null) Platform.runLater(() -> info[13].setText(jo.get("CAMCooler1").getAsString()));
        if(jo.get("CAMDelay") != null) Platform.runLater(() -> info[14].setText(jo.get("CAMDelay").getAsString()));
        if(jo.get("CAMRemaining1") != null) Platform.runLater(() -> info[15].setText(jo.get("CAMRemaining1").getAsString()));
        if(jo.get("CAMStatus") != null) Platform.runLater(() -> info[16].setText(jo.get("CAMStatus").getAsString()));
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