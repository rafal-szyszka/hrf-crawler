package pl.cccenter.robot.tasks.helpers;

import org.apache.poi.xssf.usermodel.XSSFChartSheet;
import pl.cccenter.robot.excel.SimpleSheet;
import pl.cccenter.robot.hrf.Cost;
import pl.cccenter.robot.hrf.HRFItem;
import pl.cccenter.robot.hrf.LumpCost;
import pl.cccenter.robot.hrf.Task;

import java.util.ArrayList;

/**
 * Created by Rafał Szyszka on 26.04.2018.
 */
public class HRFItemReader {

    public static final String TAG_COL = "A";
    public static final String VALUE_COL = "B";

    public enum SearchType {TASK, COST, LUMP_COST}

    public static class ItemTypes {
        public static final String TASK = "TASK";
        public static final String TASK_AMOUNT = "TASK_AMOUNT";
        public static final String COST = "COST";
        public static final String COST_AMOUNT = "COST_AMOUNT";
        public static final String LUMP_COST = "L_COST";
        public static final String LUMP_COST_AMOUNT = "L_COST_AMOUNT";
        public static final String END = "END";
    }

    private SimpleSheet sheet;
    private ArrayList<HRFItem> items;

    public HRFItemReader(SimpleSheet sheet, ArrayList<HRFItem> items) {
        this.sheet = sheet;
        this.items = items;
    }

    public boolean read(int row, SearchType type) {
        String cellValue = sheet.getCellStringValue(TAG_COL + row);
        switch (type) {
            case TASK:
                if(cellValue.equals(ItemTypes.TASK)) {
                    items.add(new Task(sheet.getCellStringValue(VALUE_COL + row)));
                    return true;
                }
                break;
            case COST:
                if(cellValue.equals(ItemTypes.COST)) {
                    items.add(new Cost(sheet.getCellStringValue(VALUE_COL + row)));
                    return true;
                }
                break;
            case LUMP_COST:
                if(cellValue.equals(ItemTypes.LUMP_COST)) {
                    items.add(new LumpCost(sheet.getCellStringValue(VALUE_COL + row)));
                    return true;
                }
                break;
            default:
                throw new IllegalArgumentException("Type unknown: " + type);
        }
        return false;
    }

}
