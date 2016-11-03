package core.interceptor;

/**
 * Created by Cian Bolster on 01/11/2016.
 */
public class SimpleLog extends AbstractLoggingRequest {

    /** public constructor
     * @param type int
     * */
    public SimpleLog(int type) {
        this.type = type;
    }

    /**
     * @param severity Severity
     * @param exception Exception
     * @param message String
     * @return String
     * */
    @Override
    public String messageCreation(LoggingRequest.Severity severity, Exception exception, String message) {
        StringBuilder sb = new StringBuilder();

        sb.append(severity);
        sb.append(":\t");
        sb.append(exception);
        sb.append(":\t");
        sb.append(message);

        return sb.toString();
    }
}
