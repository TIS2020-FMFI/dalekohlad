package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Main;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LastFrame implements GUIModule{
    private Pane pane;
    private static final Logger lgr = LogManager.getLogger(LastFrame.class);

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
