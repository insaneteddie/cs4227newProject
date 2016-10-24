package message;

public class FriendMessage extends Message
{

    public FriendMessage(int senderID, int receiverID, String message)
    {
        this.senderID = senderID;
        this.receiverID.add(receiverID);
        this.message = message;
    }

    public int getSenderID()
    {
        return senderID;
    }

    public int getReceiverID()
    {
        return receiverID.get(0);
    }

    public String getMessage()
    {
        return message;
    }

    public int getID()
    {
        return mId;
    }

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
