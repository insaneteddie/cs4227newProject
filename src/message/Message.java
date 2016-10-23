package message;

/**
 *  created by c_malone 23/10/16
 * */
public abstract class Message
{
    protected int senderID;
    protected int receiverID;
    protected int partyID;
    protected String message;
    
    public abstract int getSenderID();
    
    public abstract int getReceiverID();
    
    public abstract int getPartyID();
    
    public abstract String getMessage();

    /**
     *  @param otherInvite
     * */
    @Override
    public abstract boolean equals(Object otherInvite);

    /**
     * @return
     * */
    @Override
    public int hashCode(){
        return super.hashCode();
    }
}
