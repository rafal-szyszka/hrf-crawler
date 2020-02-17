package pl.cccenter.robot.gotobrand;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.cccenter.robot.gotobrand.controller.DetailCostController;
import pl.cccenter.robot.gotobrand.controller.LumpCostController;
import pl.cccenter.robot.hrf.DetailCost;
import pl.cccenter.robot.hrf.LumpCost;
import pl.cccenter.robot.web.FirefoxBrowser;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Rafał Szyszka on 08.03.2019.
 */
public class DetailCostView {

    public void show(ArrayList<DetailCost> detailCosts, FirefoxBrowser browser) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gotobrand/DetailCostView.fxml"));
        Parent root = loader.load();

        DetailCostController detailCostController = loader.getController();
        detailCostController.setBrowser(browser);
        detailCostController.setDetailCosts(detailCosts);

        stage.setTitle("Szczegółowe wydatki");
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);
        stage.show();
    }

}
