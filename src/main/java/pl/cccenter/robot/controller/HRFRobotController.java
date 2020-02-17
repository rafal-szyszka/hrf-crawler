package pl.cccenter.robot.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.cccenter.robot.HRFRobot;
import pl.cccenter.robot.excel.XLSFileChooser;
import pl.cccenter.robot.gotobrand.PromoTaskView;
import pl.cccenter.robot.gotobrand.TaskView;
import pl.cccenter.robot.hrf.*;
import pl.cccenter.robot.tasks.*;
import pl.cccenter.robot.web.FirefoxBrowser;
import pl.cccenter.robot.web.LoginData;
import pl.cccenter.robot.web.LoginService;
import pl.cccenter.robot.web.OpenBrowser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Squier on 2016-10-12.
 */
public class HRFRobotController implements Initializable {

    @FXML private Label chosenFile;
    @FXML private Label taskName;
    @FXML private TextArea messageArea;
    @FXML private Button chooseFile;
    @FXML private Button confirm;
    @FXML private Button openHrf;
    @FXML private Button fillHrf;
    @FXML private ProgressBar taskProgress;
    @FXML private CheckBox ifEditionMode;

    private XSSFWorkbook workbook;
    private LoginData loginData;
    private ArrayList<Task> tasks;
    private ArrayList<Cost> costs;
    private ArrayList<LumpCost> lumpCosts;
    private ArrayList<PromoTask> promoTasks;
    private FirefoxBrowser browser;
    private HRFRobot.RunMode runMode;
    private ArrayList<DetailCost> detailCosts;

    public HRFRobotController() {
    }

    private void goBackToInitialState() {
        confirm.setVisible(true);
        confirm.setDisable(true);
        openHrf.setVisible(false);
        openHrf.setDisable(true);
        fillHrf.setVisible(false);
        fillHrf.setDisable(true);
    }

    public void fillHRFAction() {
        goBackToInitialState();
        try {
            new PromoTaskView.Builder()
                    .setBrowser(browser)
                    .setCosts(costs)
                    .setTasks(tasks)
                    .setLoginData(loginData)
                    .setLumpCosts(lumpCosts)
                    .setPromoTasks(promoTasks)
                    .setDetailCosts(detailCosts)
                    .createPromoProgramView()
                    .show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openHrfAction() {
        OpenBrowser openBrowser = new OpenBrowser(new FirefoxBrowser(), messageArea.getText());

        openBrowser.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, event -> {
            messageArea.textProperty().unbind();
            browser = openBrowser.getValue();
            messageArea.appendText("");
            logIntoGenerator();
        });

        messageArea.textProperty().bind(openBrowser.messageProperty());
        openHrf.setDisable(true);
        fillHrf.setVisible(true);
        new Thread(openBrowser).start();
    }

    private void logIntoGenerator() {
        LoginService loginService = new LoginService(browser, messageArea.getText(), loginData);

        loginService.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, event -> {
            messageArea.textProperty().unbind();
            browser = loginService.getValue();
            messageArea.appendText("");
        });

        messageArea.textProperty().bind(loginService.messageProperty());
        new Thread(loginService).start();
    }

    public void confirmAction() {
        GetLoginData getLoginData = new GetLoginData(messageArea.getText(), workbook.getSheet(HRFRobot.ROBOT_SHEET));

        getLoginData.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, event -> {
            messageArea.textProperty().unbind();
            loginData = getLoginData.getValue();
            messageArea.appendText("");
            getTasksData();
        });

