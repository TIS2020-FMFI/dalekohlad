package fmfi.dalekohlad.Modules;

import com.google.gson.JsonObject;
import eap.fits.FitsHDU;
import eap.fits.FitsImageData;
import eap.fits.RandomAccessFitsFile;
import eap.fitsbrowser.FitsImageViewer;
import fmfi.dalekohlad.Mediator;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.RandomAccessFile;
import java.util.Timer;
import java.util.TimerTask;

public class Others implements GUIModule {
    private static final String shortcuts_screen = "/fxml/shortcuts_scene.fxml";
    private static final Logger lgr = LogManager.getLogger(Others.class);
    private final int FITS_SIZE = 170;
    private final Dimension DIMENSION = new Dimension(FITS_SIZE, FITS_SIZE);
    private final int IMAGE_HDU = 0;
    private static boolean wasUpdated = true;
    private static boolean connectionFailed = false;

    private Parent main_screen_root;
    private Pane pane;
    private Label info;
    private Label status;
    private Label pathFITS;
    private SwingNode swingNode;

    private void fitsHandle(String pathToFITS) {
        //suppose FITS file is never rewritten -> new photo is written to new file
        if(changingImageNotNecessary(pathToFITS)) {
            lgr.debug("FITS hasn't changed.");
            return;
        }
        try {
            FitsImageViewer image = loadImageFromFits(pathToFITS);

            image.setMaximumSize(DIMENSION);

            Platform.runLater(() -> this.swingNode.setContent(image));
        } catch (Exception e) {
            lgr.error("Failed load FITS file");
        }

        Platform.runLater(() -> pathFITS.setText(pathToFITS));
    }

    private boolean changingImageNotNecessary(String pathToFits) {
        return (pathToFits == null || pathToFits.compareTo(pathFITS.getText()) == 0);
    }

    private FitsImageViewer loadImageFromFits(String pathoTiFits) throws Exception {
        RandomAccessFile lastFrame = new RandomAccessFile(pathoTiFits.toString(), "r");
        RandomAccessFitsFile fitFile = new RandomAccessFitsFile(lastFrame);
        FitsHDU hdu = fitFile.getHDU(IMAGE_HDU);
        FitsImageData imageData = (FitsImageData) hdu.getData();
        FitsImageViewer image = new FitsImageViewer(imageData);
        return image;
    }

    @Override
    public void update(JsonObject jo) {
        markFlagsAsConnected();
        String infoText = "";
        try {
            String time = jo.get("TIMEUTC").getAsString();
            infoText += time + " ";
        } catch (Exception e) {
            lgr.debug("Time UTC wasn't loaded.");
        }
        try {
            String timeUT1 = jo.get("TIMEUT1UTC").getAsString();
            infoText += timeUT1 + " ";
        } catch (Exception e) {
            lgr.debug("Time UTC wasn't loaded.");
        }
        try {
            String fits_path = jo.get("FITSpath").getAsString();
            this.fitsHandle(fits_path);
        } catch (Exception e) {
            lgr.debug("Path to FITS wasn't loaded");
        }

        String finalInfoText = infoText;
        Platform.runLater(() -> {
            this.info.setText(finalInfoText);
        });
    }


    private void markFlagsAsConnected() {
        Others.wasUpdated = true;
        if (connectionFailed) {
            connectionFailed = false;
        }
    }

    private void setDisplayingShortcuts() {
        Pane shortcutsPane = null;
        Scene scene;
        try {
            shortcutsPane = FXMLLoader.load(Others.class.getResource(shortcuts_screen));
        }
        catch (Exception e) {
            lgr.fatal("Unable to load FXML resource: " + shortcuts_screen, e);
            System.exit(Mediator.EXIT_FXML_ERROR);
        }
        scene = pane.getParent().getScene();
        setReturningToMain(shortcutsPane);

        scene.setRoot(shortcutsPane);
    }

    private void setReturningToMain(Pane shortcutsPane) {
        Scene scene = pane.getParent().getScene();
        Button backToMain = (Button) GUIModule.GetById(shortcutsPane, "back");
        backToMain.setOnAction(e -> scene.setRoot(main_screen_root));
    }

    @Override
    public void init(Pane pane) {
        this.pane = pane;
        ((Button) GUIModule.GetById(pane,"Exit")).setOnAction(event -> System.exit(0));
        this.info = (Label) GUIModule.GetById(pane, "info");
        this.status = (Label) GUIModule.GetById(pane, "Connected");
        this.pathFITS = (Label) GUIModule.GetById(pane, "path_to_last_frame");
        this.swingNode = (SwingNode) GUIModule.GetById(pane, "node_FITS");
        this.main_screen_root = pane.getParent().getScene().getRoot();
        displayConnectionStatus();

        ((Button) GUIModule.GetById(pane,"Shortcuts")).setOnAction(event -> this.setDisplayingShortcuts());
    }

    private void displayConnectionStatus() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!Others.wasUpdated) {
                    BackgroundFill redBackground = new BackgroundFill(Color.RED, null, null);
                    Platform.runLater(() -> status.setBackground(new Background(redBackground)));
                }
                else if(Others.connectionFailed && Others.wasUpdated) {
                    Others.connectionFailed = false;
                    BackgroundFill greenBackground = new BackgroundFill(Color.GREEN, null, null);
                    Platform.runLater(() -> status.setBackground(new Background(greenBackground)));
                }

                Others.wasUpdated = false;
            }
        }, 6000,2500);
    }
}
