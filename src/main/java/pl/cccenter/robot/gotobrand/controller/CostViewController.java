package pl.cccenter.robot.gotobrand.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.cccenter.robot.gotobrand.LumpCostView;
import pl.cccenter.robot.gotobrand.web.GTBCosts;
import pl.cccenter.robot.gotobrand.web.GTBPage;
import pl.cccenter.robot.hrf.Cost;
import pl.cccenter.robot.hrf.LumpCost;
import pl.cccenter.robot.web.FirefoxBrowser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ResourceBundle;

/**
 * Created by Rafał on 06.02.2017.
 */
public class CostViewController implements Initializable {

    @FXML
    private ComboBox<String> taskName;
    @FXML
    private ComboBox<String> costCat;
    @FXML
    private TextField costName;
    @FXML
    private TextArea costDesc;
    @FXML
    private TextField costInAll;
    @FXML
    private TextField qualifiedCost;
    @FXML
    private TextField vat;
    @FXML
    private TextField subsidy;

    @FXML
    private TextField taskNameOV;
    @FXML
    private TextField costCatOV;
    @FXML
    private TextField costNameOV;
    @FXML
    private TextArea costDescOV;
    @FXML
    private TextField costInAllOV;
    @FXML
    private TextField qualifiedCostOV;
    @FXML
    private TextField vatOV;
    @FXML
    private TextField subsidyOV;

    @FXML
    private Button prevCost;
    @FXML
    private Button changeCostData;
    @FXML
    private Button skipCost;
    @FXML
    private Button confirmAll;
    @FXML
    private Button nextCost;

    @FXML
    private ProgressBar progress;

    @FXML
    private Label message;

    private ArrayList<Cost> costs;
    private ArrayList<LumpCost> lumpCosts;
    private FirefoxBrowser browser;
    private ListIterator<Cost> costIterator;

    private int editionPlace;
    private boolean prevClicked;
    private boolean nextClicked;

