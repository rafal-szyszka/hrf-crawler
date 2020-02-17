package pl.cccenter.robot.gotobrand.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.openqa.selenium.UnhandledAlertException;
import pl.cccenter.robot.gotobrand.CostView;
import pl.cccenter.robot.gotobrand.LumpCostView;
import pl.cccenter.robot.gotobrand.web.GTBTasks;
import pl.cccenter.robot.gotobrand.web.GTBPage;
import pl.cccenter.robot.hrf.Cost;
import pl.cccenter.robot.hrf.DetailCost;
import pl.cccenter.robot.hrf.LumpCost;
import pl.cccenter.robot.hrf.Task;
import pl.cccenter.robot.web.FirefoxBrowser;
import pl.cccenter.robot.web.LoginData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ResourceBundle;

/**
 * Created by Squier on 2016-10-16.
 */
public class TaskViewController implements Initializable {

    @FXML
    private TextField taskNameOV;
    @FXML
    private TextArea taskDescOV;
    @FXML
    private TextField taskStartDateOV;
    @FXML
    private TextField taskEndDateOV;

    @FXML
    private ComboBox<String> taskName;
    @FXML
    private TextArea taskDesc;
    @FXML
    private TextField taskStartDate;
    @FXML
    private TextField taskEndDate;

    @FXML
    private Button prevTask;
    @FXML
    private Button skipTask;
    @FXML
    private Button changeTaskData;
    @FXML
    private Button nextTask;
    @FXML
    private Button confirmAll;
    @FXML
    private Button fillHRF;

    private FirefoxBrowser browser;
    private ArrayList<Task> tasks;

    private ListIterator<Task> taskIterator;
    private int editionPlace;
    private boolean prevClicked;
    private boolean nextClicked;
    private ArrayList<Cost> costs;
    private ArrayList<LumpCost> lumpCosts;
    private LoginData loginData;
    private ArrayList<DetailCost> detailCosts;

    public TaskViewController() {

    }

    public void prevTaskAction() {
        if (taskIterator.hasPrevious()) {
            if (nextClicked) {
                taskIterator.previous();
                nextClicked = false;
            }
            Task task = taskIterator.previous();
            showTask(task);
            setOverViewEditable(true);
            showTaskOV(task);
            setOverViewEditable(false);
            prevClicked = true;
            editionPlace--;
        } else {
            prevTask.setDisable(true);
        }
    }

    public void skipTaskAction() {
        nextTaskAction();
    }

    public void changeTaskData() {
        tasks.get(editionPlace).setAtrString(createAtrString());
        setOverViewEditable(true);
        showTaskOV(tasks.get(editionPlace));
        setOverViewEditable(false);
    }

    private String createAtrString() {
        boolean availableTaskName = false;
        StringBuilder builder = new StringBuilder();

        for (String name : taskName.getItems()) {
            if (name.equals(taskName.getValue())) {
                availableTaskName = true;
                break;
            }
        }

        builder.append(availableTaskName ? taskName.getValue() : taskName.getValue());
        builder.append("<next>");
        builder.append(taskDesc.getText().equals("") ? "null" : taskDesc.getText());
        builder.append("<next>");
        builder.append(taskStartDate.getText().equals("") ? "null" : taskStartDate.getText());
        builder.append("<next>");
        builder.append(taskEndDate.getText().equals("") ? "null" : taskEndDate.getText());
        return builder.toString();
    }

    public void clearPrevious() {
        deleteCost();
    }

    public void deleteCost() {
        try {
            browser.executeVoidScript(GTBPage.REMOVE_CURRENT_TASKS_COSTS);
            disableEditButtons(true);
        } catch (UnhandledAlertException unhandledAlert) {
            browser.getBrowser().switchTo().alert().accept();
            deleteCost();
        }
    }

    public void nextTaskAction() {
        if (taskIterator.hasNext()) {
            if (prevClicked) {
                taskIterator.next();
                prevClicked = false;
            }
            Task task = taskIterator.next();
            editionPlace++;
            nextClicked = true;
            showTask(task);
            setOverViewEditable(true);
            showTaskOV(task);
            setOverViewEditable(false);
        } else {
            Task emptyTask = new Task("null<next>Brak dalszych zadań\nZaakceptuj aby zakończyć<next>null<next>null");
            showTask(emptyTask);
            changeTaskData.setDisable(true);
        }
        if (taskIterator.hasPrevious()) {
            prevTask.setDisable(false);
        }
        if (editionPlace < tasks.size()) {
            changeTaskData.setDisable(false);
        }
    }

    public void preparePage() {
        initializePage();
        taskIterator = tasks.listIterator();
        editionPlace = 0;
        prevTask.setDisable(true);
        Task task = taskIterator.next();
        showTask(task);
        setOverViewEditable(true);
        showTaskOV(task);
        setOverViewEditable(false);
        fillHRF.setDisable(true);
    }

    private void showTask(Task task) {
        String[] atr = task.splitToAtr();
        taskName.setValue(atr[0].equals("null") ? "" : atr[0]);
        taskDesc.setText(atr[1].equals("null") ? "" : atr[1]);
        taskStartDate.setText(atr[2].equals("null") ? "" : atr[2]);
        taskEndDate.setText(atr[3].equals("null") ? "" : atr[3]);
    }

