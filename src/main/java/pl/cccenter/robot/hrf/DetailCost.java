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
//    public static final Integer MARKET_RESEARCH = 5;
    public static final Integer UNIT_AMOUNT = 5;
    public static final Integer PRICE = 6;
    public static final Integer PEOPLE_NUMBER = 7;
    public static final Integer DAY_NUMBER = 8;
    public static final Integer OVERNIGHT_NUMBER = 9;
    public static final Integer OFFER1_DATA = 10;
    public static final Integer OFFER2_DATA = 11;
    public static final Integer OFFER3_DATA = 12;
    public static final Integer RESEARCH_DATE = 13;

    public DetailCost(String atrString) {
        super(atrString);
    }
}
