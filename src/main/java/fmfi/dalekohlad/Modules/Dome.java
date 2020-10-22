package fmfi.dalekohlad.Modules;

import javafx.scene.layout.Pane;

public class Dome implements GUIModule {
    private Pane pane;

    public void init(Pane p) {
        pane = p;
    }

    public void update(String data) {

    }

    public String collect_data() {
        return "";
    }
}
