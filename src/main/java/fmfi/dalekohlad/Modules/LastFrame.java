package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import eap.fits.FitsHDU;
import eap.fits.FitsImageData;
import eap.fits.RandomAccessFitsFile;
import eap.fitsbrowser.FitsImageViewer;
import fmfi.dalekohlad.Main;
import javafx.embed.swing.SwingNode;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.awt.*;
import java.io.RandomAccessFile;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LastFrame implements GUIModule{
    private Pane pane;
    private static final Logger lgr = LogManager.getLogger(LastFrame.class);

    @Override
    public void update(JsonObject jo) {
        String pathToFITS = "get_from_JSON";
        int size = 300;
        try {
            RandomAccessFile lastFrame = new RandomAccessFile(pathToFITS, "r");
            RandomAccessFitsFile fitFile = new RandomAccessFitsFile(lastFrame);
            FitsHDU hdu = fitFile.getHDU(0);
            FitsImageData imageData = (FitsImageData) hdu.getData();
            FitsImageViewer image = new FitsImageViewer(imageData);

            Dimension dimension = new Dimension(size, size);
            image.setMaximumSize(dimension);

            SwingNode swingNode = new SwingNode();
            swingNode.setContent(image);
            pane.getChildren().add(swingNode);
        } catch (Exception e) {
            lgr.error("Failed load FITS file");
        }


    }

    @Override
    public void init(Pane pane) {
        this.pane = pane;

    }

    @Override
    public void registerShortcuts(Map<Pair<Boolean, KeyCode>, Runnable> shortcuts) {

    }
}
