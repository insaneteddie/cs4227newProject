package core.interceptor;

/**
 * Created by Cian Bolster on 01/11/2016.
 */
public class ConcreteLoggingRequest implements LoggingRequest {

    private String level;
    private Exception exception;
    private String message;

    public ConcreteLoggingRequest(String level, Exception exception, String message) {
        this.level = level;
        this.exception = exception;
        this.message = message;
    }

    public ConcreteLoggingRequest(String level, Exception exception) {
        this(level, exception, null);
    }

    public ConcreteLoggingRequest(String level, String message) {
        this(level, null, message);
    }

    @Override
    public String getLevel() {
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
