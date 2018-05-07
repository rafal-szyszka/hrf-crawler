package pl.cccenter.robot.gotobrand.web;

import org.openqa.selenium.WebElement;

/**
 * Created by Squier on 2016-10-16.
 */
public class LoginForm {

    public static final String GTB_LOGIN_ID = "_username";
    public static final String GTB_PASSWORD_ID = "_password";
    public static final String GTB_SIGN_IN_ID = "zaloguj";

    private WebElement login;
    private WebElement password;
    private WebElement signIn;

    public LoginForm(WebElement login, WebElement password, WebElement signIn) {
        this.login = login;
        this.password = password;
        this.signIn = signIn;
    }

    public WebElement getLogin() {
        return login;
    }

    public void setLogin(WebElement login) {
        this.login = login;
    }

    public WebElement getPassword() {
        return password;
    }

    public void setPassword(WebElement password) {
        this.password = password;
    }

    public WebElement getSignIn() {
        return signIn;
    }

    public void setSignIn(WebElement signIn) {
        this.signIn = signIn;
    }
}
