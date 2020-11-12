package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Map;

//import nom.tam.fits;


public class LastFrame implements GUIModule{
    private Pane pane;

    @Override
    public void update(JsonObject jo) {
    }

    @Override
    public void init(Pane pane) {
        this.pane = pane;
    }

    @Override
    public void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {

    }
}
