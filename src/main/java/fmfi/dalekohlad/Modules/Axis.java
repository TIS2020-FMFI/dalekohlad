package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
import fmfi.dalekohlad.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import java.lang.reflect.Method;
import java.util.Map;


public class Axis implements GUIModule {
    private Pane pane;
    private Label[] info_polar;
    private Label[] info_declination;

    public void init(Pane p) {
        pane = p;
        info_polar = new Label[5];
        info_declination = new Label[5];

        for(int i = 0; i < 5; i++){
            info_polar[i] = (Label) GetById(pane, "polar" + (i+1));
            info_declination[i] = (Label) GetById(pane, "declination" + (i+1));
            info_polar[i].setText("...");
            info_declination[i].setText("...");
        }

       ((Button)GetById(pane,"EnableDisableMotors")).setOnAction(actionEvent -> EnableDisableMotors());
       ((Button)GetById(pane,"StopRA")).setOnAction(actionEvent -> StopRA());
       ((Button)GetById(pane,"StopDE")).setOnAction(actionEvent -> StopDE());
       ((Button)GetById(pane,"StopRAandDE")).setOnAction(actionEvent -> StopRAandDE());
       ((Button)GetById(pane,"Calibrate")).setOnAction(actionEvent -> Calibrate());
       ((Button)GetById(pane,"Correction")).setOnAction(actionEvent -> Correction());
       ((Button)GetById(pane,"SlewRA")).setOnAction(actionEvent -> SlewRA());
       ((Button)GetById(pane,"SlewDE")).setOnAction(actionEvent -> SlewDE());
       ((Button)GetById(pane,"GoTo")).setOnAction(actionEvent -> GoTo());
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
        TextField slew_ra = ((TextField)GetById(pane,"SlewRAField"));
        System.out.println("Slew RA: " + slew_ra.getText());
        //Communication.send_data("Prikaz123 25");
        slew_ra.setText("");
    }

    public void SlewDE() {
        TextField slew_de = ((TextField)GetById(pane,"SlewDEField"));
        System.out.println("Slew DE: " + slew_de.getText());
        //Communication.send_data("Prikaz123 25");
        slew_de.setText("");
    }

    public void GoTo() {
        TextField goto_ra = ((TextField)GetById(pane,"GoToRAField"));
        TextField goto_de = ((TextField)GetById(pane,"GoToDEField"));
        System.out.println("Go To: " + goto_ra.getText() + ", " + goto_de.getText());
        //Communication.send_data("Prikaz123 25");
        goto_ra.setText("");
        goto_de.setText("");
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

    public void FocusTextField(boolean textArea, String id){
        Node field;
        if(textArea) field = ((TextArea)GetById(pane,id));
        else field = ((TextField)GetById(pane,id));
        field.requestFocus();
    }
}