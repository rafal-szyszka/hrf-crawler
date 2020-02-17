package pl.cccenter.robot.gotobrand.web;

import javafx.concurrent.Task;
import pl.cccenter.robot.hrf.LumpCost;
import pl.cccenter.robot.hrf.PromoTask;
import pl.cccenter.robot.web.FirefoxBrowser;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Created by Rafał Szyszka on 04.03.2019.
 */
public class GTBPromoTask extends Task<Void> {

    public class Arguments {
        public static final String AMOUNT = "argAmount";
        public static final String INDEX = "argIdx";
        public static final String NAME = "argName";
        public static final String COUNTRY = "argCountry";
        public static final String YEAR = "argYear";
        public static final String SITE = "argSite";
        public static final String COUNTRY_STATION = "argNationalStation";
    }

    public static final String ADD_PROMO_TASK_FORMS =
            "var btns = document.querySelectorAll(\".btn\");\n" +
                    "for(var clicks = 0; clicks < " + Arguments.AMOUNT + "; clicks++) {\n" +
                    "    for(var i = 0; i < btns.length; i++) {\n" +
                    "        if(btns[i].innerHTML === \"Dodaj działanie promocyjne\") {\n" +
                    "            btns[i].dispatchEvent(new Event('click'));\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";

    public static final String FILL_PROMO_TASKS_SCRIPT =
            "var programName = document.getElementById(\"programy_promocji_dzialaniaPromocyjne_" + Arguments.INDEX + "_nazwa\"); \n" +
                "programName.value = \"" + Arguments.NAME + "\";\n" +
                "programName.dispatchEvent(new Event('change'));\n" +

                "var country = document.getElementById(\"programy_promocji_dzialaniaPromocyjne_" + Arguments.INDEX + "_kraj\"); \n" +
                "for (var i = 0; i < country.options.length; i++) {\n" +
                "\tif (country.options[i].text == \"" + Arguments.COUNTRY + "\") {\n" +
                "\t\tcountry.options[i].selected = true;\n" +
                "\t\tcountry.dispatchEvent(new Event('change'));\n" +
                "\t}\n" +
                "}\n" +

                "var year = document.getElementById(\"programy_promocji_dzialaniaPromocyjne_" + Arguments.INDEX + "_rok\");\n" +
                "for (var i = 0; i < year.options.length; i++) {\n" +
                "\tif (year.options[i].text == \"" + Arguments.YEAR + "\") {\n" +
                "\t\tyear.options[i].selected = true;\n" +
                "\t\tyear.dispatchEvent(new Event('change'));\n" +
                "\t}\n" +
                "}\n" +

                "var wwwSite = document.getElementById(\"programy_promocji_dzialaniaPromocyjne_" + Arguments.INDEX + "_adresWww\"); \n" +
                "wwwSite.value = \"" + Arguments.SITE + "\";\n" +
                "wwwSite.dispatchEvent(new Event('change'));\n" +

                "var isCountryStation = document.getElementById(\"programy_promocji_dzialaniaPromocyjne_" + Arguments.INDEX + "_czyStanowiskoNarodowe\");\n" +
                "if ( " + Arguments.COUNTRY_STATION + " ) {\n" +
                "\tisCountryStation.checked = true;\n" +
                "\tisCountryStation.dispatchEvent(new Event('change'));\n" +
                "}";

    private ArrayList<PromoTask> promoTasks;
    private String progressMsg;
    private FirefoxBrowser browser;

    @Override
    protected Void call() throws Exception {

        IntStream.range(0, promoTasks.size()).forEach(taskIdx -> {
            String[] taskAttrs = promoTasks.get(taskIdx).splitToAtr();
            String script = FILL_PROMO_TASKS_SCRIPT
                    .replaceAll(Arguments.INDEX, String.valueOf(taskIdx))
                    .replace(Arguments.NAME, taskAttrs[PromoTask.NAME])
                    .replace(Arguments.COUNTRY, taskAttrs[PromoTask.COUNTRY])
                    .replace(Arguments.YEAR, taskAttrs[PromoTask.YEAR])
                    .replace(Arguments.SITE, taskAttrs[PromoTask.WWW_SITE])
                    .replace(Arguments.COUNTRY_STATION, taskAttrs[PromoTask.IS_NATIONAL_STAND]);
            browser.executeVoidScript(script);
            updateProgress(taskIdx+1, promoTasks.size());
            updateMessage(progressMsg + (taskIdx+1) + " na " + promoTasks.size());
        });

        browser.executeVoidScript(GTBTasks.SAVE_PROPOSAL);

        return null;
    }

    public GTBPromoTask(ArrayList<PromoTask> promoTasks, String progressMsg, FirefoxBrowser browser) {
        this.promoTasks = promoTasks;
        this.progressMsg = progressMsg;
        this.browser = browser;
    }
}
