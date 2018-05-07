package pl.cccenter.robot.gotobrand;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.cccenter.robot.gotobrand.controller.CostViewController;
import pl.cccenter.robot.gotobrand.controller.TaskViewController;
import pl.cccenter.robot.hrf.Cost;
import pl.cccenter.robot.hrf.LumpCost;
import pl.cccenter.robot.web.FirefoxBrowser;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Rafał on 06.02.2017.
 */
public class CostView {

    public void show(ArrayList<Cost> costs, ArrayList<LumpCost> lumpCosts, FirefoxBrowser browser) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gotobrand/CostView.fxml"));
        Parent root = fxmlLoader.load();

        CostViewController controller = fxmlLoader.getController();
        controller.setBrowser(browser);
        controller.setCosts(costs);
        controller.setLumpCosts(lumpCosts);

        stage.setTitle("Wypełnij koszta");
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);
        stage.show();
    }

}
