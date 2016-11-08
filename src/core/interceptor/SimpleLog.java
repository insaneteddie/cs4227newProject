package core.interceptor;

/**
 * Created by Cian Bolster on 01/11/2016.
 */
public class SimpleLog extends AbstractLoggingRequest {

    /** public constructor
     * @param type int
     * */
    public SimpleLog(int type) {
        this.type = type;
    }

    /**
     * @param exception Exception
     * @param message String
     * @return String
     * */
    @Override
    public String messageCreation(Exception exception, String message) {
        StringBuilder sb = new StringBuilder();

        sb.append(exception);
        sb.append(":\t");
        sb.append(message);

        return sb.toString();
    }
}
