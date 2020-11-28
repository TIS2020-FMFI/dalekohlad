package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.Pair;

import java.util.Map;

public class Filterwheel implements GUIModule {
    private Pane pane;
    private Label info;

    public void init(Pane p) {
        pane = p;
        info = (Label) GUIModule.GetById(pane, "filter1");

        info.setText("...");
        ((ComboBox)GUIModule.GetById(pane, "Filterwheel")).setOnAction(actionEvent -> SetFilter());
        SetColors(((ComboBox)GUIModule.GetById(pane, "Filterwheel")));
    }

    public void SetColors(ComboBox comboBox){
        comboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>(){
            @Override public ListCell<String> call(ListView<String> p){
                return new ListCell<>() {
                    private final Rectangle rectangle = new Rectangle(10, 10);

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if(item == null || empty){
                            setGraphic(null);
                        }else{
                            switch(item) {
                                case "Filter B" : rectangle.setFill(Color.BLUE); break;
                                case "Filter V" : rectangle.setFill(Color.GREEN); break;
                                case "Filter R" : rectangle.setFill(Color.RED); break;
                                case "Filter I" : rectangle.setFill(Color.ORANGE); break;
                                case "Filter C" : rectangle.setFill(Color.GRAY); break;
                            }
                            setGraphic(rectangle);
                            setText(item);
                        }
                    }
                };
            }
        });
    }

    public void SetFilter(){
        ComboBox choiceBox = (ComboBox) GUIModule.GetById(pane, "Filterwheel");
        String data = (String)choiceBox.getValue();

        SetFilter(data.split(" ")[1]);
    }

    public void SetFilter(String s){
        switch(s){
            case "B": Communication.send_data(String.valueOf(67));
            case "V": Communication.send_data(String.valueOf(66));
            case "R": Communication.send_data(String.valueOf(86));
            case "I": Communication.send_data(String.valueOf(82));
            case "C": Communication.send_data(String.valueOf(73));
        }
        System.out.println("Set Filter: " + s);
    }

    public void update(JsonObject jo) {
        if(jo.get("FWFilter") != null) Platform.runLater(() -> info.setText(jo.get("FWFilter").getAsString()));
    }

    @Override
    public void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {
        // B - filter B
        Pair<Boolean, KeyCode> filter_B = new Pair<>(true, KeyCode.B);
        shortcuts.put(filter_B, () -> SetFilter("B"));
        // V - filter B
        Pair<Boolean, KeyCode> filter_V = new Pair<>(true, KeyCode.V);
        shortcuts.put(filter_V, () -> SetFilter("V"));
        // R - filter B
        Pair<Boolean, KeyCode> filter_R = new Pair<>(true, KeyCode.R);
        shortcuts.put(filter_R, () -> SetFilter("R"));
        // I - filter B
        Pair<Boolean, KeyCode> filter_I = new Pair<>(true, KeyCode.I);
        shortcuts.put(filter_I, () -> SetFilter("I"));
        // C - filter B
        Pair<Boolean, KeyCode> filter_C = new Pair<>(true, KeyCode.C);
        shortcuts.put(filter_C, () -> SetFilter("C"));
    }
}