package core.interceptor;

/**
 * Created by Cian Bolster on 01/11/2016.
 */
public class ConcreteWarningRequest extends AbstractLoggingRequest {

    /**public constructor
     * @param type int
     * */
    public ConcreteWarningRequest(int type) {
        this.type = type;
    }

    /**
     * @param severity Severity
     * @param exception Exception
     * @param message String
     * @return String*/
    @Override
    public String messageThingy(Severity severity, Exception exception, String message) {
        StringBuilder sb = new StringBuilder();

        sb.append(getLevel());
        sb.append(getException());
        sb.append(getStringMessage());

        return sb.toString();
    }

    /**
     * @return Severity
     * */
    @Override
    public Severity getLevel() {
        return level;
    }

    /**
     * @return Exception
     * */
    @Override
    public Exception getException() {
        return exception;
    }

    /**
     * @return String
     * */
    @Override
    public String getStringMessage() {
        return message;
    }
}
