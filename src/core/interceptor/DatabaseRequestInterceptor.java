package core.interceptor;

import database.DatabaseRequest;

/**
 * Created by David on 22/10/2016
 *
 * Modified by Cian Bolster on 29/10/2016.
 *
 * Interface for the DatabaseRequestDispatcher class that defines the takeAction method
 */


public interface DatabaseRequestInterceptor {

    void onDatabaseRequestReceived(DatabaseRequest context);
}
