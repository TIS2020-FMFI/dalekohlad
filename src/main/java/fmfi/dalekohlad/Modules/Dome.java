package fmfi.dalekohlad.Modules;

import javafx.scene.layout.Pane;

public class Dome implements GUIModule {
    private static Dome single_instance = null;
    private static Pane pane;

    public void init(Pane p) {
        pane = p;
    }

    public void update() {

    }

    private Dome() {}

    public static Dome getInstance() {
        if (single_instance == null) {
            single_instance = new Dome();
        }

        return single_instance;
    }
}
