package driver;

import core.interceptor.ConcreteLoggingRequest;
import core.interceptor.LogDispatcher;
import core.interceptor.LogInterceptor;
import core.interceptor.LoggingRequest;
import core.utils.Log;
import userinterface.StartUpUI;

import java.util.logging.Level;

/**
 *  Class containing main method
 * */
public class AppDriver {

    /**
     * Private constructor to hide implicit public constructor
     * */
    private AppDriver(){}

    /**
     *  Launches program
     *  @param args
     * */
    public static void main(String[] args)
    {


        /* Main: Boots User Interface */
        StartUpUI startUp = new StartUpUI();
        startUp.run();

         /*
            creation of the interceptor and registration of the interceptor to the dispatcher (prob go somewhere else)
         */
        LogInterceptor interceptor = new LogInterceptor() {
            Log log = new Log(getClass().getName());

            @Override
            public void onLogRequestReceived(LoggingRequest context) {
                switch(context.getLevel()) {
                    case WARNING:
                            if(context.getStringMessage().matches("")) {
                                log.logWarning(context.getException());
                            } else {
                                log.logWarning(context.getException(), context.getStringMessage());
                            }
                        break;
                    case INFO: log.logInfo(context.getStringMessage());
                        break;
                    case SEVERE:
                            if(context.getStringMessage().matches("")) {
                                log.logSevere(context.getException());
                            } else {
                                log.logSevere(context.getException(), context.getStringMessage());
                            }
                        break;
                    default:
                }
            }
        };

        LogDispatcher.getInstance().registerLogRequestInterceptor(interceptor);

    }
}
