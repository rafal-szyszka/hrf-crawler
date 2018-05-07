package pl.cccenter.robot.hrf;

/**
 * Created by Squier on 2016-10-15.
 */
public class Task extends HRFItem {

    public static final int NAME = 0;
    public static final int DESCRIPTION = 1;
    public static final int START_DATE = 2;
    public static final int END_DATE = 3;

    public Task(String atrString) {
        super(atrString);
    }
}
