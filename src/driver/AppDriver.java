package driver;

import core.interceptor.LogDispatcher;
import core.interceptor.LogInterceptor;
import core.interceptor.LoggingRequest;
import core.interceptor.Log;
import userinterface.StartUpUI;

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
                log.logMessage(context.getLevel(),context.getFinalMessage());
            }
        };

        LogDispatcher.getInstance().registerLogRequestInterceptor(interceptor);

    }
}
