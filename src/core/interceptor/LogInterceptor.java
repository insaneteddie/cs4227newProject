package core.interceptor;

/**
 * Created by David on 22/10/2016
 *
 * Modified by Cian Bolster on 29/10/2016.
 *
 * Interface for the LogDispatcher class that defines the onDatabaseRequestReceived() method
 */


public interface LogInterceptor {

    void onDatabaseRequestReceived(LoggingRequest context);
}
