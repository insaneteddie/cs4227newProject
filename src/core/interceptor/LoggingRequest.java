package core.interceptor;

/**
 * Created by David on 22/10/2016.
 *
 * Interface for context object for interceptor.
 */
public interface LoggingRequest {

    String getLevel();
    Exception getException();
    String getStringMessage();
}
