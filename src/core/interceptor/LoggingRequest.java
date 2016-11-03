package core.interceptor;

/**
 * Created by David on 22/10/2016.
 *
 * Interface for context object for interceptor.
 */
public interface LoggingRequest {
    /** enumeration describing the levels of severity for an error*/
    enum Severity{INFO, WARNING, SEVERE}
    /**
     * @return Severity
     * */
    Severity getLevel();
    /**
     * @return Exception
     * */
    Exception getException();
    /**
     * @return String
     * */
    String getStringMessage();
    /**
     * @return int
     * */
    int getType();

    /**
     * params: String final message
     */
    void setFinalMessage(String finalMessage);

}
