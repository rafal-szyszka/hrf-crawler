package pl.cccenter.robot.web;

import javafx.concurrent.Task;
import pl.cccenter.robot.web.FirefoxBrowser;

/**
 * Created by Squier on 2016-10-16.
 */
public class OpenBrowser extends Task<FirefoxBrowser> {

    private FirefoxBrowser browser;
    private String prevMessage;

    public OpenBrowser(FirefoxBrowser browser, String prevMessage) {
        this.browser = browser;
        this.prevMessage = prevMessage;
    }

    @Override
    protected FirefoxBrowser call() throws Exception {
        prevMessage += "\nInicjalizuję Firefox'a...";
        updateMessage(prevMessage);

        browser.initFirefox();

        prevMessage += "\t [-- OK --]";
        updateMessage(prevMessage);
        return browser;
    }
}
