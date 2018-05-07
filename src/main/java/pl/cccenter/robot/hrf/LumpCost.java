package pl.cccenter.robot.hrf;

/**
 * Created by Rafał Szyszka on 26.04.2018.
 */
public class LumpCost extends HRFItem {

    public static int TASK = 0;
    public static int TYPE = 1;
    public static int NAME = 2;
    public static int POINTER_NAME = 3;
    public static int POINTER_VALUE = 4;
    public static int CATEGORY = 5;
    public static int QUALIFIED_COSTS = 6;
    public static int SUBSIDY = 7;
    public static int IS_LUMP = 8;

    public LumpCost(String atrString) {
        super(atrString);
    }
}
