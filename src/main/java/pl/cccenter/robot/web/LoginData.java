package pl.cccenter.robot.web;

/**
 * Created by Squier on 2016-10-12.
 */
public class LoginData {

    private String link, login, password;

    public LoginData(String link, String login, String password) {
        this.link = link;
        this.login = login;
        this.password = password;
    }

    public LoginData() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
