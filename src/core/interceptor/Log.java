package core.interceptor;

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
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
            }
        }
    }

    /**
     * @param type LoggingRequest.Severity
     * @param message String
     * */
    public void logMessage(LoggingRequest.Severity type, String message){
        switch(type){
                case WARNING:
                    logger.log(Level.WARNING, message);
                    break;
                case SEVERE:
                    logger.log(Level.SEVERE, message);
                    break;
                case INFO:
                    logger.log(Level.INFO, message);
                    break;
            default:
            }
        }
}
