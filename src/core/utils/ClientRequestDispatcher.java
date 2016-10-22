package core.utils;

import java.util.Vector;

/**
 * Created by David on 22/10/2016.
 */
public class ClientRequestDispatcher implements ClientRequestInterceptor {
    private static ClientRequestDispatcher dispatcher;
    Vector _interceptors;

    private ClientRequestDispatcher(){
        _interceptors = new Vector();
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
        Vector interceptors;
        synchronized(this){
            interceptors = (Vector)_interceptors.clone();
        }
        for(Object i : interceptors){
            ((ClientRequestInterceptor)i).onPreMarshalRequest(contextObject);
        }
    }

    @Override
    public void onPostMarshalRequest(MarshaledRequest contextObject) {
        //default implementation
        Vector interceptors;
        synchronized(this){
            interceptors = (Vector)_interceptors.clone();
        }
        for(Object i : interceptors){
            ((ClientRequestInterceptor)i).onPostMarshalRequest(contextObject);
        }
    }

    synchronized public void registerClientRequestInterceptor(ClientRequestInterceptor i){
        _interceptors.addElement(i);
    }

    synchronized public void removeClientRequestInterceptor(ClientRequestInterceptor i){
        _interceptors.remove(i);
    }
}
