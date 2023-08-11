package pl.cccenter.robot.gotobrand.web;

import javafx.concurrent.Task;
import pl.cccenter.robot.hrf.LumpCost;
import pl.cccenter.robot.web.FirefoxBrowser;

import java.util.ArrayList;

/**
 * Created by Rafał Szyszka on 26.04.2018.
 */
public class GTBLumpCosts extends Task<Void> {

    public class Arguments {
        public static final String INDEX = "argIdx";
        public static final String TASK = "argTask";
        public static final String LUMP_TYPE = "argLumpType";
        public static final String LUMP_NAME = "argLumpName";
        public static final String POINTER_NAME = "argPointerName";
        public static final String POINTER_VALUE = "argPointerValue";
        public static final String CATEGORY = "argCategory";
        public static final String QUALIFIED_COST = "argQualifiedCost";
        public static final String SUBSIDY = "argSubsidy";
        public static final String IS_LUMP = "argIfLump";
        public static final String DOCUMENTS = "argDocuments";
    }

    public static final String FILL_LUMP_COST_SCRIPT =
            "var taskSelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_zadanie');\n" +
                    "for (var i = 0; i < taskSelect.options.length; i++) {\n" +
                    "    if (taskSelect.options[i].innerHTML == \"argTask\") {\n" +
                    "        taskSelect.options[i].selected = true;\n" +
                    "        taskSelect.dispatchEvent(new Event('change'));\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "var lumpSelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_rodzajRyczaltu');\n" +
                    "for (var i = 0; i < lumpSelect.options.length; i++) {\n" +
                    "    if (lumpSelect.options[i].innerHTML == \"argLumpType\") {\n" +
                    "        lumpSelect.options[i].selected = true;\n" +
                    "        lumpSelect.dispatchEvent(new Event('change'));\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "if (argIfLump) {\n" +
                    "    var categorySelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_kategoriaKosztowKwotaRyczaltowa');\n" +
                    "    for (var i = 0; i < categorySelect.options.length; i++) {\n" +
                    "        if (categorySelect.options[i].innerHTML == \"argCategory\") {\n" +
                    "            categorySelect.options[i].selected = true;\n" +
                    "            categorySelect.dispatchEvent(new Event('change'));\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    var lumpNameSelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_nazwaRyczaltuDlaKwotyRyczaltowej');\n" +
                    "    for (var i = 0; i < lumpNameSelect.options.length; i++) {\n" +
                    "        if (lumpNameSelect.options[i].innerHTML == \"argLumpName\") {\n" +
                    "            lumpNameSelect.options[i].selected = true;\n" +
                    "            lumpNameSelect.dispatchEvent(new Event('change'));\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    var pointerNameSelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_nazwaWskaznikaDlaKwotyRyczaltowej');\n" +
                    "    for (var i = 0; i < pointerNameSelect.options.length; i++) {\n" +
                    "        if (pointerNameSelect.options[i].innerHTML == \"argPointerName\") {\n" +
                    "            pointerNameSelect.options[i].selected = true;\n" +
                    "            pointerNameSelect.dispatchEvent(new Event('change'));\n" +
                    "        }\n" +
                    "    }\n" +
                    "    var pointerValue = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_wartoscWskaznika_tbbc_amount');\n" +
                    "    pointerValue.value = \"argPointerValue\";\n" +
                    "    pointerValue.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "    var documentsSelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_nazwaDokumentuDlaKwotyRyczaltowej');\n" +
                    "    for (var i = 0; i < documentsSelect.options.length; i++) {\n" +
                    "        if (documentsSelect.options[i].innerHTML == \"argDocuments\") {\n" +
                    "            documentsSelect.options[i].selected = true;\n" +
                    "            documentsSelect.dispatchEvent(new Event('change'));\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "var qualifiedCost = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_wydatkiKwalifikowalne_tbbc_amount');\n" +
                    "qualifiedCost.value = \"argQualifiedCost\";\n" +
                    "qualifiedCost.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "var subsidy = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_wnioskowaneDofinansowanie_tbbc_amount');\n" +
                    "subsidy.value = \"argSubsidy\";\n" +
                    "subsidy.dispatchEvent(new Event('change'));";

    private ArrayList<String[]> costs;
    private FirefoxBrowser browser;
    private String progressMsg;

    public GTBLumpCosts(ArrayList<String[]> costs, FirefoxBrowser browser) {
        this.costs = costs;
        this.browser = browser;
    }

    @Override
    protected Void call() throws Exception {

        for(int i = 0; i < costs.size(); i++) {
            String[] cost = costs.get(i);
            String script = FILL_LUMP_COST_SCRIPT.replaceAll(Arguments.INDEX, Integer.toString(i))
                    .replace(Arguments.CATEGORY, cost[LumpCost.CATEGORY].trim())
                    .replace(Arguments.LUMP_NAME, cost[LumpCost.NAME].trim())
                    .replace(Arguments.LUMP_TYPE, cost[LumpCost.TYPE].trim())
                    .replace(Arguments.POINTER_NAME, cost[LumpCost.POINTER_NAME].trim())
                    .replace(Arguments.POINTER_VALUE, cost[LumpCost.POINTER_VALUE].trim())
                    .replace(Arguments.QUALIFIED_COST, cost[LumpCost.QUALIFIED_COSTS].trim())
                    .replace(Arguments.SUBSIDY, cost[LumpCost.SUBSIDY].trim())
                    .replace(Arguments.TASK, cost[LumpCost.TASK].trim())
                    .replace(Arguments.IS_LUMP, cost[LumpCost.IS_LUMP].trim())
                    .replace(Arguments.DOCUMENTS, cost[LumpCost.DOCUMENTS].trim())
                    .replaceAll("\n", " ");
            browser.executeVoidScript(script);
            updateProgress(i+1, costs.size());
            updateMessage(progressMsg + (i+1) + " na " + costs.size());
        }

        return null;
    }

    public void setProgressMsg(String progressMsg) {
        this.progressMsg = progressMsg;
    }
}
