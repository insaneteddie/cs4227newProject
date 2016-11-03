package core.interceptor;

/**
 * Created by Cian Bolster on 01/11/2016.
 */
public abstract class AbstractLoggingRequest implements LoggingRequest {
    public static final int INFO = 1;
    public static final int WARNING = 2;
    public static final int SEVERE = 3;
    public static final int WARNINGMES = 4;
    public static final int SEVEREMES = 5;

    protected int type;

    protected Severity level;
    protected Exception exception;
    protected String message;

    protected AbstractLoggingRequest nextInChain;

    public void setNextInChain(AbstractLoggingRequest loggingRequest){
        this.nextInChain = loggingRequest;
    }

    public String getDetails(int type, Severity severity, Exception exception, String message){
        String details = "";

        if(this.type == type){
            details = messageThingy(severity, exception, message);
        }
        else if(nextInChain != null){
            details = nextInChain.getDetails(type, severity, exception, message);
        }

        return details;
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

    @Override
    public int getType() { return type;}
}
