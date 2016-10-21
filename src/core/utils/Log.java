package core.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by David on 21/10/2016.
 */
public class Log {
    public static final String FILEPATH = "./Resources/logFile.log";
    private static Logger logger;
    private static FileHandler fh;
    public static final String CLASSNAME = Log.class.getName();

    private Log(){}

    private static void setUpLogger(String name){
        logger =  Logger.getLogger(name);
        if(fh == null){
            try {
                fh = new FileHandler(FILEPATH);
            } catch (IOException e) {
                logException(CLASSNAME, e);
            }
        }
        logger.addHandler(fh);
        logger.setUseParentHandlers(false);
    }

    public static void logException(String name, Exception e){
        setUpLogger(name);
        logger.log(Level.WARNING, e.getMessage());
    }
}
