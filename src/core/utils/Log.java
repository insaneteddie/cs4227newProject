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
    private Logger logger;
    private static FileHandler fh;
    public static final String CLASSNAME = Log.class.getName();

    public Log(String classname){
        setUpLogger(classname);
    }

    private void setUpLogger(String name){
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

    public void logException(Exception e){
        logger.log(Level.WARNING, e.getMessage());
    }
    public void logException(String msg, Exception e){
        logger.log(Level.WARNING, msg + "\t" + e.getMessage());
    }
    public void logException(String desc){
        logger.log(Level.INFO, desc);
    }
}
