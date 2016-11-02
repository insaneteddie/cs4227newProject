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

    public void setNextInChain(AbstractLoggingRequest loggingRequest){
        this.nextInChain = loggingRequest;
    }

    public void getDetails(int type, Severity severity, Exception exception, String message){
        if(this.type == type){
            messageThingy(severity, exception, message);
        }
        if(nextInChain != null){
            nextInChain.getDetails(type, severity, exception, message);
        }
    }

    public abstract String messageThingy(Severity severity, Exception exception, String message);

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
