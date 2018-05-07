package pl.cccenter.robot.web;

import javafx.concurrent.Task;
import org.openqa.selenium.By;
import pl.cccenter.robot.gotobrand.web.LoginForm;

/**
 * Created by Squier on 2016-10-16.
 */
public class LoginService extends Task<FirefoxBrowser> {

    private FirefoxBrowser browser;
    private String prevMessage;
    private LoginData loginData;

    public LoginService(FirefoxBrowser browser, String prevMessage, LoginData loginData) {
        this.browser = browser;
        this.prevMessage = prevMessage;
        this.loginData = loginData;
    }

    @Override
    protected FirefoxBrowser call() throws Exception {
        updateMessage(prevMessage += "\nPrzekierowuję na stronę logowania...");

        browser.navigateTo(loginData.getLink());
        updateMessage(prevMessage += "\t [-- OK --]\nPobieram dane logowania...");

        LoginForm form = new LoginForm(
                browser.getBrowser().findElement(By.id(LoginForm.GTB_LOGIN_ID)),
                browser.getBrowser().findElement(By.id(LoginForm.GTB_PASSWORD_ID)),
                browser.getBrowser().findElement(By.id(LoginForm.GTB_SIGN_IN_ID))
        );
        updateMessage(prevMessage += "\t [-- OK --]\nLoguję...");

        form.getLogin().sendKeys(loginData.getLogin());
        form.getPassword().sendKeys(loginData.getPassword());
        form.getSignIn().click();
        updateMessage(prevMessage += "\t [-- OK --]\nWybierz odpowiednie nabory i utwórz wniosek!");

        return browser;
    }
}
