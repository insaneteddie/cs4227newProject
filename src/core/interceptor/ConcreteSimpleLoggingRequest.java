package core.interceptor;

/**
 * Created by Cian Bolster on 03/11/2016.
 */
public class ConcreteSimpleLoggingRequest implements LoggingRequest {
    private Severity level;
    private Exception exception;
    private String message;
    private final int type = SimpleLog.SIMPLELOG;

    private String finalMessage = "";

    public ConcreteSimpleLoggingRequest(Severity level, Exception exception, String message) {
        this.level = level;
        this.exception = exception;
        this.message = message;
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

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setFinalMessage(String finalMessage){
        this.finalMessage = finalMessage;
    }

}
