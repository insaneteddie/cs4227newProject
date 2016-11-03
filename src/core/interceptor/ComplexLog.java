package core.interceptor;

/**
 * Created by Cian Bolster on 01/11/2016.
 */
public class ComplexLog extends AbstractLoggingRequest {

    /** public constructor
     * @param type int
     * */
    public ComplexLog(int type) {
        this.type = type;
    }

    /**
     * @param severity Severity
     * @param exception Exception
     * @param message String
     * @return String
     * */
    @Override
    public String messageCreation(Severity severity, Exception exception, String message) {
        StringBuilder sb = new StringBuilder();

        sb.append(severity);
        sb.append(exception);
        sb.append(message);

        return sb.toString();
    }

}
