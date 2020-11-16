package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Map;

public class Filterwheel implements GUIModule {
    private Pane pane;

    public void init(Pane p) {
        pane = p;
        ((ChoiceBox)GetById(pane, "Filterwheel")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SetFilter();
            }
        });
    }

    public void update(JsonObject jo) {

    }

    public void SetFilter(){
        ChoiceBox choiceBox = (ChoiceBox)GetById(pane, "Filterwheel");
        String data = (String) choiceBox.getValue();

        System.out.println("Set Filter: " + data);
        //Communication.send_data("Prikaz123 25");
    }

    @Override
    public void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {

    }

    Node GetById(Pane pane, String id){
        for(Node i:pane.getChildren()){
            if(i.getId() != null && i.getId().equals(id)) return i;
        }
        return null;
    }
}