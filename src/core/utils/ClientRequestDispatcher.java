package core.utils;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by David on 22/10/2016.
 */
public class ClientRequestDispatcher implements ClientRequestInterceptor {
    private static ClientRequestDispatcher dispatcher;
    ArrayList _interceptors;

    private ClientRequestDispatcher(){
        _interceptors = new ArrayList();
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
        ArrayList interceptors;
        synchronized(this){
            interceptors = (ArrayList) _interceptors.clone();
        }
        for(Object i : interceptors){
            ((ClientRequestInterceptor)i).onPreMarshalRequest(contextObject);
        }
    }

    @Override
    public void onPostMarshalRequest(MarshaledRequest contextObject) {
        //default implementation
        ArrayList interceptors;
        synchronized(this){
            interceptors = (ArrayList) _interceptors.clone();
        }
        for(Object i : interceptors){
            ((ClientRequestInterceptor)i).onPostMarshalRequest(contextObject);
        }
    }

    synchronized public void registerClientRequestInterceptor(ClientRequestInterceptor i){
        _interceptors.add(i);
    }

    synchronized public void removeClientRequestInterceptor(ClientRequestInterceptor i){
        _interceptors.remove(i);
    }
}
