package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Map;

public interface GUIModule {
    // Vsetky moduly by mali mat default konstruktor.
    
    // Kazdy modul ma v inite prideleny prislusny Pane, ktory moze lubovolne upravovat.
    
    // Pane prisluchajuci urcitemu modulu musi byt v GUI priamym potomkom sceny a mat id v tvare "module_%nazov_modulu%".
    
    // update bude volany vzdy ked sa dostane v komunikacii novy konfig.
    
    // Kazdy modul musi robit zmeny v GUI pomocou Platform.runLater() alebo Task (https://stackoverflow.com/questions/13784333/platform-runlater-and-task-in-javafx),
    // napriklad: Platform.runLater(() -> {label.text = "zmeneny";});.
    // Je to nevyhnutne kvoli https://stackoverflow.com/questions/25171039/what-is-the-best-way-to-manage-multithreading-in-javafx-8       .
    
    void update(JsonObject jo);
    void init(Pane pane);
    default void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {}  // Pair<isShiftDown(), KeyCode>
}
