package pl.cccenter.robot.tasks;

import javafx.concurrent.Task;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import pl.cccenter.robot.excel.SimpleSheet;
import pl.cccenter.robot.hrf.LumpCost;
import pl.cccenter.robot.hrf.PromoTask;
import pl.cccenter.robot.tasks.helpers.HRFItemReader;

import java.util.ArrayList;

/**
 * Created by Rafał Szyszka on 02.03.2019.
 */
public class ReadPromoTasks extends Task<ArrayList<PromoTask>> {

    private static final String TAG_COL = "A";
    private static final String VALUE_COL = "B";

    private XSSFSheet robotSheet;
    private ArrayList<PromoTask> promoTasks;
    private String prevMessage;
    private SimpleSheet simpleRobot;

    @Override
    protected ArrayList<PromoTask> call() throws Exception {
        simpleRobot = new SimpleSheet(robotSheet);
        int row = 3;
        int promoTaskAmount = 0;
        int promoTaskAdded = 0;

        prevMessage += "\nWczytuję działania promocyjne...";
        updateMessage(prevMessage);

        String cellValue = simpleRobot.getCellStringValue(TAG_COL + row);
        while (!cellValue.equals(HRFItemReader.ItemTypes.END)) {

            if (cellValue.equals(HRFItemReader.ItemTypes.PROMO_TASK_AMOUNT)) {
                promoTaskAmount = simpleRobot.getCellIntegerValue(VALUE_COL + row);
            } else if (cellValue.equals(HRFItemReader.ItemTypes.PROMO_TASK)) {
                promoTasks.add(new PromoTask(simpleRobot.getCellStringValue(VALUE_COL + row)));

                updateMessage(prevMessage + ++promoTaskAdded + "/" + promoTaskAmount);
                updateProgress(promoTaskAdded, promoTaskAmount);
            }
            row++;
            cellValue = simpleRobot.getCellStringValue(TAG_COL + row);
        }

        prevMessage += "\t [-- OK --]";
        updateMessage(prevMessage);
        prevMessage += "\n\tIlość działań promocyjnych:\t" + promoTaskAmount;
        updateMessage(prevMessage);
        return promoTasks;
    }

    public void setRobotSheet(XSSFSheet robotSheet) {
        this.robotSheet = robotSheet;
    }

    public void setPromoTasks(ArrayList<PromoTask> promoTasks) {
        this.promoTasks = promoTasks;
    }

    public void setPrevMessage(String prevMessage) {
        this.prevMessage = prevMessage;
    }

    public void setSimpleRobot(SimpleSheet simpleRobot) {
        this.simpleRobot = simpleRobot;
    }

    public ArrayList<PromoTask> getPromoTasks() {
        return promoTasks;
    }
}
