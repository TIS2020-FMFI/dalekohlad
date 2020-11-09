package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import fmfi.dalekohlad.Communication.Communication;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Map;

public class Dome implements GUIModule {
    private Pane pane;

    public void init(Pane p) {
        pane = p;
    }

    public void update(JsonObject jo) {

    }

    public void nastavRychlostKupoly() {
        System.out.println("Hello from the modules side");
        Communication.send_data("Prikaz123 25");
    }

    @Override
    public void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {
        // f - pouzivatel zadava double hodnotu, nastavuje sa rýchlosť kupoly (0 - 60)
        Pair<Boolean, KeyCode> shortcut_id = new Pair<>(false, KeyCode.F);
        shortcuts.put(shortcut_id, this::nastavRychlostKupoly);
    }
}
