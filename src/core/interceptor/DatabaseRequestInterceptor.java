package core.interceptor;

import database.DatabaseMarshalRequest;
import database.DatabaseUnmarshalRequest;

/**
 * Created by David on 22/10/2016
 *
 * Modified by Cian Bolster on 29/10/2016.
 *
 * Interface for the DatabaseRequestDispatcher class that defines the takeAction method
 */


public interface DatabaseRequestInterceptor {

    void onDatabaseMarshalRequest(DatabaseMarshalRequest context);
    void onDatabaseUnmarshalRequest(DatabaseUnmarshalRequest context);
}
