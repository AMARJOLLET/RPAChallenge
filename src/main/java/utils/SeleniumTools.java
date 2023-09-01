package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SeleniumTools extends Logging {
    private final String className;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd_HH.mm.ss");
    private static final Date date = new Date();
    private final String dateExecution = sdf.format(date.getTime());

    public SeleniumTools(String className) {
        this.className = className;
    }

    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws IOException {
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);
    }

    public void snapshot(String className, WebDriver driver, Throwable error) throws Throwable {
        String snapfile = "/target/snapshots" + className + "/" + dateExecution + "_" + className + ".png";
        takeSnapShot(driver, snapfile);
        throw error;
    }

    public void clickOnElement(WebDriverWait wait, WebElement we) {
        wait.until(ExpectedConditions.elementToBeClickable(we));
        we.click();
    }

    public void scrollToElement(WebDriverWait wait, WebDriver driver, WebElement we) throws Throwable {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', 'border:2px solid red; background:yellow')", we);
            js.executeScript("arguments[0].scrollIntoView();", we);
            wait.until(ExpectedConditions.elementToBeClickable(we));
        } catch (Throwable e) {
            snapshot(className, driver, e);
        }
    }

    public void sendKey(WebDriverWait wait, WebDriver driver, WebElement we, String string) throws Throwable {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(we));
            we.click();
            we.clear();
            we.sendKeys(string);
        } catch (Throwable e) {
            snapshot(className, driver, e);
        }
    }
}
