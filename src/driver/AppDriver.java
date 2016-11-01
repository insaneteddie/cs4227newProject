package driver;

import core.interceptor.LogDispatcher;
import core.interceptor.LogInterceptor;
import core.interceptor.LoggingRequest;
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
            @Override
            public void onDatabaseRequestReceived(LoggingRequest context) {
                context.getRequest();
            }
        };

        LogDispatcher.getInstance().registerDatabaseRequestInterceptor(interceptor);
    }
}
