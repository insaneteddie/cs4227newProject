package core.interceptor;

/**
 * Created by Cian Bolster on 01/11/2016.
 */
public abstract class AbstractLoggingRequest{
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
     * @return AbstractLoggingRequest nextInChain
     */
    public AbstractLoggingRequest getNextInChain(){
        return nextInChain;
    }

    /**
     * @param type int
     * @param exception Exception
     * @param message String
     * @return String
     * */
    public  void getDetails(int type, Exception exception, String message){
        if(this.type == type){
            fullMessage = messageCreation(exception, message);
        }
        else if(nextInChain != null){
            nextInChain.getDetails(type, exception, message);
        }
    }

    /**
     * @param exception Exception
     * @param message String
     * @return String
     * */
    public abstract String messageCreation(Exception exception, String message);

    public int getType(){
        return type;
    }

    public String getFullMessage(){
        return fullMessage;
    }
}
