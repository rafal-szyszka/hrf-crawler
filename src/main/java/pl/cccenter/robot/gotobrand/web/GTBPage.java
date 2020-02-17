package pl.cccenter.robot.gotobrand.web;

import javafx.concurrent.Task;
import pl.cccenter.robot.web.FirefoxBrowser;

/**
 * Created by Rafał on 06.02.2017.
 */
public class GTBPage extends Task<Void> {

    public static class Arguments {
        public static final String TASK_ADD_BUTTON = "taskAddButton";
        public static final String COST_ADD_BUTTON = "costAddButton";
        public static final String LUMP_COST_ADD_BUTTON = "lumpCostAddButton";
        public static final String TASKS_AMOUNT = "tasksAmount";
        public static final String COSTS_AMOUNT = "costsAmount";
        public static final String LUMP_COSTS_AMOUNT = "lumpCostsAmount";
        public static final String PROMO_TASK_AMOUNT = "lumpCostsAmount";
        public static final String DETAIL_COST_AMOUNT = "detailCostAmount";
    }

    public static final String GET_ALLOWED_COST_CATS = "var select = document.getElementById(\"zakres_rzeczowo_finansowy_wydatki_0_zadanie\");\n" +
            "select.options[1].selected= true;\n" +
            "select.dispatchEvent(new Event('change'));\n" +
            "\n" +
            "select = document.getElementById(\"zakres_rzeczowo_finansowy_wydatki_0_kategoriaKosztow\");\n" +
            "var toRet = [];\n" +
            "for(var i = 1; i < select.options.length; i++) {\n" +
            "  toRet.push(select.options[i].text);\n" +
            "}\n" +
            "\n" +
            "select = document.getElementById(\"zakres_rzeczowo_finansowy_wydatki_0_zadanie\");\n" +
            "select.options[0].selected = true;\n" +
            "select.dispatchEvent(new Event('change'));\n" +
            "return toRet;";

    public static final String GET_ALLOWED_TASK_NAMES = "var opt = document.getElementById(\"zakres_rzeczowo_finansowy_zadania_0_zadanieDozwolonaNazwa\").options;\n" +
            "var toRet = [];\n" +
            "for(var i = 0; i < opt.length; i++) {\n" +
            "    toRet.push(opt[i].text);\n" +
            "}\n" +
            "return toRet;";

    public static final String GET_ALLOWED_TNAMES = "var selectTask = document.getElementById(\"zakres_rzeczowo_finansowy_wydatki_0_zadanie\");\n" +
            "var tasks = [];\n" +
            "for(var i = 0; i < selectTask.options.length; i++) {\n" +
            "  var val = selectTask.options[i].text;\n" +
            "  tasks.push(val);\n" +
            "}\n" +
            "\n" +
            "return tasks;";

    public static final String CLICK_ADD_BUTTON_SCRIPT = "var btns = document.querySelectorAll(\".btn\");\n" +
            "\n" +
            "for(var i = 0; i < btns.length; i++) {\n" +
            "  if(btns[i].innerHTML === \"Dodaj zadanie\") {\n" +
            "    for(var j = 0; j < " + Arguments.TASKS_AMOUNT + "; j++) {\n" +
            "      btns[i].dispatchEvent(new Event('click'));\n" +
            "    }\n" +
            "  }\n" +
            "  if(btns[i].innerHTML === \"Dodaj wydatek rzeczywiście ponoszony\") {\n" +
            "    for(var j = 0; j < " + Arguments.COSTS_AMOUNT + "; j++) {\n" +
            "      btns[i].dispatchEvent(new Event('click'));\n" +
            "    }\n" +
            "  }\n" +
            "  if(btns[i].innerHTML === \"Dodaj wydatek rozliczany ryczałtowo\") {\n" +
            "    for(var j = 0; j < " + Arguments.LUMP_COSTS_AMOUNT + "; j++) {\n" +
            "      btns[i].dispatchEvent(new Event('click'));\n" +
            "    }\n" +
            "  }\n" +
            "  //if(btns[i].innerHTML === \"Dodaj działanie promocyjne\") {\n" +
            "    //for(var j = 0; j < " + Arguments.PROMO_TASK_AMOUNT + "; j++) {\n" +
            "      //btns[i].dispatchEvent(new Event('click'));\n" +
            "    //}\n" +
            "  //}\n" +
            "  if(btns[i].innerHTML.indexOf(\"Dodaj wydatek szczegółowy\") != -1) {\n" +
            "    for(var j = 0; j < " + Arguments.DETAIL_COST_AMOUNT + "; j++) {\n" +
            "      btns[i].dispatchEvent(new Event('click'));\n" +
            "    }\n" +
            "  }\n" +
            "}";

