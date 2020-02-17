package pl.cccenter.robot.tasks;

import javafx.concurrent.Task;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import pl.cccenter.robot.excel.SimpleSheet;
import pl.cccenter.robot.hrf.DetailCost;
import pl.cccenter.robot.hrf.PromoTask;
import pl.cccenter.robot.tasks.helpers.HRFItemReader;

import java.util.ArrayList;

/**
 * Created by Rafał Szyszka on 07.03.2019.
 */
public class ReadDetailCost extends Task<ArrayList<DetailCost>> {

    private static final String TAG_COL = "A";
    private static final String VALUE_COL = "B";

    private XSSFSheet robotSheet;
    private ArrayList<DetailCost> detailCosts;
    private String prevMessage;
    private SimpleSheet simpleRobot;

    @Override
    public ArrayList<DetailCost> call() throws Exception {
        simpleRobot = new SimpleSheet(robotSheet);
        int row = 3;
        int detailCostAmount = 0;
        int detailCostAdded = 0;

        prevMessage += "\nWczytuję koszta szczegółowe...";
        updateMessage(prevMessage);

        String cellValue = simpleRobot.getCellStringValue(TAG_COL + row);
        while (!cellValue.equals(HRFItemReader.ItemTypes.END)) {

            if (cellValue.equals(HRFItemReader.ItemTypes.DETAIL_COST_AMOUNT)) {
                detailCostAmount = simpleRobot.getCellIntegerValue(VALUE_COL + row);
            } else if (cellValue.equals(HRFItemReader.ItemTypes.DETAIL_COST)) {
                detailCosts.add(new DetailCost(simpleRobot.getCellStringValue(VALUE_COL + row)));

                updateMessage(prevMessage + ++detailCostAdded + "/" + detailCostAmount);
                updateProgress(detailCostAdded, detailCostAmount);
            }
            row++;
            cellValue = simpleRobot.getCellStringValue(TAG_COL + row);
        }

        prevMessage += "\t [-- OK --]";
        updateMessage(prevMessage);
        prevMessage += "\n\tIlość kosztów szczegółowych:\t" + detailCostAmount;
        updateMessage(prevMessage);
        return detailCosts;
    }

    public void setRobotSheet(XSSFSheet robotSheet) {
        this.robotSheet = robotSheet;
    }

    public void setDetailCosts(ArrayList<DetailCost> detailCosts) {
        this.detailCosts = detailCosts;
    }

    public void setPrevMessage(String prevMessage) {
        this.prevMessage = prevMessage;
    }
}
