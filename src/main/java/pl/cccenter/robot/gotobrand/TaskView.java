package pl.cccenter.robot.gotobrand;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.cccenter.robot.hrf.Cost;
import pl.cccenter.robot.hrf.LumpCost;
import pl.cccenter.robot.hrf.Task;
import pl.cccenter.robot.gotobrand.controller.TaskViewController;
import pl.cccenter.robot.web.FirefoxBrowser;
import pl.cccenter.robot.web.LoginData;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Squier on 2016-10-16.
 */
public class TaskView {

    private ArrayList<Task> tasks;
    private ArrayList<Cost> costs;
    private ArrayList<LumpCost> lumpCosts;
    private FirefoxBrowser browser;
    private LoginData loginData;

    private TaskView(ArrayList<Task> tasks, ArrayList<Cost> costs, ArrayList<LumpCost> lumpCosts, FirefoxBrowser browser, LoginData loginData) {
        this.tasks = tasks;
        this.costs = costs;
        this.lumpCosts = lumpCosts;
        this.browser = browser;
        this.loginData = loginData;
    }

    public void show() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gotobrand/TaskView.fxml"));
        Parent root = fxmlLoader.load();

        TaskViewController controller = fxmlLoader.getController();
        controller.setBrowser(browser);
        controller.setTasks(tasks);
        controller.setCosts(costs);
        controller.setLumpCosts(lumpCosts);
        controller.setLoginData(loginData);

        stage.setTitle("Wypełnij zadania");
        stage.setScene(new Scene(root, 841, 333));
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public static class Builder {
        private ArrayList<Task> tasks;
        private ArrayList<Cost> costs;
        private ArrayList<LumpCost> lumpCosts;
        private FirefoxBrowser browser;
        private LoginData loginData;

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

        public TaskView createTaskView() {
            return new TaskView(tasks, costs, lumpCosts, browser, loginData);
        }
    }
}
