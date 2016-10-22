package core.utils;

import java.util.Vector;

/**
 * Created by David on 22/10/2016.
 */
public class ClientRequestDispatcher implements ClientRequestInterceptor {
    private static ClientRequestDispatcher dispatcher;
    Vector interceptors;

    private ClientRequestDispatcher(){
        interceptors = new Vector();
    }

    public static ClientRequestDispatcher getInstance(){
        if(dispatcher == null){
            dispatcher = new ClientRequestDispatcher();
        }
        return dispatcher;
    }

    @Override
    public void onPreMarshalRequest(UnmarshaledRequest contextObject) {

    }

    @Override
    public void onPostMarshalRequest(MarshaledRequest contextObject) {

    }

    synchronized public void registerClientRequestInterceptor(ClientRequestInterceptor i){
        interceptors.addElement(i);
    }

    synchronized public void removeClientRequestInterceptor(ClientRequestInterceptor i){
        interceptors.remove(i);
    }
}
