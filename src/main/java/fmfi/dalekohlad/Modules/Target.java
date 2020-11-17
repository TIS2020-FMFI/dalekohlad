package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Map;

public class Target implements GUIModule {
    private Pane pane;
    private Label[] info;

    public void init(Pane p) {
        pane = p;
        info = new Label[10];

        for(int i = 0; i < 10; i++){
            info[i] = (Label) GetById(pane, "target" + (i+1));
            info[i].setText("...");
        }

        ((Button)GetById(pane,"LoadTarget")).setOnAction(actionEvent -> LoadTarget());
        ((Button)GetById(pane,"GoToCancel")).setOnAction(actionEvent -> GoToCancel());
        ((Button)GetById(pane,"PoleCrossing")).setOnAction(actionEvent -> PoleCrossing());
    }

    public void update(JsonObject jo) {

    }

    public void LoadTarget(){
        TextField ra = ((TextField)GetById(pane,"LoadTargetRA"));
        TextField de = ((TextField)GetById(pane,"LoadTargetDE"));

        System.out.println("Load Target: " + ra.getText() + ", " + de.getText());
        //Communication.send_data("Prikaz123 25");
        ra.setText("");
        de.setText("");
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