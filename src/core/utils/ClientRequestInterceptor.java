package core.utils;

/**
 * Created by David on 22/10/2016.
 */
public interface ClientRequestInterceptor {
    void onPreMarshalRequest(UnmarshaledRequest contextObject);
    void onPostMarshalRequest(MarshaledRequest contextObject);
}
