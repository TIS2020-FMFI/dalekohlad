package fmfi.dalekohlad.Modules;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Map;

public class Dome implements GUIModule {
    private Pane pane;

    public void init(Pane p) {
        pane = p;
    }

    public void update(String data) {

    }

    public String collectData() {
        return "";
    }

    public void nastavRychlostKupoly() {
        System.out.println("Hello from the modules side");
    }

    @Override
    public void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {
        // f - pouzivatel zadava double hodnotu, nastavuje sa rýchlosť kupoly (0 - 60)
        Pair<Boolean, KeyCode> shortcut_id = new Pair<>(false, KeyCode.F);
        shortcuts.put(shortcut_id, this::nastavRychlostKupoly);

        // velke f ukazka
        Pair<Boolean, KeyCode> shortcut_id_velke_f = new Pair<>(true, KeyCode.F);
        shortcuts.put(shortcut_id_velke_f, () -> System.out.println("Velke F"));

        // left sipka ukazka
        Pair<Boolean, KeyCode> shortcut_id_left = new Pair<>(false, KeyCode.LEFT);
        shortcuts.put(shortcut_id_left, () -> System.out.println("Sipka vlavo"));
    }
}
