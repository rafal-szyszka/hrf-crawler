package pl.cccenter.robot.gotobrand.web;

import javafx.concurrent.Task;
import pl.cccenter.robot.hrf.DetailCost;
import pl.cccenter.robot.web.FirefoxBrowser;

import java.util.ArrayList;
import java.util.stream.IntStream;


/**
 * Created by Rafał Szyszka on 08.03.2019.
 */
public class GTBDetailCosts extends Task<Void> {

    public class Arguments {
        public static final String INDEX = "argIdx";
        public static final String TASK = "argTask";
        public static final String DESCRIPTION = "argDescription";
        public static final String CATALOG = "argCatalog";
        public static final String QUALIFIED_COST = "argQualifiedCost";
        public static final String DETAILS = "argDetails";
    }

    public static final String FILL_DETAIL_COSTS_SCRIPT =
            "var task = document.getElementById(\"zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_" + Arguments.INDEX + "_zadanie\");\n" +
                    "var description = document.getElementById(\"zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_" + Arguments.INDEX + "_szczegoloweWydatki\");\n" +
                    "var catalog = document.getElementById(\"zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_" + Arguments.INDEX + "_katalogKosztow\");\n" +
                    "var qualified = document.getElementById(\"zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_" + Arguments.INDEX + "_wydatkiKwalifikowalne_tbbc_amount\");\n" +
                    "var details = document.getElementById(\"zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_" + Arguments.INDEX + "_sposobRozeznaniaRynku\");\n" +
                    "\n" +
                    "for (var i = 0; i < task.options.length; i++) {\n" +
                    "\tif (task.options[i].text == \"" + Arguments.TASK + "\") {\n" +
                    "\t\ttask.options[i].selected = true;\n" +
                    "\t\ttask.dispatchEvent(new Event('change'));\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "description.value = \"" + Arguments.DESCRIPTION + "\";\n" +
                    "description.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "for (var i = 0; i < catalog.options.length; i++) {\n" +
                    "\tif (catalog.options[i].text == \"" + Arguments.CATALOG + "\") {\n" +
                    "\t\tcatalog.options[i].selected = true;\n" +
                    "\t\tcatalog.dispatchEvent(new Event('change'));\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "qualified.value = \"" + Arguments.QUALIFIED_COST + "\";\n" +
                    "qualified.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "details.value = \"" + Arguments.DETAILS + "\";\n" +
                    "details.dispatchEvent(new Event('change'));";

    private ArrayList<DetailCost> costs;
    private FirefoxBrowser browser;
    private String progressMsg;

    public GTBDetailCosts(ArrayList<DetailCost> costs, FirefoxBrowser browser, String progressMsg) {
        this.costs = costs;
        this.browser = browser;
        this.progressMsg = progressMsg;
    }

    @Override
    protected Void call() throws Exception {

        IntStream.range(0, costs.size()).forEach(idx -> {
            String[] costAttrs = costs.get(idx).splitToAtr();
            browser.executeVoidScript(
                    FILL_DETAIL_COSTS_SCRIPT.replaceAll(Arguments.INDEX, String.valueOf(idx))
                            .replace(Arguments.TASK, costAttrs[DetailCost.TASK])
                            .replace(Arguments.DESCRIPTION, costAttrs[DetailCost.DESCRIPTION])
                            .replace(Arguments.CATALOG, costAttrs[DetailCost.CATALOG])
                            .replace(Arguments.DETAILS, costAttrs[DetailCost.DETAILS])
                            .replace(Arguments.QUALIFIED_COST, costAttrs[DetailCost.QUALIFIED_SUM])
            );
            updateProgress(idx+1, costs.size());
            updateMessage(progressMsg + (idx+1) + " na " + costs.size());
        });

        return null;
    }
}
