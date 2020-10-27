package fmfi.dalekohlad.Modules;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Map;

public interface GUIModule {
    // vsetky moduly by mali mat default konstruktor
    // kazdy ma v inite prideleny prislusny Pane, ktory moze lubovolne upravovvat
    // Pane prisluchajuci urcitemu modulu musi byt v GUI priamym potomkom sceny a mat id v tvare "module_%nazov_modulu%"
    // update bude volany vzdy ked sa dostane v komunikacii novy konfig
    void update(String data);
    void init(Pane pane);
    String collectData();
    default void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {}  // Pair<isShiftDown(), KeyCode>
}
