package pl.cccenter.robot.gotobrand;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.cccenter.robot.gotobrand.controller.LumpCostController;
import pl.cccenter.robot.hrf.LumpCost;
import pl.cccenter.robot.web.FirefoxBrowser;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Rafał Szyszka on 27.04.2018.
 */
public class LumpCostView {

    public void show(ArrayList<LumpCost> lumpCosts, FirefoxBrowser browser) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gotobrand/LumpCostView.fxml"));
        Parent root = loader.load();

        LumpCostController lumpCostController = loader.getController();
        lumpCostController.setBrowser(browser);
        lumpCostController.setLumpCosts(lumpCosts);

        stage.setTitle("Koszty ryczałtowe");
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);
        stage.show();
    }

}
