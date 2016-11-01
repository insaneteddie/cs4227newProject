package core.interceptor;

/**
 * Created by Cian Bolster on 01/11/2016.
 */
public class ConcreteLoggingRequest implements LoggingRequest {
    
    private Severity level;
    private Exception exception;
    private String message;

    public ConcreteLoggingRequest(Severity level, Exception exception, String message) {
        this.level = level;
        this.exception = exception;
        this.message = message;
    }

    public ConcreteLoggingRequest(Severity level, Exception exception) {
        this(level, exception, "");
    }

    public ConcreteLoggingRequest(Severity level, String message) {
        this(level, null, message);
    }

    @Override
    public Severity getLevel() {
        return level;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getStringMessage() {
        return message;
    }
}
