package core.interceptor;

/**
 * Created by Cian Bolster on 01/11/2016.
 */
public class ConcreteSevereRequest extends AbstractLoggingRequest {

    public ConcreteSevereRequest(int type) {
        this.type = type;
    }

    @Override
    public String getDetails() {
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