    private void showTaskOV(Task task) {
        String[] atr = task.splitToAtr();
        taskNameOV.setText(atr[0].equals("null") ? "" : atr[0]);
        taskDescOV.setText(atr[1].equals("null") ? "" : atr[1]);
        taskStartDateOV.setText(atr[2].equals("null") ? "" : atr[2]);
        taskEndDateOV.setText(atr[3].equals("null") ? "" : atr[3]);
    }

    public void confirmAllAction() {
        //fixNewLines();

        ArrayList<String[]> arrayTasks = new ArrayList<>();
        tasks.forEach(task -> {
            System.out.println(task.toString());
            arrayTasks.add(task.splitToAtr());
        });
        GTBTasks motion = new GTBTasks(arrayTasks, browser);

        motion.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, e -> {
            ((Stage) taskName.getScene().getWindow()).close();
            try {
                if (costs != null && costs.size() > 0) {
                    (new CostView()).show(costs, lumpCosts, detailCosts, browser);
                } else {
                    (new LumpCostView()).show(lumpCosts, detailCosts, browser);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        (new Thread(motion)).start();
        /*for (int i = 0; i < arrayTasks.size(); i++) {
            String[] task = arrayTasks.get(i);
            String script = GTBTasks.FILL_TASK_SCRIPT.replaceAll("arg1", Integer.toString(i));
            script = script.replaceAll("arg2", task[0].equals("null") ? "" : task[0]);
            script = script.replace("arg3", task[1].equals("null") ? "" : task[1]);
            script = script.replace("arg4", task[2].equals("null") ? "" : task[2]);
            script = script.replace("arg5", task[3].equals("null") ? "" : task[3]);
            script = script.replace("arg6", task[4].equals("null") ? "" : task[4]);
            script = script.replaceAll("\n", " ");
            System.out.println(script + "\n\n");
            browser.executeVoidScript(script);
        }*/
    }

    private void fixNewLines() {
        taskIterator = tasks.listIterator();
        editionPlace = 0;
        prevTask.setDisable(true);
        Task task = taskIterator.next();
        showTask(task);
        setOverViewEditable(true);
        showTaskOV(task);
        setOverViewEditable(false);
        while (taskIterator.hasNext()) {
            if (editionPlace < tasks.size()) {
                changeTaskData();
            }
            nextTaskAction();
            if (editionPlace == tasks.size() - 1) {
                changeTaskData();
            }
        }
    }

    public void setBrowser(FirefoxBrowser browser) {
        this.browser = browser;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableEditButtons(true);
        setOverViewEditable(false);
    }

    private void initializePage() {
        GTBPage pageInitializer = new GTBPage.Builder()
                .setTaskCount(tasks.size())
                .setPromoTasksAmount(0)
                .setCostCount(costs.size())
                .setLumpCostsAmount(lumpCosts.size())
                .setDetailCostAmount(detailCosts.size())
                .setBrowser(browser)
                .createGTBPage();

        pageInitializer.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, event -> {
            disableEditButtons(false);
            //setAllowedTaskNames();
        });

        (new Thread(pageInitializer)).start();

        /*String revealTasksScript = GTBPage.CLICK_ADD_BUTTON_SCRIPT.replace("arg1", GTBPage.ADD_TASK_BUTTON_INNER_HTML);
        revealTasksScript = revealTasksScript.replace("arg2", Integer.toString(tasks.size()));
        revealTasksScript = revealTasksScript.replace("arg3", GTBPage.ADD_COST_BUTTON_INNER_HTML);
        revealTasksScript = revealTasksScript.replace("arg4", Integer.toString(costs.size()));
        try {
            browser.executeVoidScript(revealTasksScript);
        } catch (UnhandledAlertException e) {
            try {
                browser.getBrowser().switchTo().alert().accept();
            } catch (NoAlertPresentException ex) {
                /* OK *//*
            }
        }
        /*browser.executeVoidScript(revealTasksScript);*/
    }

    private void setAllowedTaskNames() {
        ArrayList<String> allowed = (ArrayList<String>) browser.executeReturnScript(GTBPage.GET_ALLOWED_TASK_NAMES);
        ObservableList<String> allowedTaskNames = FXCollections.observableArrayList();
        allowedTaskNames.addAll(allowed);
        taskName.setItems(allowedTaskNames);
    }


    private void disableEditButtons(boolean disable) {
        prevTask.setDisable(disable);
        skipTask.setDisable(disable);
        changeTaskData.setDisable(disable);
        nextTask.setDisable(disable);
        confirmAll.setDisable(disable);
    }

    private void setOverViewEditable(boolean disable) {
        taskNameOV.setEditable(disable);
        taskDescOV.setEditable(disable);
        taskStartDateOV.setEditable(disable);
        taskEndDateOV.setEditable(disable);
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }

    public void setCosts(ArrayList<Cost> costs) {
        this.costs = costs;
    }

    public void setLumpCosts(ArrayList<LumpCost> lumpCosts) {
        this.lumpCosts = lumpCosts;
    }

    public void setDetailCosts(ArrayList<DetailCost> detailCosts) {
        this.detailCosts = detailCosts;
    }
}
