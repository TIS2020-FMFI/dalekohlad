package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
import fmfi.dalekohlad.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.lang.reflect.Method;
import java.util.Map;


public class Axis implements GUIModule {
    private Pane pane;

    public void init(Pane p) {
        pane = p;
       ((Button)GetById(pane,"EnableDisableMotors")).setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               EnableDisableMotors();
           }
       });
       ((Button)GetById(pane,"StopRA")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                StopRA();
            }
       });
       ((Button)GetById(pane,"StopDE")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                StopDE();
            }
       });
       ((Button)GetById(pane,"StopRAandDE")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                StopRAandDE();
            }
       });
       ((Button)GetById(pane,"Calibrate")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Calibrate();
            }
       });
       ((Button)GetById(pane,"Correction")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Correction();
            }
       });
       ((Button)GetById(pane,"SlewRA")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SlewRA();
            }
       });
       ((Button)GetById(pane,"SlewDE")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SlewDE();
            }
       });
       ((Button)GetById(pane,"GoTo")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GoTo();
            }
       });
    }

    public void update(JsonObject jo) {

    }

    public void EnableDisableMotors() {
        Button button = (Button) GetById(pane, "EnableDisableMotors");
        if(button.getText().equals("Enable Motors")) button.setText("Disable Motors");
        else button.setText("Enable Motors");
        System.out.println("Hello from the modules side");
    }

    public void StopRA(){
        System.out.println("Stop RA");
        //Communication.send_data("StopRA");
    }

    public void StopDE(){
        System.out.println("Stop DE");
        //Communication.send_data("StopDE");
    }

    public void StopRAandDE(){
        System.out.println("Stop RA and DE");
        //Communication.send_data("StopRAandDE");
    }

    public void Calibrate(){
        System.out.println("Calibrate");
        //Communication.send_data("Calibrate");
    }

    public void  Correction(){
        System.out.println("Correction");
        //Communication.send_data("Correction");
    }

    public void SlewRA() {
        String data = ((TextField)GetById(pane,"SlewRAField")).getText();

        System.out.println("Slew RA: " + data);
        //Communication.send_data("Prikaz123 25");
    }

    public void SlewDE() {
        String data = ((TextField)GetById(pane,"SlewDEField")).getText();

        System.out.println("Slew DE: " + data);
        //Communication.send_data("Prikaz123 25");
    }

    public void GoTo() {
        String data = ((TextField)GetById(pane,"GoToRAField")).getText();
        data += ", " + ((TextField)GetById(pane,"GoToDEField")).getText();

        System.out.println("Go To: " + data);
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