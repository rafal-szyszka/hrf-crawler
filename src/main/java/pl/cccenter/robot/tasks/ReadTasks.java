package pl.cccenter.robot.tasks;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import pl.cccenter.robot.excel.SimpleSheet;
import pl.cccenter.robot.hrf.Task;
import pl.cccenter.robot.tasks.helpers.HRFItemReader;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Squier on 2016-10-12.
 */
public class ReadTasks extends javafx.concurrent.Task<ArrayList<Task>> {

    public static final int OFFSET = 3;

    private XSSFSheet robotSheet;
    private ArrayList<Task> tasks;
    private String prevMessage;
    private SimpleSheet simpleRobot;

    public ReadTasks() {}

    @Override
    protected ArrayList<Task> call() throws Exception {
        int taskAmount;
        simpleRobot = new SimpleSheet(robotSheet);
        taskAmount = simpleRobot.getCellIntegerValue("B2");

        prevMessage += "\nWczytuję zadania...";
        updateProgress(0, taskAmount);
        updateMessage(prevMessage);

        for (int i = 0; i < taskAmount; i++) {
            tasks.add(getTaskFromSheet(HRFItemReader.VALUE_COL + (i + OFFSET)));

            updateMessage(prevMessage + (i+1) + "/" + taskAmount);
            updateProgress(i + 1, taskAmount);
        }

        prevMessage += "\t [-- OK --]";
        updateMessage(prevMessage);
        prevMessage += "\n\tIlość zadań:\t" + taskAmount;
        updateMessage(prevMessage);
        return tasks;
    }

    private Task getTaskFromSheet(String taskCell) throws IOException {
        return new Task(simpleRobot.getCellStringValue(taskCell));
    }

    public void setRobotSheet(XSSFSheet robotSheet) {
        this.robotSheet = robotSheet;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void setPrevMessage(String prevMessage) {
        this.prevMessage = prevMessage;
    }

}