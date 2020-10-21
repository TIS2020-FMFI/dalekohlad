package fmfi.dalekohlad.Modules;

import javafx.scene.layout.Pane;

public interface GUIModule {
    // vsetky moduly by mali byt singletony, budu mat urcite iba jednu instanciu a musia implementovat tento interface
    // instancia sa musi ziskavat statickou verejnou metodou getInstance()
    // kazdy ma v inite prideleny prislusny Pane, ktory moze lubovolne upravovvat
    // Pane prisluchajuci urcitemu modulu musi byt v GUI priamym potomkom sceny a mat id v tvare "module_%nazov_modulu%"
    // update bude volany vzdy ked sa dostane v komunikacii novy konfig
    public void update();
    public void init(Pane pane);
}
