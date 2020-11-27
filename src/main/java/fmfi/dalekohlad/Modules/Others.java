package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import eap.fits.FitsHDU;
import eap.fits.FitsImageData;
import eap.fits.RandomAccessFitsFile;
import eap.fitsbrowser.FitsImageViewer;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.RandomAccessFile;

public class Others implements GUIModule {
    private Pane pane;
    private Button shortcuts;
    private Label info;
    private Label status;
    private Label pathFITS;
    private static final Logger lgr = LogManager.getLogger(Others.class);
    private final int FITS_SIZE = 170;
    private final Dimension DIMENSION = new Dimension(FITS_SIZE, FITS_SIZE);
    private SwingNode swingNode;

    private void fitsHandle(String pathToFITS) {
        //suppose FITS file is never rewritten -> new photo is written to new file
        if(pathToFITS == null || pathToFITS.compareTo(pathFITS.getText()) == 0) {
            lgr.debug("FITS hasn't changed.");
            return;
        }
        pathFITS.setText(pathToFITS);
        try {
            RandomAccessFile lastFrame = new RandomAccessFile(pathToFITS.toString(), "r");
            RandomAccessFitsFile fitFile = new RandomAccessFitsFile(lastFrame);
            FitsHDU hdu = fitFile.getHDU(0);
            FitsImageData imageData = (FitsImageData) hdu.getData();
            FitsImageViewer image = new FitsImageViewer(imageData);

            image.setMaximumSize(DIMENSION);

            Platform.runLater(() -> this.swingNode.setContent(image));
        } catch (Exception e) {
            lgr.error("Failed load FITS file");
        }
    }

    @Override
    public void update(JsonObject jo) {
        String time = jo.get("TIMEUTC").getAsString();
        String timeUT1 = jo.get("TIMEUT1UTC").getAsString();
        String fits_path = jo.get("FITSpath").getAsString();
        String infoText = time + " " + timeUT1;
        this.fitsHandle(fits_path);
        Platform.runLater(() -> {
            this.info.setText(infoText);
        });
    }

    @Override
    public void init(Pane pane) {
        this.pane = pane;
        ((Button) GUIModule.GetById(pane,"Exit")).setOnAction(event -> System.exit(0));
        this.shortcuts = (Button) GUIModule.GetById(pane, "Shortcuts");
        this.info = (Label) GUIModule.GetById(pane, "info");
        this.status = (Label) GUIModule.GetById(pane, "Connected");
        this.pathFITS = (Label) GUIModule.GetById(pane, "path_to_last_frame");
        this.swingNode = (SwingNode) GUIModule.GetById(pane, "node_FITS");
        
        //((Button) GUIModule.GetById(pane,"Shortcuts")).setOnAction(event -> this.update(new JsonObject()));
    }
}
