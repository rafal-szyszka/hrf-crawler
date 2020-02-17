package pl.cccenter.robot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.cccenter.robot.hrf.Task;
import pl.cccenter.robot.web.FirefoxBrowser;

import java.io.IOException;

public class HRFRobot extends Application {

    public static final String VERSION = "2.3.11";

    public static final String ROBOT_SHEET = "ROBOT";
    public enum RunMode {
        RAW, EDITION
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/HRFRobot.fxml"));
        primaryStage.setTitle("HRFRobot v" + VERSION);
        primaryStage.setScene(new Scene(root, 500, 330));
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }
}
