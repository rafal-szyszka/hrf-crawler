package pl.cccenter.robot.tasks;

import javafx.concurrent.Task;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import pl.cccenter.robot.excel.SimpleSheet;
import pl.cccenter.robot.hrf.LumpCost;
import pl.cccenter.robot.tasks.helpers.HRFItemReader;

import java.util.ArrayList;

/**
 * Created by Rafał Szyszka on 26.04.2018.
 */
public class ReadLumpCost extends Task<ArrayList<LumpCost>> {

    private static final String TAG_COL = "A";
    private static final String VALUE_COL = "B";

    private XSSFSheet robotSheet;
    private ArrayList<LumpCost> lumpCosts;
    private String prevMessage;
    private SimpleSheet simpleRobot;

    public ReadLumpCost() {}

    @Override
    protected ArrayList<LumpCost> call() throws Exception {
        simpleRobot = new SimpleSheet(robotSheet);
        int row = 3;
        int costAmount = 0;
        int costAdded = 0;

        prevMessage += "\nWczytuję koszty ryczałtowe...";
        updateMessage(prevMessage);

        String cellValue = simpleRobot.getCellStringValue(TAG_COL + row);
        while(!cellValue.equals(HRFItemReader.ItemTypes.END)) {

            if(cellValue.equals(HRFItemReader.ItemTypes.LUMP_COST_AMOUNT)) {
                costAmount = simpleRobot.getCellIntegerValue(VALUE_COL + row);
            } else if(cellValue.equals(HRFItemReader.ItemTypes.LUMP_COST)) {
                lumpCosts.add(new LumpCost(simpleRobot.getCellStringValue(VALUE_COL + row)));

                updateMessage(prevMessage + ++costAdded + "/" + costAmount);
                updateProgress(costAdded, costAmount);
            }
            row++;
            cellValue = simpleRobot.getCellStringValue(TAG_COL + row);
        }

        prevMessage += "\t [-- OK --]";
        updateMessage(prevMessage);
        prevMessage += "\n\tIlość kosztów ryczałtowych:\t" + costAmount;
        updateMessage(prevMessage);
        return lumpCosts;
    }

    public void setRobotSheet(XSSFSheet robotSheet) {
        this.robotSheet = robotSheet;
    }

    public void setLumpCosts(ArrayList<LumpCost> lumpCosts) {
        this.lumpCosts = lumpCosts;
    }

    public void setPrevMessage(String prevMessage) {
        this.prevMessage = prevMessage;
    }

    public void setSimpleRobot(SimpleSheet simpleRobot) {
        this.simpleRobot = simpleRobot;
    }
}