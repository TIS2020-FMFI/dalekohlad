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

    public static boolean isDouble(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void LoadTarget(){
        TextField ra = ((TextField)GUIModule.GetById(pane,"LoadTargetRA"));
        TextField de = ((TextField)GUIModule.GetById(pane,"LoadTargetDE"));

        String ra_text = ra.getText();
        String de_text = de.getText();

        if(isDouble(ra_text) && isDouble(de_text)) {
            Communication.send_data(76+";"+ra_text+";"+de_text);
        }
        else {
            InputConfirmation.warn("Data was entered incorrectly!");
        }


        System.out.println("Load Target: " + ra.getText() + ", " + de.getText());
        ra.setText("");
        de.setText("");
    }

    public void GoToCancel(){
        Communication.send_data(String.valueOf(71));
        System.out.println("Go To / Cancel");
    }

    public void PoleCrossing(){
        Communication.send_data(String.valueOf(112));
        System.out.println("Pole Crossing");
    }

    public void update(JsonObject jo) {
        info[0].setText(jo.get("TAREncoder1").getAsString());
        info[1].setText(jo.get("TARdEnc1").getAsString());
        info[2].setText(jo.get("TARHAApparent").getAsString());
        info[3].setText(jo.get("TARDEApparent").getAsString());
        info[4].setText(jo.get("TARRAJ2000").getAsString());
        info[5].setText(jo.get("TARDEJ2000").getAsString());
        info[6].setText(jo.get("TARAzimuth").getAsString());
        info[7].setText(jo.get("TARElevation").getAsString());
        info[8].setText(jo.get("TARPoleCrossing").getAsString());
        info[9].setText(jo.get("TARStatus").getAsString());
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