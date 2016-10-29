package core.interceptor;

import database.DatabaseRequest;

import java.util.ArrayList;

/**
 * Created by David on 22/10/2016
 *
 * Modified by Cian Bolster on 29/10/2016.
 *
 * Base implementation for the DatabaseRequestDispatcher class that will as of now log Database Interactions through the
 * logger class.
 */
public class DatabaseRequestDispatcher implements DatabaseRequestInterceptor {

    private static DatabaseRequestDispatcher dispatcher;
    ArrayList interceptors;

    private DatabaseRequestDispatcher(){
        interceptors = new ArrayList();
    }

    public static DatabaseRequestDispatcher getInstance(){
        if(dispatcher == null){
            dispatcher = new DatabaseRequestDispatcher();
        }
        return dispatcher;
    }

    /**
     * @param i
     * */
    public synchronized void registerDatabaseRequestInterceptor(DatabaseRequestInterceptor i){
        interceptors.add(i);
    }

    /**
     * @param i
     * */
    public synchronized void removeDatabaseRequestInterceptor(DatabaseRequestInterceptor i){
        interceptors.remove(i);
    }


    @Override
    public void onDatabaseRequestReceived(DatabaseRequest context) {
        ArrayList interceptorList;
        synchronized(this){
            interceptorList = (ArrayList) this.interceptors.clone();
        }
        for(Object i : interceptorList){
            ((DatabaseRequestInterceptor)i).onDatabaseRequestReceived(context);
        }
    }
}
