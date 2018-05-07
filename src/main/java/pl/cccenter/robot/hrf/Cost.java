package pl.cccenter.robot.hrf;

/**
 * Created by Squier on 2016-10-15.
 */
public class Cost extends HRFItem {

    public static final int TASK = 0;
    public static final int CATEGORY = 1;
    public static final int NAME = 2;
    public static final int DESCRIPTION = 3;
    public static final int COSTS = 4;
    public static final int QUALIFIED_COSTS = 5;
    public static final int VAT = 6;
    public static final int SUBSIDY = 7;

    public Cost(String atrString) {
        super(atrString);
    }
}
