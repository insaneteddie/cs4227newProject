package driver;

import core.interceptor.DatabaseRequestDispatcher;
import core.interceptor.DatabaseRequestInterceptor;
import database.DatabaseRequest;
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
        DatabaseRequestInterceptor interceptor = new DatabaseRequestInterceptor() {
            @Override
            public void onDatabaseRequestReceived(DatabaseRequest context) {
                context.getRequest();
            }
        };

        DatabaseRequestDispatcher.getInstance().registerDatabaseRequestInterceptor(interceptor);
    }
}
