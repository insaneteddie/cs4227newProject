package core.interceptor;

/**
 * Created by Cian Bolster on 01/11/2016.
 */
public class ConcreteInfoRequest extends AbstractLoggingRequest {

    public ConcreteInfoRequest(int type) {
        this.type = type;
    }

    @Override
    public String messageThingy(Severity severity, Exception exception, String message) {
        StringBuilder sb = new StringBuilder();

        sb.append(getLevel());
        sb.append(getException());
        sb.append(getStringMessage());

        return sb.toString();
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
