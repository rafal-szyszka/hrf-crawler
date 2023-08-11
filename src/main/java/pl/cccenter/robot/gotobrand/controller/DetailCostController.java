package pl.cccenter.robot.gotobrand.controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import pl.cccenter.robot.gotobrand.web.GTBDetailCosts;
import pl.cccenter.robot.hrf.DetailCost;
import pl.cccenter.robot.web.FirefoxBrowser;

import java.util.ArrayList;

/**
 * Created by Rafał Szyszka on 08.03.2019.
 */
public class DetailCostController {

    @FXML private Button closeApp;
    @FXML private Button fillForm;
    @FXML private Label message;
    @FXML private Label progressMsg;
    @FXML private ProgressBar progressBar;

    private FirefoxBrowser browser;
    private ArrayList<DetailCost> detailCosts;

    public void fillForm() {

        GTBDetailCosts fillDetailCosts = new GTBDetailCosts(detailCosts, browser, "Uzupełniono: ");

        fillDetailCosts.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, e -> {
            progressMsg.textProperty().unbind();
            progressBar.progressProperty().unbind();
            progressMsg.setText("Uzupełniłeś wniosek :)");
            fillForm.setVisible(false);
            closeApp.setVisible(true);
        });

        progressMsg.textProperty().bind(fillDetailCosts.messageProperty());
        progressBar.progressProperty().bind(fillDetailCosts.progressProperty());

        new Thread(fillDetailCosts).start();

//        IntStream.range(0, detailCosts.size()).forEach(idx -> {
//            String[] costAttrs = detailCosts.get(idx).splitToAtr();
//            String script = GTBDetailCosts.FILL_DETAIL_COSTS_SCRIPT.replaceAll(GTBDetailCosts.Arguments.INDEX, String.valueOf(idx))
//                    .replace(GTBDetailCosts.Arguments.TASK, costAttrs[DetailCost.TASK].trim())
//                    .replace(GTBDetailCosts.Arguments.DESCRIPTION, costAttrs[DetailCost.DESCRIPTION].trim())
//                    .replace(GTBDetailCosts.Arguments.CATALOG, costAttrs[DetailCost.CATALOG].trim())
//                    .replace(GTBDetailCosts.Arguments.DETAILS, costAttrs[DetailCost.DETAILS].trim())
//                    .replace(GTBDetailCosts.Arguments.QUALIFIED_COST, costAttrs[DetailCost.QUALIFIED_SUM].trim())
//                    .replace(GTBDetailCosts.Arguments.UNIT_NUMBER, costAttrs[DetailCost.UNIT_AMOUNT].trim())
//                    .replace(GTBDetailCosts.Arguments.UNIT_PRICE, costAttrs[DetailCost.PRICE].trim())
//                    .replace(GTBDetailCosts.Arguments.PEOPLE_AMOUNT, costAttrs[DetailCost.PEOPLE_NUMBER].trim())
//                    .replace(GTBDetailCosts.Arguments.DAYS_NUMBER, costAttrs[DetailCost.DAY_NUMBER].trim())
//                    .replace(GTBDetailCosts.Arguments.NIGHTS_NUMBER, costAttrs[DetailCost.OVERNIGHT_NUMBER].trim())
//                    .replace(GTBDetailCosts.Arguments.OFFER_1, costAttrs[DetailCost.OFFER1_DATA].trim())
//                    .replace(GTBDetailCosts.Arguments.OFFER_2, costAttrs[DetailCost.OFFER2_DATA].trim())
//                    .replace(GTBDetailCosts.Arguments.OFFER_3, costAttrs[DetailCost.OFFER3_DATA].trim())
//                    .replace(GTBDetailCosts.Arguments.IS_DATE_RECOGNIZE, costAttrs[DetailCost.RESEARCH_DATE].trim().equalsIgnoreCase("Nie dotyczy") ? "false" : "true")
//                    .replace(GTBDetailCosts.Arguments.DATE_RECOGNIZE, costAttrs[DetailCost.RESEARCH_DATE].trim());
//            System.out.println("---\n\n" + script);
//            browser.executeVoidScript(script);
//        });
    }

    public void closeApp() {
        ((Stage)message.getScene().getWindow()).close();
    }

    public void setBrowser(FirefoxBrowser browser) {
        this.browser = browser;
    }

    public void setDetailCosts(ArrayList<DetailCost> detailCosts) {
        this.detailCosts = detailCosts;
    }
}
