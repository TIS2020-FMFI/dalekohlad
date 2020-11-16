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

public class Dome implements GUIModule {
    private Pane pane;

    public void init(Pane p) {
        pane = p;
        ((Button)GetById(pane,"Frequency")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Frequency();
            }
        });
        ((Button)GetById(pane,"CalibrateAzimuth")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Frequency();
            }
        });
        ((Button)GetById(pane,"DomeWest")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DomeWest();
            }
        });
        ((Button)GetById(pane,"DomeStop")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DomeStop();
            }
        });
        ((Button)GetById(pane,"DomeEast")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DomeEast();
            }
        });
        ((Button)GetById(pane,"Synchronize")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Synchronize();
            }
        });
    }

    public void update(JsonObject jo) {

    }

    public void Frequency() {
        String data = ((TextField)GetById(pane,"FrequencyField")).getText();

        System.out.println("Frequency: " + data);
        //Communication.send_data("Prikaz123 25");
    }

    public void CalibrateAzimuth() {
        String data = ((TextField)GetById(pane,"CalibrateAzimuthField")).getText();

        System.out.println("Calibrate azimuth: " + data);
        //Communication.send_data("Prikaz123 25");
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

    @Override
    public void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {
        // f - pouzivatel zadava double hodnotu, nastavuje sa rýchlosť kupoly (0 - 60)
        Pair<Boolean, KeyCode> shortcut_id = new Pair<>(false, KeyCode.F);
        shortcuts.put(shortcut_id, this::Frequency);
    }

    Node GetById(Pane pane, String id){
        for(Node i:pane.getChildren()){
            if(i.getId() != null && i.getId().equals(id)) return i;
        }
        return null;
    }
}
