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
        public static final String UNIT_NUMBER = "argUnitNumber";
        public static final String UNIT_PRICE = "argUnitPrice";
        public static final String PEOPLE_AMOUNT = "argPeopleAmount";
        public static final String DAYS_NUMBER = "argDaysNumber";
        public static final String NIGHTS_NUMBER = "argNightsNumber";
        public static final String OFFER_1 = "argOffer1";
        public static final String OFFER_2 = "argOffer2";
        public static final String OFFER_3 = "argOffer3";
        public static final String DATE_RECOGNIZE = "argDateRecognize";
        public static final String IS_DATE_RECOGNIZE = "argRecognizeValidDate";
    }

    public static final String FILL_DETAIL_COSTS_SCRIPT =
            "var task = document.getElementById(\"zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_zadanie\");\n" +
                    "var description = document.getElementById(\"zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_szczegoloweWydatki\");\n" +
                    "var catalog = document.getElementById(\"zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_katalogKosztow\");\n" +
                    "var qualified = document.getElementById(\"zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_wydatkiKwalifikowalne_tbbc_amount\");\n" +
                    "var details = document.getElementById(\"zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_sposobRozeznaniaRynku\");\n" +
                    "\n" +
                    "var unitNumber = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_liczbaJednostek_tbbc_amount');\n" +
                    "var unitPrice = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_cenaJednostkowa_tbbc_amount');\n" +
                    "var peopleAmount = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_liczbaOsob');\n" +
                    "var dayNumber = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_liczbaDniDelegacji');\n" +
                    "var nightsNumber = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_liczbaNoclegow');\n" +
                    "var offer1 = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_daneOferty1');\n" +
                    "var offer2 = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_daneOferty2');\n" +
                    "var offer3 = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_daneOferty3');\n" +
                    "var dateRecognize = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_dataRozeznaniaRynku');\n" +
                    "\n" +
                    "for (var i = 0; i < task.options.length; i++) {\n" +
                    "    if (task.options[i].text == \"argTask\") {\n" +
                    "        task.options[i].selected = true;\n" +
                    "        task.dispatchEvent(new Event('change'));\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "description.value = \"argDescription\";\n" +
                    "description.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "for (var i = 0; i < catalog.options.length; i++) {\n" +
                    "    if (catalog.options[i].text == \"argCatalog\") {\n" +
                    "        catalog.options[i].selected = true;\n" +
                    "        catalog.dispatchEvent(new Event('change'));\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "qualified.value = \"argQualifiedCost\";\n" +
                    "qualified.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "details.value = \"argDetails\";\n" +
                    "details.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "unitNumber.value = \"argUnitNumber\";\n" +
                    "unitNumber.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "unitPrice.value = \"argUnitPrice\";\n" +
                    "unitPrice.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "peopleAmount.value = \"argPeopleAmount\";\n" +
                    "peopleAmount.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "dayNumber.value = \"argDaysNumber\";\n" +
                    "dayNumber.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "nightsNumber.value = \"argNightsNumber\";\n" +
                    "nightsNumber.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "offer1.value = \"argOffer1\";\n" +
                    "offer1.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "offer2.value = \"argOffer2\";\n" +
                    "offer2.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "offer3.value = \"argOffer3\";\n" +
                    "offer3.dispatchEvent(new Event('change'));\n" +
                    "\n" +
                    "if (argRecognizeValidDate) {\n" +
                    "    dateRecognize.value = \"argDateRecognize\";\n" +
                    "    dateRecognize.dispatchEvent(new Event('change'));\n" +
                    "}";

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
                            .replace(Arguments.TASK, costAttrs[DetailCost.TASK].trim())
                            .replace(Arguments.DESCRIPTION, costAttrs[DetailCost.DESCRIPTION].trim())
                            .replace(Arguments.CATALOG, costAttrs[DetailCost.CATALOG].trim())
                            .replace(Arguments.DETAILS, costAttrs[DetailCost.DETAILS].trim())
                            .replace(Arguments.QUALIFIED_COST, costAttrs[DetailCost.QUALIFIED_SUM].trim().replace(",", "."))
                            .replace(Arguments.UNIT_NUMBER, costAttrs[DetailCost.UNIT_AMOUNT].trim().replace(",", "."))
                            .replace(Arguments.UNIT_PRICE, costAttrs[DetailCost.PRICE].trim().replace(",", "."))
                            .replace(Arguments.PEOPLE_AMOUNT, costAttrs[DetailCost.PEOPLE_NUMBER].trim())
                            .replace(Arguments.DAYS_NUMBER, costAttrs[DetailCost.DAY_NUMBER].trim())
                            .replace(Arguments.NIGHTS_NUMBER, costAttrs[DetailCost.OVERNIGHT_NUMBER].trim())
                            .replace(Arguments.OFFER_1, costAttrs[DetailCost.OFFER1_DATA].trim())
                            .replace(Arguments.OFFER_2, costAttrs[DetailCost.OFFER2_DATA].trim())
                            .replace(Arguments.OFFER_3, costAttrs[DetailCost.OFFER3_DATA].trim())
                            .replace(Arguments.IS_DATE_RECOGNIZE, costAttrs[DetailCost.RESEARCH_DATE].trim().equalsIgnoreCase("Nie dotyczy") ? "false" : "true")
                            .replace(Arguments.DATE_RECOGNIZE, costAttrs[DetailCost.RESEARCH_DATE].trim())
            );
            updateProgress(idx+1, costs.size());
            updateMessage(progressMsg + (idx+1) + " na " + costs.size());
        });

        return null;
    }
}
