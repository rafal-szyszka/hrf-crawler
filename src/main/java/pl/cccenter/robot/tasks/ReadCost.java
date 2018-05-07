package pl.cccenter.robot.tasks;

import javafx.concurrent.Task;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import pl.cccenter.robot.excel.SimpleSheet;
import pl.cccenter.robot.hrf.Cost;
import pl.cccenter.robot.tasks.helpers.HRFItemReader;

import java.util.ArrayList;

/**
 * Created by Squier on 2016-10-15.
 */
public class ReadCost extends Task<ArrayList<Cost>>{

    private XSSFSheet robotSheet;
    private ArrayList<Cost> costs;
    private String prevMessage;
    private SimpleSheet simpleRobot;

    public ReadCost() {}

    @Override
    protected ArrayList<Cost> call() throws Exception {
        simpleRobot = new SimpleSheet(robotSheet);
        int row = 3;
        int costAmount = 0;
        int costAdded = 0;

        prevMessage += "\nWczytuję koszty...";
        updateMessage(prevMessage);

        String cellValue = simpleRobot.getCellStringValue(HRFItemReader.TAG_COL + row);
        while(!cellValue.equals(HRFItemReader.ItemTypes.END)) {

            if(cellValue.equals(HRFItemReader.ItemTypes.COST_AMOUNT)) {
                costAmount = simpleRobot.getCellIntegerValue(HRFItemReader.VALUE_COL + row);
            } else if(cellValue.equals(HRFItemReader.ItemTypes.COST)) {
                costs.add(new Cost(simpleRobot.getCellStringValue(HRFItemReader.VALUE_COL + row)));

                updateMessage(prevMessage + ++costAdded + "/" + costAmount);
                updateProgress(costAdded, costAmount);
            }
            row++;
            cellValue = simpleRobot.getCellStringValue(HRFItemReader.TAG_COL + row);
        }

        prevMessage += "\t [-- OK --]";
        updateMessage(prevMessage);
        prevMessage += "\n\tIlość kosztów:\t" + costAmount;
        updateMessage(prevMessage);
        return costs;
    }

    public void setRobotSheet(XSSFSheet robotSheet) {
        this.robotSheet = robotSheet;
    }

    public void setCosts(ArrayList<Cost> costs) {
        this.costs = costs;
    }

    public void setPrevMessage(String prevMessage) {
        this.prevMessage = prevMessage;
    }

    public void setSimpleRobot(SimpleSheet simpleRobot) {
        this.simpleRobot = simpleRobot;
    }
}
