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

    /**
     * @param classname
     * */
    public Log(String classname){
        setUpLogger(classname);
    }

    /**
     * @param name
     * */
    private void setUpLogger(String name){
        logger =  Logger.getLogger(name);
        if(fh == null){
            try {
                synchronized (logger) {
                    fh = new FileHandler(FILEPATH);
                    logger.addHandler(fh);
                    logger.setUseParentHandlers(false);
                }
            } catch (IOException e) {
                logSevere(e, CLASSNAME);
            }
        }
    }

    /**
     * @param e
     * @param desc
     * */
    public void logWarning(Exception e, String desc){
        logger.log(Level.WARNING, desc + "\n" + e.getMessage());
    }

    /**
     * @param e
     * */
    public void logWarning(Exception e){
        logWarning(e, "");
    }

    /**
     * @param desc
     * */
    public void logInfo(String desc){
        logger.log(Level.INFO, desc);
    }

    /**
     * @param e
     * */
    public void logSevere(Exception e){
        logSevere(e, "");
    }

    /**
     * @param e
     * @param desc
     * */
    public void logSevere(Exception e, String desc){
        logger.log(Level.SEVERE, desc + "\n" + e.getMessage());
    }
}
