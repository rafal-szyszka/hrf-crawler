package pl.cccenter.robot.hrf;

/**
 * Created by Rafał Szyszka on 07.03.2019.
 */
public class DetailCost extends HRFItem {

    public static final Integer TASK = 0;
    public static final Integer DESCRIPTION = 1;
    public static final Integer CATALOG = 2;
    public static final Integer QUALIFIED_SUM = 3;
    public static final Integer DETAILS = 4;

    public DetailCost(String atrString) {
        super(atrString);
    }
}
