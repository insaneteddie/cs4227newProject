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

    /**
     * @param loggingRequest AbstractLoggingRequest
     * */
    public void setNextInChain(AbstractLoggingRequest loggingRequest){
        this.nextInChain = loggingRequest;
    }

    /**
     * @param type int
     * @param severity Severity
     * @param exception Exception
     * @param message String
     * @return
     * */
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

    /**
     * @param severity Severity
     * @param exception Exception
     * @param message String
     * @return String
     * */
    public abstract String messageThingy(Severity severity, Exception exception, String message);

    /**
     * @return Severity
     * */
    public Severity getLevel() {
        return level;
    }

    /**
     * @return Exception
     * */
    public Exception getException() {
        return exception;
    }

    /**
     * @return String
     * */
    public String getStringMessage() {
        return message;
    }

    /**
     * @return int
     * */
    @Override
    public int getType() { return type;}
}
