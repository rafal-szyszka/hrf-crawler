package pl.cccenter.robot.hrf;

/**
 * Created by Rafał Szyszka on 26.04.2018.
 */
public abstract class HRFItem {

    protected String atrString;

    protected HRFItem(String atrString) {
        this.atrString = atrString;
    }

    public String getAtrString() {
        return atrString;
    }

    public void setAtrString(String atrString) {
        this.atrString = atrString;
    }

    public String[] splitToAtr(){
        return atrString.split("<next>");
    }
}
