package core.utils;

import java.util.ArrayList;

/**
 * Created by David on 22/10/2016.
 */
public class ClientRequestDispatcher implements ClientRequestInterceptor {
    private static ClientRequestDispatcher dispatcher;
    ArrayList interceptors;

    private ClientRequestDispatcher(){
        interceptors = new ArrayList();
    }

    public static ClientRequestDispatcher getInstance(){
        if(dispatcher == null){
            dispatcher = new ClientRequestDispatcher();
        }
        return dispatcher;
    }

    @Override
    public void onPreMarshalRequest(UnmarshaledRequest contextObject) {
        //default implementation
        ArrayList interceptorsList;
        synchronized(this){
            interceptorsList = (ArrayList) this.interceptors.clone();
        }
        for(Object i : interceptorsList){
            ((ClientRequestInterceptor)i).onPreMarshalRequest(contextObject);
        }
    }

    @Override
    public void onPostMarshalRequest(MarshaledRequest contextObject) {
        //default implementation
        ArrayList interceptorList;
        synchronized(this){
            interceptorList = (ArrayList) this.interceptors.clone();
        }
        for(Object i : interceptorList){
            ((ClientRequestInterceptor)i).onPostMarshalRequest(contextObject);
        }
    }

    /**
     * @param i
     * */
    public synchronized void registerClientRequestInterceptor(ClientRequestInterceptor i){
        interceptors.add(i);
    }

    /**
     * @param i
     * */
    public synchronized void removeClientRequestInterceptor(ClientRequestInterceptor i){
        interceptors.remove(i);
    }
}
