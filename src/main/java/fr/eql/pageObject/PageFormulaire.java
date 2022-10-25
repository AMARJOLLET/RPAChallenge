package fr.eql.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;

public class PageFormulaire extends AbstractBlockPage{
    public PageFormulaire(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button")
    WebElement start;

    @FindBy(xpath = "//input[contains(@ng-reflect-name, 'label')]")
    List<WebElement> listInputFormulaire;

    @FindBy(xpath = "//label")
    List<WebElement> listLabelFormulaire;

    @FindBy(xpath = "//input[@value=\"Submit\"]")
    WebElement submitButton;

    public void letsGo (WebDriverWait wait, WebDriver driver) throws Throwable {
        seleniumTools.clickOnElement(wait, driver, start);
    }


    public Map<String, WebElement> returnMapFormulaire() {
        return outilsProjet.returnMapFormulaire(listLabelFormulaire,listInputFormulaire);
    }

    public void remplirFormulaire(WebDriverWait wait, WebDriver driver, Map<String, WebElement> map1, Map<String, String> map2) throws Throwable {
        outilsProjet.remplirFormulaire(wait, driver, map1, map2);
    }

    public void sudmit(WebDriverWait wait, WebDriver driver) throws Throwable {
        seleniumTools.clickOnElement(wait, driver, submitButton);
    }
}
