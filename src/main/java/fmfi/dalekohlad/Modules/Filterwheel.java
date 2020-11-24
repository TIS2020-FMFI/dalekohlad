package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Map;

public class Filterwheel implements GUIModule {
    private Pane pane;
    private Label info;

    public void init(Pane p) {
        pane = p;
        info = (Label) GetById(pane, "filter1");

        info.setText("...");
        ((ChoiceBox)GetById(pane, "Filterwheel")).setOnAction(actionEvent -> SetFilter());
    }

    public void SetFilter(){
        ChoiceBox choiceBox = (ChoiceBox)GetById(pane, "Filterwheel");
        String data = (String) choiceBox.getValue();
        System.out.println("Set Filter: " + data);
        //Communication.send_data("Prikaz123 25");
    }

    public void SetFilter(String s){
        System.out.println("Set Filter: " + s);
        //Communication.send_data("Prikaz123 25");
    }

    public void update(JsonObject jo) {

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