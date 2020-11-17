package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Main;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import nom.tam.fits.Fits;

public class LastFrame implements GUIModule{
    private Pane pane;
    private static final Logger lgr = LogManager.getLogger(LastFrame.class);

    @Override
    public void update(JsonObject jo) {
    }

    @Override
    public void init(Pane pane) {

        this.pane = pane;
        try {
            Fits f = new Fits("/example.fits");
        } catch (Exception e) {
            lgr.fatal(e.getMessage());
        }
    }

    @Override
    public void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {

    }
}
