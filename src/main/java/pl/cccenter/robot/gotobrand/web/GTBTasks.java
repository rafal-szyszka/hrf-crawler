package pl.cccenter.robot.gotobrand.web;

import pl.cccenter.robot.hrf.Task;
import pl.cccenter.robot.web.FirefoxBrowser;

import java.util.ArrayList;

/**
 * Created by Rafał on 06.02.2017.
 */
public class GTBTasks extends javafx.concurrent.Task<Void> {

    /*public static final String FILL_TASK_SCRIPT = "var changed = false;\n" +
            "var taskName = document.getElementById('zakres_rzeczowo_finansowy_zadania_arg1_zadanieDozwolonaNazwa');\n" +
            "for(var i = 0; i < taskName.options.length; i ++) {\n" +
            "if(taskName.options[i].innerHTML == 'arg2'){\n" +
            "taskName.options[i].selected = true;\n" +
            "taskName.dispatchEvent(new Event('change'));\n}\n}\n" +
            "\n" +
            "var taskDesc = document.getElementById('zakres_rzeczowo_finansowy_zadania_arg1_opisPlanowanychDzialan');\n" +
            "taskDesc.value = 'arg3';\n" +
            "var sDate = document.getElementById('zakres_rzeczowo_finansowy_zadania_arg1_dataRozpoczeciaZadania');\n" +
            "sDate.value = 'arg4';\n" +
            "sDate.dispatchEvent(new Event('change'));\n" +
            "var eDate = document.getElementById('zakres_rzeczowo_finansowy_zadania_arg1_dataZakonczeniaZadania');\n" +
            "eDate.value = 'arg5';\n" +
            "eDate.dispatchEvent(new Event('change'));";*/

    public static final String FILL_TASK_SCRIPT = "var changed = false;\n" +
            "var taskName = document.getElementById('zakres_rzeczowo_finansowy_zadania_arg1_nazwa');\n" +
            "taskName.value = 'arg2';\n" +
            "taskName.dispatchEvent(new Event('change'));\n" +
            "\n" +
            "var taskDesc = document.getElementById('zakres_rzeczowo_finansowy_zadania_arg1_opisPlanowanychDzialan');\n" +
            "taskDesc.value = 'arg3';\n" +
            "taskDesc.dispatchEvent(new Event('change'));\n" +
            "var sDate = document.getElementById('zakres_rzeczowo_finansowy_zadania_arg1_dataRozpoczeciaZadania');\n" +
            "sDate.value = 'arg4';\n" +
            "sDate.dispatchEvent(new Event('change'));\n" +
            "var eDate = document.getElementById('zakres_rzeczowo_finansowy_zadania_arg1_dataZakonczeniaZadania');\n" +
            "eDate.value = 'arg5';\n" +
            "eDate.dispatchEvent(new Event('change'));";

    public static final String SAVE_PROPOSAL = "var select = document.getElementById(\"akcja_zapisz_wniosek\");\n" +
            "select.dispatchEvent(new Event('click'));";

    private ArrayList<String[]> tasks;
    private FirefoxBrowser browser;

    public GTBTasks(ArrayList<String[]> tasks, FirefoxBrowser browser) {
        this.tasks = tasks;
        this.browser = browser;
    }

    @Override
    protected Void call() throws Exception {

        for (int i = 0; i < tasks.size(); i++) {
            String[] task = tasks.get(i);
            String script = FILL_TASK_SCRIPT.replaceAll("arg1", Integer.toString(i));
            script = script.replaceAll("arg2", task[Task.NAME].equals("null") ? "" : task[Task.NAME]);
            script = script.replace("arg3", task[Task.DESCRIPTION].equals("null") ? "" : task[Task.DESCRIPTION]);
            script = script.replace("arg4", task[Task.START_DATE].equals("null") ? "" : task[Task.START_DATE]);
            script = script.replace("arg5", task[Task.END_DATE].equals("null") ? "" : task[Task.END_DATE]);
            script = script.replaceAll("\n", " ");
            browser.executeVoidScript(script);
        }

        browser.executeVoidScript(SAVE_PROPOSAL);
        return null;
    }
}
