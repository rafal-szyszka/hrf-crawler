package pl.cccenter.robot.hrf;

/**
 * Created by Rafał Szyszka on 02.03.2019.
 */
public class PromoTask extends HRFItem {

    public static final Integer NAME = 0;
    public static final Integer COUNTRY = 1;
    public static final Integer YEAR = 2;
    public static final Integer WWW_SITE = 3;
    public static final Integer IS_NATIONAL_STAND = 4;

    public PromoTask(String atrString) {
        super(atrString);
    }
}
