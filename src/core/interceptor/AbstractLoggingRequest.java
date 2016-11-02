package core.interceptor;

/**
 * Created by Cian Bolster on 01/11/2016.
 */
public abstract class AbstractLoggingRequest implements LoggingRequest {
    public static int INFO = 1;
    public static int WARNING = 2;
    public static int SEVERE = 3;
    public static int WARNINGMES = 4;
    public static int SEVERMES = 5;

    protected int type;

    protected Severity level;
    protected Exception exception;
    protected String message;

    protected AbstractLoggingRequest nextInChain;

    public AbstractLoggingRequest() {
        this(Severity.INFO, null, "");
    }

    public AbstractLoggingRequest(Severity level, Exception exception, String message) {
        this.level = level;
        this.exception = exception;
        this.message = message;
    }

    public void setNextInChain(AbstractLoggingRequest loggingRequest){
        this.nextInChain = loggingRequest;
    }

    public abstract String getDetails();

    public Severity getLevel() {
        return level;
    }

    public Exception getException() {
        return exception;
    }

    public String getStringMessage() {
        return message;
    }
}
