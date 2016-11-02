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

    private LogDispatcher(){
        interceptors = new ArrayList();
    }


        /*method that returns the current instance of the dispatcher and creates one if none exist.*/

    public static LogDispatcher getInstance(){
        if(dispatcher == null){
            dispatcher = new LogDispatcher();
        }
        return dispatcher;
    }

    /**
     * @param i
     * */
    public synchronized void registerLogRequestInterceptor(LogInterceptor i){
        interceptors.add(i);
    }

    /**
     * @param i
     * */
    public synchronized void removeLogRequestInterceptor(LogInterceptor i){
        interceptors.remove(i);
    }

    public static AbstractLoggingRequest getChain(){
        AbstractLoggingRequest logInfo = new ConcreteInfoRequest(AbstractLoggingRequest.INFO);
        AbstractLoggingRequest logWarning = new ConcreteWarningRequest(AbstractLoggingRequest.WARNING);
        AbstractLoggingRequest logSevere = new ConcreteSevereRequest(AbstractLoggingRequest.SEVERE);
        AbstractLoggingRequest logWarningMes = new ConcreteWarningRequest(AbstractLoggingRequest.WARNINGMES);
        AbstractLoggingRequest logSevereMes = new ConcreteSevereRequest(AbstractLoggingRequest.SEVERMES);

        logInfo.setNextInChain(logWarning);
        logWarning.setNextInChain(logSevere);
        logSevere.setNextInChain(logWarningMes);
        logWarningMes.setNextInChain(logSevereMes);

        return logInfo;
    }


    @Override
    public void onLogRequestReceived(LoggingRequest context) {
        ArrayList interceptorList;
        AbstractLoggingRequest loggingChain = getChain();
        synchronized(this){
            interceptorList = (ArrayList) this.interceptors.clone();
        }
        for(Object i : interceptorList){
            //loggingChain;
            ((LogInterceptor)i).onLogRequestReceived(context);
        }
    }
}
