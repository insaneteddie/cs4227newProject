package message;

/** class that contains data on friend messages */
public class FriendMessage extends Message
{

    /**
     * @param senderID
     * @param receiverID
     * @param message
     * */
    public FriendMessage(int senderID, int receiverID, String message)
    {
        this.senderID = senderID;
        this.receiverID.add(receiverID);
        this.message = message;
    }

    /**
     * @return
     * */
    public int getSenderID()
    {
        return senderID;
    }

    /**
     * @return
     * */
    public int getReceiverID()
    {
        return receiverID.get(0);
    }

    /**
     * @return
     * */
    public String getMessage()
    {
        return message;
    }

    /**
     * @return
     * */
    public int getID()
    {
        return mId;
    }

    /**
     * @param message 
     * @return
     * */
    public boolean equals(Object message)
    {
        if(message != null) {
            Message m = (Message) message;
            return this.mId == m.getID();
        }
        else
            return false;
    }

    /**
     * @return
     * */
    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
