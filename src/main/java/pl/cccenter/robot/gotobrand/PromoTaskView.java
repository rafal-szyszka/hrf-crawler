package pl.cccenter.robot.gotobrand;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.cccenter.robot.gotobrand.controller.PromoTaskController;
import pl.cccenter.robot.gotobrand.controller.TaskViewController;
import pl.cccenter.robot.hrf.*;
import pl.cccenter.robot.web.FirefoxBrowser;
import pl.cccenter.robot.web.LoginData;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Rafał Szyszka on 02.03.2019.
 */
public class PromoTaskView {

    private ArrayList<PromoTask> promoTasks;
    private ArrayList<Task> tasks;
    private ArrayList<Cost> costs;
    private ArrayList<LumpCost> lumpCosts;
    private ArrayList<DetailCost> detailCosts;
    private FirefoxBrowser browser;
    private LoginData loginData;

    PromoTaskView(ArrayList<PromoTask> promoTasks, ArrayList<Task> tasks, ArrayList<Cost> costs, ArrayList<LumpCost> lumpCosts, ArrayList<DetailCost> detailCosts, FirefoxBrowser browser, LoginData loginData) {
        this.promoTasks = promoTasks;
        this.tasks = tasks;
        this.costs = costs;
        this.lumpCosts = lumpCosts;
        this.detailCosts = detailCosts;
        this.browser = browser;
        this.loginData = loginData;
    }

    public void show() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gotobrand/PromoTaskView.fxml"));
        Parent root = fxmlLoader.load();

        PromoTaskController controller = fxmlLoader.getController();
        controller.setBrowser(browser);
        controller.setPromoTasks(promoTasks);
        controller.setTasks(tasks);
        controller.setCosts(costs);
        controller.setLumpCosts(lumpCosts);
        controller.setDetailCosts(detailCosts);

        stage.setTitle("Wypełnij zadania");
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public static class Builder {
        private ArrayList<PromoTask> promoTasks;
        private ArrayList<Task> tasks;
        private ArrayList<Cost> costs;
        private ArrayList<LumpCost> lumpCosts;
        private FirefoxBrowser browser;
        private LoginData loginData;
        private ArrayList<DetailCost> detailCosts;

        public Builder setPromoTasks(ArrayList<PromoTask> promoTasks) {
            this.promoTasks = promoTasks;
            return this;
        }

        public Builder setTasks(ArrayList<Task> tasks) {
            this.tasks = tasks;
            return this;
        }

        public Builder setCosts(ArrayList<Cost> costs) {
            this.costs = costs;
            return this;
        }

        public Builder setLumpCosts(ArrayList<LumpCost> lumpCosts) {
            this.lumpCosts = lumpCosts;
            return this;
        }

        public Builder setBrowser(FirefoxBrowser browser) {
            this.browser = browser;
            return this;
        }

        public Builder setLoginData(LoginData loginData) {
            this.loginData = loginData;
            return this;
        }

        public PromoTaskView createPromoProgramView() {
            return new PromoTaskView(promoTasks, tasks, costs, lumpCosts, detailCosts, browser, loginData);
        }

        public Builder setDetailCosts(ArrayList<DetailCost> detailCosts) {
            this.detailCosts = detailCosts;
            return this;
        }
    }

}
