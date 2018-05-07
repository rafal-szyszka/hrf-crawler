package pl.cccenter.robot.web;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by Squier on 2016-10-15.
 */
public class FirefoxBrowser {

    private WebDriver firefox;

    public FirefoxBrowser(WebDriver browser) {
        this.firefox = browser;
    }

    public FirefoxBrowser() {

    }

    public void initFirefox() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        String firefox_binary_path = "C:\\Firefox\\firefox.exe";
        //capabilities.setCapability("marionette", true);
        capabilities.setCapability("firefox_binary", firefox_binary_path);
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        firefox = new FirefoxDriver(capabilities);
    }

    public WebDriver getBrowser() {
        return firefox;
    }

    public void navigateTo(String link) {
        firefox.navigate().to(link);
    }

    public WebElement findElementById(String id) {
        return firefox.findElement(By.id(id));
    }

    public WebElement findElementByCssSelector(String cssSelector) {
        return firefox.findElement(By.cssSelector(cssSelector));
    }

    public WebElement findElementByXPath(String xPath) {
        return firefox.findElement(By.xpath(xPath));
    }

    public WebElement findElementByLinkText(String linkText) {
        return firefox.findElement(By.linkText(linkText));
    }

    public void executeVoidScript(String jsScript) {
        JavascriptExecutor js = (JavascriptExecutor) firefox;
        js.executeScript(jsScript);
    }

    public Object executeReturnScript(String jsScript) {
        JavascriptExecutor js = (JavascriptExecutor) firefox;
        return  js.executeScript(jsScript);
    }

}
