package pl.cccenter.robot.tasks;

import javafx.concurrent.Task;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import pl.cccenter.robot.excel.SimpleSheet;
import pl.cccenter.robot.web.LoginData;

/**
 * Created by Squier on 2016-10-15.
 */
public class GetLoginData extends Task<LoginData> {

    private String prevMessage;
    private XSSFSheet robotSheet;

    public GetLoginData(String prevMessage, XSSFSheet robotSheet) {
        this.prevMessage = prevMessage;
        this.robotSheet = robotSheet;
    }

    @Override
    protected LoginData call() throws Exception {
        prevMessage += "\nWczytuję dane logowania...";
        updateMessage(prevMessage);
        String link, login, password;
        SimpleSheet simpleRobot = new SimpleSheet(robotSheet);

        link = simpleRobot.getCellStringValue("A1");
        login = simpleRobot.getCellStringValue("B1");
        password = simpleRobot.getCellStringValue("C1");

        prevMessage += "\t [-- OK --]";
        updateMessage(prevMessage);
        prevMessage += "\n\tWczytane dane logowania:\n\t\tlink:\t\t" + link +
                "\n\t\tlogin:\t" + login +
                "\n\t\thasło:\t" + password;
        updateMessage(prevMessage);

        return new LoginData(link, login, password);
    }
}
