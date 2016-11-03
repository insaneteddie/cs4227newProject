package core.interceptor;

/**
 * Created by Cian Bolster on 01/11/2016.
 */
public abstract class AbstractLoggingRequest implements LoggingRequest {
    public static final int SIMPLELOG = 1;
    public static final int COMPLEXLOG = 2;

    protected int type;

    protected AbstractLoggingRequest nextInChain;

    protected String fullMessage;

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
     * @return String
     * */
    public void getDetails(int type, Severity severity, Exception exception, String message){
        if(this.type == type){
            fullMessage = messageCreation(severity, exception, message);
        }
        else if(nextInChain != null){
            nextInChain.getDetails(type, severity, exception, message);
        }
    }

    /**
     * @param severity Severity
     * @param exception Exception
     * @param message String
     * @return String
     * */
    public abstract String messageCreation(Severity severity, Exception exception, String message);

    /**
     * @return String
     * */
    @Override
    public String getStringMessage() {
        return fullMessage;
    }

    /**
     * @return int
     * */
    @Override
    public int getType() { return type;}
}
