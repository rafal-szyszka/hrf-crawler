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
    }

    public static final String FILL_LUMP_COST_SCRIPT =
            "var taskSelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_zadanie');\n" +
            "for(var i = 0; i < taskSelect.options.length; i++) {\n" +
            "\tif(taskSelect.options[i].innerHTML == \"argTask\") {\n" +
            "\t\ttaskSelect.options[i].selected = true;\n" +
            "\t\ttaskSelect.dispatchEvent(new Event('change'));\n" +
            "\t}\n" +
            "}\n" +
            "\n" +
            "if(argIfLump) {\n" +
            "\tvar lumpSelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_rodzajRyczaltu');\n" +
            "\tfor(var i = 0; i < lumpSelect.options.length; i++) {\n" +
            "\t\tif(lumpSelect.options[i].innerHTML == \"argLumpType\") {\n" +
            "\t\t\tlumpSelect.options[i].selected = true;\n" +
            "\t\t\tlumpSelect.dispatchEvent(new Event('change'));\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "\tvar lumpName = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_nazwaRyczaltuSlownie');\n" +
            "\tlumpName.value = \"argLumpName\";\n" +
            "\tlumpName.dispatchEvent(new Event('change')); \n" +
            "\n" +
            "\tvar pointerName = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_nazwaWskaznika');\n" +
            "\tpointerName.value = \"argPointerName\";\n" +
            "\tpointerName.dispatchEvent(new Event('change'));\n" +
            "\n" +
            "\tvar pointerValue = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_wartoscWskaznika_tbbc_amount');\n" +
            "\tpointerValue.value = \"argPointerValue\";\n" +
            "\tpointerValue.dispatchEvent(new Event('change'));\n" +
            "\n" +
            "\tvar categorySelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_kategoriaKosztowKwotaRyczaltowa');\n" +
            "    for(var i = 0; i < categorySelect.options.length; i++) {\n" +
            "        if(categorySelect.options[i].innerHTML == \"argCategory\") {\n" +
            "            categorySelect.options[i].selected = true;\n" +
            "            categorySelect.dispatchEvent(new Event('change'));\n" +
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
                    .replace(Arguments.CATEGORY, cost[LumpCost.CATEGORY])
                    .replace(Arguments.LUMP_NAME, cost[LumpCost.NAME])
                    .replace(Arguments.LUMP_TYPE, cost[LumpCost.TYPE])
                    .replace(Arguments.POINTER_NAME, cost[LumpCost.POINTER_NAME])
                    .replace(Arguments.POINTER_VALUE, cost[LumpCost.POINTER_VALUE])
                    .replace(Arguments.QUALIFIED_COST, cost[LumpCost.QUALIFIED_COSTS])
                    .replace(Arguments.SUBSIDY, cost[LumpCost.SUBSIDY])
                    .replace(Arguments.TASK, cost[LumpCost.TASK])
                    .replace(Arguments.IS_LUMP, cost[LumpCost.IS_LUMP])
                    .replaceAll("\n", " ");
            browser.executeVoidScript(script);
            updateProgress(i+1, costs.size());
            updateMessage(progressMsg + i + " na " + costs.size());
        }

        return null;
    }

    public void setProgressMsg(String progressMsg) {
        this.progressMsg = progressMsg;
    }
}
