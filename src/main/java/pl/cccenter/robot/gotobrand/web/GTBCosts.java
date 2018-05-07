package pl.cccenter.robot.gotobrand.web;

import javafx.concurrent.Task;
import pl.cccenter.robot.hrf.Cost;
import pl.cccenter.robot.web.FirefoxBrowser;

import java.util.ArrayList;

/**
 * Created by Rafał on 09.02.2017.
 */
public class GTBCosts extends Task<Void> {

    public static final String FILL_COST_SCRIPT = "var changed = false;\n" +
            "var select = document.getElementById(\"zakres_rzeczowo_finansowy_wydatki_arg1_zadanie\");\n" +
            "for(var i = 0; i < select.options.length; i++) {\n" +
            "  if(select.options[i].text.indexOf(\"arg2\") != -1) {\n" +
            "    select.options[i].selected = true;\n" +
            "    select.dispatchEvent(new Event('change'));\n" +
            "    changed = true;\n" +
            "    break;\n" +
            "  }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "select = document.getElementById(\"zakres_rzeczowo_finansowy_wydatki_arg1_kategoriaKosztow\");\n" +
            "for(var i = 0; i < select.options.length; i++) {\n" +
            "  if(select.options[i].innerHTML == \"arg3\") {\n" +
            "    select.options[i].selected = true;\n" +
            "    select.dispatchEvent(new Event('change'));\n" +
            "    break;\n" +
            "  }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "select = document.getElementById(\"zakres_rzeczowo_finansowy_wydatki_arg1_nazwaKosztu\");\n" +
            "select.value = \"arg4\";\n" +
            "select.dispatchEvent(new Event('change'));\n" +
            "\n" +
            "\n" +
            "select = document.getElementById(\"zakres_rzeczowo_finansowy_wydatki_arg1_opisKosztuWDanejPodkategorii\");\n" +
            "select.value = \"arg5\";\n" +
            "select.dispatchEvent(new Event('change'));\n" +
            "\n" +
            "\n" +
            "select = document.getElementById(\"zakres_rzeczowo_finansowy_wydatki_arg1_wartoscOgolem_tbbc_amount\");\n" +
            "select.value = \"arg6\";\n" +
            "select.dispatchEvent(new Event('change'));\n" +
            "\n" +
            "\n" +
            "select = document.getElementById(\"zakres_rzeczowo_finansowy_wydatki_arg1_wydatkiKwalifikowalne_tbbc_amount\");\n" +
            "select.value = \"arg7\";\n" +
            "select.dispatchEvent(new Event('change'));\n" +
            "\n" +
            "\n" +
            "select = document.getElementById(\"zakres_rzeczowo_finansowy_wydatki_arg1_wydatkiKwalifikowalneVat_tbbc_amount\");\n" +
            "select.value = \"arg8\";\n" +
            "select.dispatchEvent(new Event('change'));\n" +
            "\n" +
            "\n" +
            "select = document.getElementById(\"zakres_rzeczowo_finansowy_wydatki_arg1_wnioskowaneDofinansowanie_tbbc_amount\");\n" +
            "select.value = \"arg9\";\n" +
            "select.dispatchEvent(new Event('change'));";

    private ArrayList<String[]> costs;
    private FirefoxBrowser browser;
    private String prevMessage;

    public GTBCosts(ArrayList<String[]> costs, FirefoxBrowser browser) {
        this.costs = costs;
        this.browser = browser;
    }

    @Override
    protected Void call() throws Exception {

        for (int i = 0; i < costs.size(); i++) {
            String[] costAtr = costs.get(i);
            String script = FILL_COST_SCRIPT.replaceAll("arg1", Integer.toString(i));
            script = script.replace("arg2", costAtr[Cost.TASK].equals("null") ? "" : costAtr[Cost.TASK]);
            script = script.replace("arg3", costAtr[Cost.CATEGORY].equals("null") ? "" : costAtr[Cost.CATEGORY]);
            script = script.replace("arg4", costAtr[Cost.NAME].equals("null") ? "" : costAtr[Cost.NAME]);
            script = script.replace("arg5", costAtr[Cost.DESCRIPTION].equals("null") ? "" : costAtr[Cost.DESCRIPTION]);
            script = script.replace("arg6", costAtr[Cost.COSTS].equals("null") ? "" : costAtr[Cost.COSTS]);
            script = script.replace("arg7", costAtr[Cost.QUALIFIED_COSTS].equals("null") ? "" : costAtr[Cost.QUALIFIED_COSTS]);
            script = script.replace("arg8", costAtr[Cost.VAT].equals("null") ? "" : costAtr[Cost.VAT]);
            script = script.replace("arg9", costAtr[Cost.SUBSIDY].equals("null") ? "" : costAtr[Cost.SUBSIDY]);
            browser.executeVoidScript(script);
            updateProgress(i+1, costs.size());
            updateMessage(prevMessage + Integer.toString(i+1) + " na " + Integer.toString(costs.size()));
        }

        return null;
    }

    public void setPrevMessage(String prevMessage) {
        this.prevMessage = prevMessage;
    }
}