    public CostViewController() {
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------
     */
    public void preparePage() {
        ArrayList<String> allowedData = (ArrayList<String>) browser.executeReturnScript(GTBPage.GET_ALLOWED_TNAMES);
        ObservableList<String> allowedTasks = FXCollections.observableArrayList();
        allowedData.forEach(allowedTasks::add);
        taskName.setItems(allowedTasks);

        allowedData = (ArrayList<String>) browser.executeReturnScript(GTBPage.GET_ALLOWED_COST_CATS);
        ObservableList<String> allowedCosts = FXCollections.observableArrayList();
        allowedData.forEach(allowedCosts::add);
        costCat.setItems(allowedCosts);

        costIterator = costs.listIterator();
        Cost toShow = costIterator.next();
        showCost(toShow);
        setOVEditable(true);
        showCostOV(toShow);
        setOVEditable(false);
        editionPlace = 0;

        disableButtons(false);
    }

    public void prevCostAction() {
        if (costIterator.hasPrevious()) {
            if (nextClicked) {
                costIterator.previous();
                nextClicked = false;
            }
            Cost cost = costIterator.previous();
            showCost(cost);
            setOVEditable(true);
            showCostOV(cost);
            setOVEditable(false);
            prevClicked = true;
            editionPlace--;
        } else {
            prevCost.setDisable(true);
        }
    }

    public void skipCostAction() {
        nextCostAction();
    }

    public void changeCostDataAction() {
        costs.get(editionPlace).setAtrString(createAtrString());
        setOVEditable(true);
        showCostOV(costs.get(editionPlace));
        setOVEditable(false);
    }

    public void nextCostAction() {
        if(costIterator.hasNext()) {
            if(prevClicked) {
                costIterator.next();
                prevClicked = false;
            }
            Cost cost = costIterator.next();
            showCost(cost);
            setOVEditable(true);
            showCostOV(cost);
            setOVEditable(false);
        } else {
            Cost emptyCost = new Cost("null<next>null<next>null<next>Brak dalszych kosztów, zatwierdź wszystkie aby zakończyć");
            showCost(emptyCost);
            changeCostData.setDisable(true);
        }
        if(costIterator.hasPrevious()) {
            prevCost.setDisable(false);
        }
        if(editionPlace < costs.size()) {
            changeCostData.setDisable(false);
        }
    }

    public void confirmAllAction() {
        ArrayList<String[]> costAtr = new ArrayList<>();
        costs.forEach(cost -> costAtr.add(cost.splitToAtr()));

        GTBCosts gtbCosts = new GTBCosts(costAtr, browser);
        gtbCosts.setPrevMessage("Wypełnione zadanie: ");

        gtbCosts.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, event -> {
            message.textProperty().unbind();
            progress.progressProperty().unbind();
            message.setText("");
            ((Stage)prevCost.getScene().getWindow()).close();
            try {
                if(lumpCosts != null && lumpCosts.size() > 0) {
                    (new LumpCostView()).show(lumpCosts, browser);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        message.textProperty().bind(gtbCosts.messageProperty());
        progress.progressProperty().bind(gtbCosts.progressProperty());

        new Thread(gtbCosts).start();/*
        for (int i = 0; i < costAtr.size(); i++) {
            String[] costAt = costAtr.get(i);
            String script = GTBCosts.FILL_COST_SCRIPT.replaceAll("arg1", Integer.toString(i));
            script = script.replace("arg2", costAt[0].equals("null") ? "" : costAt[0]);
            script = script.replace("arg3", costAt[1].equals("null") ? "" : costAt[1]);
            script = script.replace("arg4", costAt[2].equals("null") ? "" : costAt[2]);
            script = script.replace("arg5", costAt[3].equals("null") ? "" : costAt[3]);
            script = script.replace("arg6", costAt[4].equals("null") ? "" : costAt[4]);
            script = script.replace("arg7", costAt[5].equals("null") ? "" : costAt[5]);
            script = script.replace("arg8", costAt[6].equals("null") ? "" : costAt[6]);
            script = script.replace("arg9", costAt[7].equals("null") ? "" : costAt[7]);
            System.out.println(script + "\n\n");
            browser.executeVoidScript(script);
        }*/
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------
     */

    private String createAtrString() {
        StringBuilder builder = new StringBuilder();
        builder.append(taskName.getValue().length() == 0 ? "null" : taskName.getValue());
        builder.append("<next>");
        builder.append(costCat.getValue().length() == 0 ? "null" : costCat.getValue());
        builder.append("<next>");
        builder.append(costName.getText().isEmpty() ? "null" : costName.getText());
        builder.append("<next>");
        builder.append(costDesc.getText().isEmpty() ? "null" : costDesc.getText());
        builder.append("<next>");
        builder.append(costInAll.getText().isEmpty() ? "null" : costInAll.getText());
        builder.append("<next>");
        builder.append(qualifiedCost.getText().isEmpty() ? "null" : qualifiedCost.getText());
        builder.append("<next>");
        builder.append(vat.getText().isEmpty() ? "null" : vat.getText());
        builder.append("<next>");
        builder.append(subsidy.getText().isEmpty() ? "null" : subsidy.getText());
        return builder.toString();
    }

    private void showCost(Cost cost) {
        String[] atr = cost.splitToAtr();
        taskName.setValue(atr[0].equals("null") ? "" : atr[0]);
        costCat.setValue(atr[1].equals("null") ? "" : atr[1]);
        costName.setText(atr[2].equals("null") ? "" : atr[2]);
        costDesc.setText(atr[3].equals("null") ? "" : atr[3]);
        costInAll.setText(atr[4].equals("null") ? "" : atr[4]);
        qualifiedCost.setText(atr[5].equals("null") ? "" : atr[5]);
        vat.setText(atr[6].equals("null") ? "" : atr[6]);
        subsidy.setText(atr[7].equals("null") ? "" : atr[7]);
    }

    private void showCostOV(Cost cost) {
        String[] atr = cost.splitToAtr();
        taskNameOV.setText(atr[0].equals("null") ? "" : atr[0]);
        costCatOV.setText(atr[1].equals("null") ? "" : atr[1]);
        costNameOV.setText(atr[2].equals("null") ? "" : atr[2]);
        costDescOV.setText(atr[3].equals("null") ? "" : atr[3]);
        costInAllOV.setText(atr[4].equals("null") ? "" : atr[4]);
        qualifiedCostOV.setText(atr[5].equals("null") ? "" : atr[5]);
        vatOV.setText(atr[6].equals("null") ? "" : atr[6]);
        subsidyOV.setText(atr[7].equals("null") ? "" : atr[7]);
    }

    private void setOVEditable(boolean editable) {
        taskNameOV.setEditable(editable);
        costCatOV.setEditable(editable);
        costNameOV.setEditable(editable);
        costDescOV.setEditable(editable);
        costInAllOV.setEditable(editable);
        qualifiedCostOV.setEditable(editable);
        vatOV.setEditable(editable);
        subsidyOV.setEditable(editable);
    }

    private void disableButtons(boolean disable) {
        prevCost.setDisable(disable);
        changeCostData.setDisable(disable);
        skipCost.setDisable(disable);
        confirmAll.setDisable(disable);
        nextCost.setDisable(disable);
    }

    public void setCosts(ArrayList<Cost> costs) {
        this.costs = costs;
    }

    public void setBrowser(FirefoxBrowser browser) {
        this.browser = browser;
    }

    public void setLumpCosts(ArrayList<LumpCost> lumpCosts) {
        this.lumpCosts = lumpCosts;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setOVEditable(false);
        disableButtons(true);
        progress.setProgress(0);
        message.setText("");
    }
}
