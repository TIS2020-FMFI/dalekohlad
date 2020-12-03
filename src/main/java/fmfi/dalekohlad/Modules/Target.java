package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import fmfi.dalekohlad.InputHandling.InputConfirmation;
import java.util.Map;

public class Target implements GUIModule {
    private Pane pane;
    private Label[] info;

    private final int GOTO_CANCEL_CODE = 71;
    private final int SWITCH_POLE_CROSSING_CODE = 112;

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

    public static boolean goodFormat(String input) {
        if (input == null) return false;

        if(!input.matches("[0-9][0-9][.][0-9][0-9][0-9]") && !input.matches("[0-9][0-9][:][0-9][0-9][:][0-9][0-9][.][0-9]")) {
            return false;
        }
        return true;
    }

    public void LoadTarget(){
        TextField ra = ((TextField)GUIModule.GetById(pane,"LoadTargetRA"));
        TextField de = ((TextField)GUIModule.GetById(pane,"LoadTargetDE"));

        String ra_text = ra.getText();
        String de_text = de.getText();

        if(goodFormat(ra_text) && goodFormat(de_text)) {
            InputConfirmation.warn("Currently not implemented!");
        }
        else {
            InputConfirmation.warn("Data was entered incorrectly!");
        }

        ra.setText("");
        de.setText("");
    }

    public void GoToCancel(){
        Communication.sendData(String.valueOf(GOTO_CANCEL_CODE));
    }

    public void PoleCrossing(){
        Communication.sendData(String.valueOf(SWITCH_POLE_CROSSING_CODE));
    }

    public void update(JsonObject jo) {
        if(jo.get("TAREncoder1") != null) Platform.runLater(() -> {info[0].setText(jo.get("TAREncoder1").getAsString());});
        if(jo.get("TARdEnc1") != null) Platform.runLater(() -> {info[1].setText(jo.get("TARdEnc1").getAsString());});
        if(jo.get("TARHAApparent") != null) Platform.runLater(() -> {info[2].setText(jo.get("TARHAApparent").getAsString());});
        if(jo.get("TARDEApparent") != null) Platform.runLater(() -> {info[3].setText(jo.get("TARDEApparent").getAsString());});
        if(jo.get("TARRAJ2000") != null) Platform.runLater(() -> {info[4].setText(jo.get("TARRAJ2000").getAsString());});
        if(jo.get("TARDEJ2000") != null) Platform.runLater(() -> {info[5].setText(jo.get("TARDEJ2000").getAsString());});
        if(jo.get("TARAzimuth") != null) Platform.runLater(() -> {info[6].setText(jo.get("TARAzimuth").getAsString());});
        if(jo.get("TARElevation") != null) Platform.runLater(() -> {info[7].setText(jo.get("TARElevation").getAsString());});
        if(jo.get("TARPoleCrossing") != null) Platform.runLater(() -> {info[8].setText(jo.get("TARPoleCrossing").getAsString());});
        if(jo.get("TARStatus") != null) Platform.runLater(() -> {info[9].setText(jo.get("TARStatus").getAsString());});
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