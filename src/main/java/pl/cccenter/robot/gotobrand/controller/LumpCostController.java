package pl.cccenter.robot.gotobrand.controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import pl.cccenter.robot.gotobrand.DetailCostView;
import pl.cccenter.robot.gotobrand.web.GTBLumpCosts;
import pl.cccenter.robot.hrf.DetailCost;
import pl.cccenter.robot.hrf.LumpCost;
import pl.cccenter.robot.web.FirefoxBrowser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Rafał Szyszka on 27.04.2018.
 */
public class LumpCostController implements Initializable {

    @FXML private Button closeApp;
    @FXML private Button fillForm;
    @FXML private Label message;
    @FXML private Label progressMsg;
    @FXML private ProgressBar progressBar;

    private FirefoxBrowser browser;
    private ArrayList<LumpCost> lumpCosts;
    private ArrayList<DetailCost> detailCosts;

    public void fillForm() {
        ArrayList<String[]> lumpCostsAttrs = new ArrayList<>();
        lumpCosts.forEach(lumpCost -> lumpCostsAttrs.add(lumpCost.splitToAtr()));

        GTBLumpCosts gtbLumpCosts = new GTBLumpCosts(lumpCostsAttrs, browser);
        gtbLumpCosts.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, event -> {
            progressMsg.textProperty().unbind();
            progressBar.progressProperty().unbind();

            if (detailCosts.size() > 0) {
                try {
                    closeApp();
                    (new DetailCostView()).show(detailCosts, browser);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                message.setText("Pomyślnie wypełniłeś wniosek! :)");
                fillForm.setDisable(true);
                fillForm.setVisible(false);
                closeApp.setVisible(true);
                closeApp.setDisable(false);
            }
        });

        progressMsg.textProperty().bind(gtbLumpCosts.messageProperty());
        progressBar.progressProperty().bind(gtbLumpCosts.progressProperty());
        gtbLumpCosts.setProgressMsg("Uzupełniono: ");

        new Thread(gtbLumpCosts).start();
        //manualFill(lumpCostsAttrs);
    }

    private void manualFill(ArrayList<String[]> lumpCostsAttrs) {
        for(int i = 0; i < lumpCostsAttrs.size(); i++) {
            String[] cost = lumpCostsAttrs.get(i);
            String script = GTBLumpCosts.FILL_LUMP_COST_SCRIPT.replaceAll(GTBLumpCosts.Arguments.INDEX, Integer.toString(i))
                    .replace(GTBLumpCosts.Arguments.CATEGORY, cost[LumpCost.CATEGORY])
                    .replace(GTBLumpCosts.Arguments.LUMP_NAME, cost[LumpCost.NAME])
                    .replace(GTBLumpCosts.Arguments.LUMP_TYPE, cost[LumpCost.TYPE])
                    .replace(GTBLumpCosts.Arguments.POINTER_NAME, cost[LumpCost.POINTER_NAME])
                    .replace(GTBLumpCosts.Arguments.POINTER_VALUE, cost[LumpCost.POINTER_VALUE])
                    .replace(GTBLumpCosts.Arguments.QUALIFIED_COST, cost[LumpCost.QUALIFIED_COSTS])
                    .replace(GTBLumpCosts.Arguments.SUBSIDY, cost[LumpCost.SUBSIDY])
                    .replace(GTBLumpCosts.Arguments.TASK, cost[LumpCost.TASK])
                    .replace(GTBLumpCosts.Arguments.IS_LUMP, cost[LumpCost.IS_LUMP])
                    .replaceAll("\n", " ");
            System.out.println(script);
            browser.executeVoidScript(script);
        }
    }

    public void closeApp() {
        ((Stage)message.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeApp.setDisable(true);
        closeApp.setVisible(false);
        progressBar.setProgress(0);
    }

    public FirefoxBrowser getBrowser() {
        return browser;
    }

    public void setBrowser(FirefoxBrowser browser) {
        this.browser = browser;
    }

    public ArrayList<LumpCost> getLumpCosts() {
        return lumpCosts;
    }

    public void setLumpCosts(ArrayList<LumpCost> lumpCosts) {
        this.lumpCosts = lumpCosts;
    }

    public void setDetailCosts(ArrayList<DetailCost> detailCosts) {
        this.detailCosts = detailCosts;
    }
}
