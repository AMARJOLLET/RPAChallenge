package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OutilsProjet extends Logging {
    Logger LOGGER = LoggerFactory.getLogger(className);
    SeleniumTools seleniumTools = new SeleniumTools(className);


    public Map<String, String> chargementCSVJDD(String fileName) throws IOException {
        String csvFilePath = "src/main/resources/JDD/csv/" + fileName + ".csv";
        Map<String, String> jdd = new HashMap<>();
        LOGGER.info("---------- Fichier JDD chargé : " + csvFilePath + " ----------");
        List<String[]> list =
                Files.lines(Paths.get(csvFilePath))
                        .map(line -> line.split("\\\\r?\\\\n"))
                        .collect(Collectors.toList());
        if (list.size() > 2) {
            LOGGER.error("***** Mauvais format de fichier CSV - trop de lignes (2 lignes attendues : 1 ligne keys et 1 ligne values *****");
        }
        String[] titres = list.get(0)[0].split(",");
        String[] valeurs = list.get(1)[0].split((","));
        for (int i = 0; i < titres.length; i++) {
            jdd.put(titres[i], valeurs[i]);
        }
        jdd.forEach((key, value) -> LOGGER.info(key + " = " + value));
        return jdd;
    }

    public ArrayList<Map<String, String>> loadCsvSeveralJDD (String fileName) throws IOException {
        String csvFilePath = "src/main/resources/JDD/csv/" + fileName + ".csv";
        ArrayList<Map<String, String>> listJDD = new ArrayList<>();
        LOGGER.info("---------- Fichier JDD chargé : " + csvFilePath + " ----------");
        List<String[]> list =
                Files.lines(Paths.get(csvFilePath))
                        .map(line -> line.split("\\\\r?\\\\n"))
                        .collect(Collectors.toList());
        for (int j = 1; j < list.size(); j++) {
            Map<String, String> jdd = new HashMap<>();

            String[] titres = list.get(0)[0].split(",");
            String[] val = list.get(j)[0].split((","));
            for (int i = 0; i < titres.length; i++) {
                jdd.put(titres[i], val[i]);
            }
            listJDD.add(jdd);
        }
        return listJDD;
    }


    public Map<String, WebElement> returnMapFormulaire(List<WebElement> listLibelle, List<WebElement> listInput) {
        return IntStream.range(0, listInput.size())
                .boxed()
                .parallel()
                .collect(Collectors.toMap(
                        (Integer i) -> listLibelle.get(i).getText(),
                        listInput::get
                ));
    }

    public void remplirFormulaire(WebDriverWait wait, WebDriver driver, Map<String, WebElement> map1, Map<String, String> map2) throws Throwable {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        map1.keySet().stream()
                .parallel()
                .forEach(k -> {
                    WebElement we = map1.get(k);
                    String string = map2.get(k);
//                    js.executeScript("arguments[0].click;", we);
//                    js.executeScript("arguments[0].value='';", we);
                    js.executeScript("arguments[0].value=arguments[1];", we, string);
                });
    }
}

