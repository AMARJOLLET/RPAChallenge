package fr.eql.test;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.SeleniumTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class TestSelenium extends AbstractTestSelenium {

    // Chargement JDD
    ArrayList<Map<String,String>> listJdd = outilsProjet.loadCsvSeveralJDD("listRPAUser");

    public TestSelenium() throws IOException {
    }

    @FindBy(xpath = "//button")
    WebElement start;

    @FindBy(xpath = "//input[contains(@ng-reflect-name, 'label')]")
    List<WebElement> listInputFormulaire;

    @FindBy(xpath = "//label")
    List<WebElement> listLabelFormulaire;

    @FindBy(xpath = "//input[@value=\"Submit\"]")
    WebElement submitButton;

    @FindBy(xpath = "//input[@ng-reflect-name='labelAddress']")
    WebElement adress;

    @FindBy(xpath = "//input[@ng-reflect-name='labelRole']")
    WebElement role;

    @FindBy(xpath = "//input[@ng-reflect-name='labelPhone']")
    WebElement phone;

    @FindBy(xpath = "//input[@ng-reflect-name='labelLastName']")
    WebElement lastName;

    @FindBy(xpath = "//input[@ng-reflect-name='labelFirstName']")
    WebElement firstName;

    @FindBy(xpath = "//input[@ng-reflect-name='labelCompanyName']")
    WebElement companyName;

    @FindBy(xpath = "//input[@ng-reflect-name='labelEmail']")
    WebElement email;

    public void letsGo (WebDriverWait wait) {
        seleniumTools.clickOnElement(wait, start);
    }

    public Map<String, WebElement> changeToMap(){
        Map<String, WebElement> map = new HashMap<>();
        map.put("Address",adress);
        map.put("Role in Company",role);
        map.put("Phone Number",phone);
        map.put("Last Name",lastName);
        map.put("Email",email);
        map.put("Company Name",companyName);
        map.put("First Name",firstName);
        return map;
    }


    /**
     * Utilisation WebELement Selenium avec sendkey javascript
     * temps : 500ms
     */
    @Test
    void run1() throws IOException {
        LOGGER.info("DEBUT DU TEST");
        // Driver URL

        driver.get("https://www.rpachallenge.com/");
        PageFactory.initElements(driver, this);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        letsGo(wait);

        IntStream.range(0, 10).forEach(i -> {
            PageFactory.initElements(driver, this);
            changeToMap().entrySet().stream()
                    .parallel()
                    .forEach(v -> js.executeScript("arguments[0].value=arguments[1];", v.getValue(), listJdd.get(i).get(v.getKey())));
            js.executeScript("arguments[0].click();", submitButton);
        });

        SeleniumTools.takeSnapShot(driver, "target/data/finish1.png");

        LOGGER.info("FIN DU TEST");
    }

    /**
     * Utilisation javascript seulement
     * temps : 445ms
     */
    @Test
    void run2() throws IOException {
        LOGGER.info("DEBUT DU TEST");
        // Driver URL

        driver.get("https://www.rpachallenge.com/");
        PageFactory.initElements(driver, this);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        letsGo(wait);

        IntStream.range(0, 10).forEach(i -> {
            js.executeScript("document.querySelector('input[ng-reflect-name=\"labelAddress\"]').value=arguments[0]", listJdd.get(i).get("Address"));
            js.executeScript("document.querySelector('input[ng-reflect-name=\"labelRole\"]').value=arguments[0]", listJdd.get(i).get("Role in Company"));
            js.executeScript("document.querySelector('input[ng-reflect-name=\"labelPhone\"]').value=arguments[0]", listJdd.get(i).get("Phone Number"));
            js.executeScript("document.querySelector('input[ng-reflect-name=\"labelLastName\"]').value=arguments[0]", listJdd.get(i).get("Last Name"));
            js.executeScript("document.querySelector('input[ng-reflect-name=\"labelEmail\"]').value=arguments[0]", listJdd.get(i).get("Email"));
            js.executeScript("document.querySelector('input[ng-reflect-name=\"labelCompanyName\"]').value=arguments[0]", listJdd.get(i).get("Company Name"));
            js.executeScript("document.querySelector('input[ng-reflect-name=\"labelFirstName\"]').value=arguments[0]", listJdd.get(i).get("First Name"));

            js.executeScript("document.querySelector('input[value=\"Submit\"]').click()");
        });

        SeleniumTools.takeSnapShot(driver, "target/data/finish2.png");

        LOGGER.info("FIN DU TEST");
    }

    /**
     * Utilisation javascript seulement avec multithreading
     * temps : 411ms
     */
    @Test
    void run3() throws IOException {
        LOGGER.info("DEBUT DU TEST");
        // Driver URL

        driver.get("https://www.rpachallenge.com/");
        PageFactory.initElements(driver, this);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        letsGo(wait);

        int nbProcs = Runtime.getRuntime().availableProcessors();

        ExecutorService executorService = Executors.newFixedThreadPool(nbProcs);
        IntStream.range(0, 10).forEach(i -> {
            List<Runnable> tasks = new ArrayList<>();
            tasks.add(() -> js.executeScript("document.querySelector('input[ng-reflect-name=\"labelAddress\"]').value=arguments[0]", listJdd.get(i).get("Address")));
            tasks.add(() -> js.executeScript("document.querySelector('input[ng-reflect-name=\"labelRole\"]').value=arguments[0]", listJdd.get(i).get("Role in Company")));
            tasks.add(() -> js.executeScript("document.querySelector('input[ng-reflect-name=\"labelPhone\"]').value=arguments[0]", listJdd.get(i).get("Phone Number")));
            tasks.add(() -> js.executeScript("document.querySelector('input[ng-reflect-name=\"labelLastName\"]').value=arguments[0]", listJdd.get(i).get("Last Name")));
            tasks.add(() -> js.executeScript("document.querySelector('input[ng-reflect-name=\"labelEmail\"]').value=arguments[0]", listJdd.get(i).get("Email")));
            tasks.add(() -> js.executeScript("document.querySelector('input[ng-reflect-name=\"labelCompanyName\"]').value=arguments[0]", listJdd.get(i).get("Company Name")));
            tasks.add(() -> js.executeScript("document.querySelector('input[ng-reflect-name=\"labelFirstName\"]').value=arguments[0]", listJdd.get(i).get("First Name")));
            List<Callable<Object>> callableTasks = tasks.stream()
                    .map(Executors::callable)
                    .collect(Collectors.toList());
            try {
                executorService.invokeAll(callableTasks);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            js.executeScript("document.querySelector('input[value=\"Submit\"]').click()");
        });
        executorService.shutdown();

        SeleniumTools.takeSnapShot(driver, "target/data/finish3.png");

        LOGGER.info("FIN DU TEST");
    }
}
