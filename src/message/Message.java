package message;

import java.util.ArrayList;

/**
 *  created by c_malone 23/10/16
 * */
public abstract class Message
{
    protected int mId;
    protected int senderID;
    protected ArrayList<Integer> receiverID;
    protected String message;

    public abstract int getID();

    public abstract int getSenderID();

    public abstract String getMessage();

    /**
     *  @param otherMessage
     * */
    @Override
    public abstract boolean equals(Object otherMessage);

    /**
     * @return
     * */
    @Override
    public int hashCode(){
        return super.hashCode();
    }
}
