package fr.eql.pageObject;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Logging;
import utils.OutilsProjet;
import utils.SeleniumTools;

public class AbstractBlockPage extends Logging {
    protected final static String snapshotsDirectory = "target/snapshots/";
    public Logger LOGGER = LoggerFactory.getLogger(className);

    protected OutilsProjet outilsProjet = new OutilsProjet();
    protected SeleniumTools seleniumTools = new SeleniumTools(className);

    protected final WebDriver driver;

    public AbstractBlockPage(WebDriver driver) {
        this.driver = driver;
    }
}
