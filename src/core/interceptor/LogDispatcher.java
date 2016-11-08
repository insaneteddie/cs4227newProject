package core.interceptor;

import java.util.ArrayList;

/**
 * Created by David on 22/10/2016
 *
 * Modified by Cian Bolster on 29/10/2016.
 *
 * Base implementation for the LogDispatcher class that will as of now log Database Interactions through the
 * logger class.
 */
public class LogDispatcher implements LogInterceptor {

    private static LogDispatcher dispatcher;
    ArrayList interceptors;

    /** private constructor*/
    private LogDispatcher(){
        interceptors = new ArrayList();
    }


    /**method that returns the current instance of the dispatcher and creates one if none exist.
    * @return LogDispatcher
     * */
    public static LogDispatcher getInstance(){
        if(dispatcher == null){
            dispatcher = new LogDispatcher();
        }
        return dispatcher;
    }

    /**
     * @param i LogInterceptor
     * */
    public synchronized void registerLogRequestInterceptor(LogInterceptor i){
        interceptors.add(i);
    }

    /**
     * @param i LogInterceptor
     * */
    public synchronized void removeLogRequestInterceptor(LogInterceptor i){
        interceptors.remove(i);
    }

    public static AbstractLoggingRequest getChain(){
        AbstractLoggingRequest logSimple = new SimpleLog(AbstractLoggingRequest.SIMPLELOG);
        AbstractLoggingRequest logComplex = new ComplexLog(AbstractLoggingRequest.COMPLEXLOG);

        logSimple.setNextInChain(logComplex);

        return logSimple;
    }

    /**
     * @param context LoggingRequest
     * */
    @Override
    public void onLogRequestReceived(LoggingRequest context) {
        ArrayList interceptorList;
        AbstractLoggingRequest loggingChain = getChain();
        synchronized(this){
            interceptorList = (ArrayList) this.interceptors.clone();
        }
        for(Object i : interceptorList){
            loggingChain.getDetails(context.getType(), context.getException(), context.getStringMessage());
            context.setFinalMessage(loggingChain.getFullMessage());
            ((LogInterceptor)i).onLogRequestReceived(context);
        }
    }
}