        messageArea.textProperty().bind(getLoginData.messageProperty());
        confirm.setDisable(true);
        new Thread(getLoginData).start();
    }

    private void getTasksData() {
        ReadTasks readTasks = new ReadTasks();
        readTasks.setRobotSheet(workbook.getSheet(HRFRobot.ROBOT_SHEET));
        readTasks.setTasks(new ArrayList<>());
        readTasks.setPrevMessage(messageArea.getText());

        readTasks.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, event -> {
            messageArea.textProperty().unbind();
            taskProgress.progressProperty().unbind();
            tasks = readTasks.getValue();
            messageArea.appendText("");
            getCostsData();
        });

        messageArea.textProperty().bind(readTasks.messageProperty());
        taskProgress.progressProperty().bind(readTasks.progressProperty());
        new Thread(readTasks).start();
    }

    private void getCostsData() {
        ReadCost readCost = new ReadCost();
        readCost.setPrevMessage(messageArea.getText());
        readCost.setRobotSheet(workbook.getSheet(HRFRobot.ROBOT_SHEET));
        readCost.setCosts(new ArrayList<>());

        readCost.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, event -> {
            messageArea.textProperty().unbind();
            taskProgress.progressProperty().unbind();
            costs = readCost.getValue();
            messageArea.appendText("");
            getLumpCostsData();
        });

        messageArea.textProperty().bind(readCost.messageProperty());
        taskProgress.progressProperty().bind(readCost.progressProperty());
        new Thread(readCost).start();
    }

    private void getLumpCostsData() {
        ReadLumpCost readLumpCost = new ReadLumpCost();
        readLumpCost.setPrevMessage(messageArea.getText());
        readLumpCost.setRobotSheet(workbook.getSheet(HRFRobot.ROBOT_SHEET));
        readLumpCost.setLumpCosts(new ArrayList<>());

        readLumpCost.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, event -> {
            messageArea.textProperty().unbind();
            taskProgress.progressProperty().unbind();
            lumpCosts = readLumpCost.getValue();
            messageArea.appendText("");
            getPromoTasks();
        });

        messageArea.textProperty().bind(readLumpCost.messageProperty());
        taskProgress.progressProperty().bind(readLumpCost.progressProperty());
        new Thread(readLumpCost).start();
    }

    private void getPromoTasks() {
        ReadPromoTasks readPromoTasks = new ReadPromoTasks();
        readPromoTasks.setPrevMessage(messageArea.getText());
        readPromoTasks.setRobotSheet(workbook.getSheet(HRFRobot.ROBOT_SHEET));
        readPromoTasks.setPromoTasks(new ArrayList<>());

        readPromoTasks.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, event -> {
            messageArea.textProperty().unbind();
            taskProgress.progressProperty().unbind();
            promoTasks = readPromoTasks.getValue();
            messageArea.appendText("");
            getDetailCosts();
        });

        messageArea.textProperty().bind(readPromoTasks.messageProperty());
        taskProgress.progressProperty().bind(readPromoTasks.progressProperty());
        new Thread(readPromoTasks).start();
    }

    private void getDetailCosts() {
        ReadDetailCost readDetailCost = new ReadDetailCost();
        readDetailCost.setPrevMessage(messageArea.getText());
        readDetailCost.setRobotSheet(workbook.getSheet(HRFRobot.ROBOT_SHEET));
        readDetailCost.setDetailCosts(new ArrayList<>());

        readDetailCost.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, e -> {
            messageArea.textProperty().unbind();
            taskProgress.progressProperty().unbind();
            detailCosts = readDetailCost.getValue();
            confirm.setVisible(false);
            openHrf.setVisible(true);
            messageArea.appendText("");
        });

        messageArea.textProperty().bind(readDetailCost.messageProperty());
        taskProgress.progressProperty().bind(readDetailCost.progressProperty());
        new Thread(readDetailCost).start();
    }

    public void chooseFileAction() {
        LoadXLSFile loadXLSFile = new LoadXLSFile(new XLSFileChooser(new FileChooser()).chooseFile());

        confirm.setVisible(true);
        openHrf.setVisible(false);

        loadXLSFile.setPrevText(messageArea.getText());
        if(loadXLSFile.getHrfFile() != null) {
            chosenFile.setText(loadXLSFile.getHrfFile().getName());

            loadXLSFile.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, event -> {
                messageArea.textProperty().unbind();
                workbook = loadXLSFile.getValue();
                if(workbook != null && workbook.getSheet(HRFRobot.ROBOT_SHEET) != null) {
                    confirm.setDisable(false);
                } else {
                    messageArea.appendText("\nNie znaleziono arkusza robot!\n\tWygeneruj go w excelu: Menu Główne -> Robot -> Generuj\n" +
                            "\ti spróbuj ponownie");
                }
                chooseFile.setDisable(false);
            });

            messageArea.textProperty().bind(loadXLSFile.messageProperty());
            chooseFile.setDisable(true);
            new Thread(loadXLSFile).start();
        } else {
            messageArea.appendText("\nNie wybrano pliku.\n\tWybierz plik i spróbuj ponownie");
        }

    }

    public HRFRobot.RunMode getRunMode() {
        return runMode;
    }

    public void initialize(URL location, ResourceBundle resources) {
        runMode = HRFRobot.RunMode.RAW;
        chosenFile.setText("Wybierz plik aby móc kontynuować");
        messageArea.textProperty().addListener((observable, oldValue, newValue) -> messageArea.setScrollTop(Double.MAX_VALUE));
        messageArea.appendText("Aplikacja HRFRobot służy do automatycznego wypełniania wniosków.");
        taskProgress.setProgress(0);
        taskName.setText("Postęp aktualnego zadania:");
        confirm.setDisable(true);
        openHrf.setVisible(false);
        fillHrf.setVisible(false);
        initializeEditionModeCheckBox();
    }

    private void initializeEditionModeCheckBox() {
        ifEditionMode.selectedProperty().addListener((observable, oldValue, newValue) -> {
            runMode = newValue ? HRFRobot.RunMode.EDITION : HRFRobot.RunMode.RAW;
            chooseFile.setDisable(newValue);
        });
    }

    public XSSFWorkbook getWorkbook() { return workbook; }
    public LoginData getLoginData() { return loginData; }
    public ArrayList<Task> getTasks() { return tasks; }
    public ArrayList<Cost> getCosts() { return costs; }
}