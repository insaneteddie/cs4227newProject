package core.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by David on 21/10/2016.
 */
public class Log {
    public static final String filepath = "./Resources/logFile.log";
    private static Logger logger;
    private static FileHandler fh;

    private Log(){}

    private void setUpLogger(String name){
        logger =  Logger.getLogger(name);
        if(fh == null){
            try {
                fh = new FileHandler(filepath);
            } catch (IOException e) {
                logException(getClass().getName(), e);
            }
        }
        logger.addHandler(fh);
        logger.setUseParentHandlers(false);
    }

    public void logException(String name, Exception e){
        setUpLogger(name);
        logger.log(Level.WARNING, e.getMessage());
    }
}
