package core.utils;

/**
 * Created by David on 22/10/2016.
 */
public interface ClientRequestInterceptor {
    /**
     * @param contextObject
     * */
    void onPreMarshalRequest(UnmarshaledRequest contextObject);
    /**
     * @param contextObject
     * */
    void onPostMarshalRequest(MarshaledRequest contextObject);
}
