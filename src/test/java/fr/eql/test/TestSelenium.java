package fr.eql.test;


import fr.eql.pageObject.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import utils.SeleniumTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSelenium extends AbstractTestSelenium {

    // Chargement JDD
    ArrayList<Map<String,String>> listJdd = outilsProjet.loadCsvSeveralJDD("listRPAUser");

    public TestSelenium() throws IOException {
    }


    @Test
    void run() throws Throwable {
        LOGGER.info("DEBUT DU TEST");
        // Driver URL

        driver.get("https://www.rpachallenge.com/");

        PageFormulaire pageFormulaire = new PageFormulaire(driver);
        pageFormulaire.letsGo(wait, driver);

        for (int i = 0; i < 10; i++) {
            Map<String, WebElement> mapFormulaire = pageFormulaire.returnMapFormulaire();
            pageFormulaire.remplirFormulaire(wait, driver, mapFormulaire, listJdd.get(i));
            pageFormulaire.sudmit(wait, driver);
        }
        Thread.sleep(10000);

        LOGGER.info("FIN DU TEST");
    }
}