    public static final String REMOVE_CURRENT_TASKS_COSTS = "var delbut = document.querySelectorAll(\".btn\");\n" +
            "\n" +
            "for(var i = delbut.length - 1; i >= 0; i--) {\n" +
            "  if(delbut[i].innerHTML.indexOf(\"Usuń wydatek\") != -1 || delbut[i].innerHTML.indexOf(\"Usuń wydatek rozliczany ryczałtowo\") != -1 || delbut[i].innerHTML.indexOf(\"Usuń wydatek szczegółowy\") != -1){\n" +
            "    delbut[i].dispatchEvent(new Event('click'));\n" +
            "  }\n" +
            "}";

    private int taskCount;
    private int costCount;
    private int lumpCostsAmount;
    private int promoTasksAmount;
    private int detailCostAmount;

    private FirefoxBrowser browser;

    private GTBPage(int taskCount, int costCount, int lumpCostsAmount, int promoTasksAmount, int detailCostAmount, FirefoxBrowser browser) {
        this.taskCount = taskCount;
        this.costCount = costCount;
        this.lumpCostsAmount = lumpCostsAmount;
        this.promoTasksAmount = promoTasksAmount;
        this.detailCostAmount = detailCostAmount;
        this.browser = browser;
    }

    @Override
    protected Void call() throws Exception {

        String revealTasksScript = CLICK_ADD_BUTTON_SCRIPT
                .replace(Arguments.TASKS_AMOUNT, Integer.toString(taskCount))
                .replace(Arguments.COSTS_AMOUNT, Integer.toString(costCount))
                .replace(Arguments.LUMP_COSTS_AMOUNT, Integer.toString(lumpCostsAmount))
                .replace(Arguments.PROMO_TASK_AMOUNT, Integer.toString(promoTasksAmount))
                .replace(Arguments.DETAIL_COST_AMOUNT, Integer.toString(detailCostAmount));

        browser.executeVoidScript(revealTasksScript);
        return null;
    }

    public void go() throws Exception {
        call();
    }

    public static class Builder {
        private int taskCount;
        private int costCount;
        private int lumpCostsAmount;
        private int promoTasksAmount;
        private int detailCostAmount;
        private FirefoxBrowser browser;

        public Builder setTaskCount(int taskCount) {
            this.taskCount = taskCount;
            return this;
        }

        public Builder setCostCount(int costCount) {
            this.costCount = costCount;
            return this;
        }

        public Builder setLumpCostsAmount(int lumpCostsAmount) {
            this.lumpCostsAmount = lumpCostsAmount;
            return this;
        }

        public Builder setBrowser(FirefoxBrowser browser) {
            this.browser = browser;
            return this;
        }

        public Builder setPromoTasksAmount(int promoTasksAmount) {
            this.promoTasksAmount = promoTasksAmount;
            return this;
        }

        public Builder setDetailCostAmount(int detailCostAmount) {
            this.detailCostAmount = detailCostAmount;
            return this;
        }

        public GTBPage createGTBPage() {
            return new GTBPage(taskCount, costCount, lumpCostsAmount, promoTasksAmount, detailCostAmount, browser);
        }
    }
}
