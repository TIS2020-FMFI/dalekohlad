package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Map;

public class Target implements GUIModule {
    private Pane pane;

    public void init(Pane p) {
        pane = p;
        ((Button)GetById(pane,"LoadTarget")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LoadTarget();
            }
        });
        ((Button)GetById(pane,"GoToCancel")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GoToCancel();
            }
        });
        ((Button)GetById(pane,"PoleCrossing")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                PoleCrossing();
            }
        });
    }

    public void update(JsonObject jo) {

    }

    public void LoadTarget(){
        String data = ((TextField)GetById(pane,"LoadTargetRA")).getText();
        data += ", " + ((TextField)GetById(pane,"LoadTargetDE")).getText();

        System.out.println("Load Target: " + data);
        //Communication.send_data("Prikaz123 25");
    }

    public void GoToCancel(){
        System.out.println("Go To / Cancel");
        //Communication.send_data("GoToCancel");
    }

    public void PoleCrossing(){
        System.out.println("Pole Crossing");
        //Communication.send_data("PoleCrossing");
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