package pl.cccenter.robot.gotobrand.controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import pl.cccenter.robot.gotobrand.TaskView;
import pl.cccenter.robot.gotobrand.web.GTBPromoTask;
import pl.cccenter.robot.hrf.*;
import pl.cccenter.robot.web.FirefoxBrowser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Created by Rafał Szyszka on 02.03.2019.
 */
public class PromoTaskController {

    @FXML private Button closeApp;
    @FXML private Button prepare;
    @FXML private Button fillForm;
    @FXML private Label message;
    @FXML private Label progressMsg;
    @FXML private ProgressBar progressBar;

    private FirefoxBrowser browser;
    private ArrayList<PromoTask> promoTasks;
    private ArrayList<Task> tasks;
    private ArrayList<Cost> costs;
    private ArrayList<LumpCost> lumpCosts;
    private ArrayList<DetailCost> detailCosts;

    public void clearPrevious() {
        message.setText("Te zadania muszą zostać usunięte ręcznie.");
    }

    public void nextStep(ActionEvent actionEvent) {
        ((Stage)message.getScene().getWindow()).close();
        try {
            new TaskView.Builder()
                    .setLumpCosts(lumpCosts)
                    .setTasks(tasks)
                    .setCosts(costs)
                    .setDetailCosts(detailCosts)
                    .setBrowser(browser)
                    .createTaskView()
                    .show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void fillForm(ActionEvent actionEvent) {
        GTBPromoTask promoTask = new GTBPromoTask(promoTasks, "Uzupełniono: ", browser);

        promoTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, e -> {
            fillForm.setVisible(false);
            closeApp.setVisible(true);
            progressMsg.textProperty().unbind();
            progressBar.progressProperty().unbind();
        });

        progressMsg.textProperty().bind(promoTask.messageProperty());
        progressBar.progressProperty().bind(promoTask.progressProperty());
        (new Thread(promoTask)).start();

    }

    private void manualFill() {
        String script = GTBPromoTask.FILL_PROMO_TASKS_SCRIPT;

        IntStream.range(0, promoTasks.size()).forEach(idx -> {
            String[] promoTaskParams = promoTasks.get(idx).splitToAtr();

            browser.executeVoidScript(
                script.replaceAll(GTBPromoTask.Arguments.INDEX, String.valueOf(idx))
                    .replace(GTBPromoTask.Arguments.NAME, promoTaskParams[PromoTask.NAME])
                    .replace(GTBPromoTask.Arguments.COUNTRY, promoTaskParams[PromoTask.COUNTRY])
                    .replace(GTBPromoTask.Arguments.YEAR, promoTaskParams[PromoTask.YEAR])
                    .replace(GTBPromoTask.Arguments.SITE, promoTaskParams[PromoTask.WWW_SITE])
                    .replace(GTBPromoTask.Arguments.COUNTRY_STATION, promoTaskParams[PromoTask.IS_NATIONAL_STAND])
            );
        });
    }

    public void setupPage() {
        browser.executeVoidScript(
                GTBPromoTask.ADD_PROMO_TASK_FORMS
                    .replace(GTBPromoTask.Arguments.AMOUNT, String.valueOf(promoTasks.size()))
        );

        fillForm.setVisible(true);
        prepare.setDisable(true);
        prepare.setVisible(false);
    }

    public void setBrowser(FirefoxBrowser browser) {
        this.browser = browser;
    }

    public void setPromoTasks(ArrayList<PromoTask> promoTasks) {
        this.promoTasks = promoTasks;
    }

    public void setCosts(ArrayList<Cost> costs) {
        this.costs = costs;
    }

    public void setLumpCosts(ArrayList<LumpCost> lumpCosts) {
        this.lumpCosts = lumpCosts;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void setDetailCosts(ArrayList<DetailCost> detailCosts) {
        this.detailCosts = detailCosts;
    }
}
