package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
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
            info[i] = (Label) GUIModule.GetById(pane, "target" + (i+1));
            info[i].setText("...");
        }

        ((Button)GUIModule.GetById(pane,"LoadTarget")).setOnAction(actionEvent -> LoadTarget());
        ((Button)GUIModule.GetById(pane,"GoToCancel")).setOnAction(actionEvent -> GoToCancel());
        ((Button)GUIModule.GetById(pane,"PoleCrossing")).setOnAction(actionEvent -> PoleCrossing());
    }

    public void LoadTarget(){
        TextField ra = ((TextField)GUIModule.GetById(pane,"LoadTargetRA"));
        TextField de = ((TextField)GUIModule.GetById(pane,"LoadTargetDE"));

        System.out.println("Load Target: " + ra.getText() + ", " + de.getText());
        // temporary for testing
        Communication.send_data(ra.getText());
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

    public void update(JsonObject jo) {

    }

    @Override
    public void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {
        // L - load target
        Pair<Boolean, KeyCode> load_target = new Pair<>(true, KeyCode.L);
        shortcuts.put(load_target, () -> FocusTextField(false,"LoadTargetRA", pane));

        // p - pole crossing
        Pair<Boolean, KeyCode> pole_crossing = new Pair<>(false, KeyCode.P);
        shortcuts.put(pole_crossing, this::PoleCrossing);
        // G - goto/cancel
        Pair<Boolean, KeyCode> goto_cancel = new Pair<>(true, KeyCode.G);
        shortcuts.put(goto_cancel, this::GoToCancel);

    }
}