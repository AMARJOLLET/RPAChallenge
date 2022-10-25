package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class Logging {
    public String className ;


    public Logging() {
        this.className = this.getClass().getSimpleName();
        System.setProperty("logFileName", this.className);
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        File file = new File("src/main/resources/log4j2.properties");
        context.setConfigLocation(file.toURI());
    }

}
