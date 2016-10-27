package message;

/**
 * Created by Christian on 24/10/2016.
 */
public class FriendInvite extends Message
{

    /**
     * @param senderID
     * @param receiverID
     * @param message
     * */
    public FriendInvite(int senderID, int receiverID, String message)
    {
        this.senderID = senderID;
        this.receiverID.add(receiverID);
        this.message = message;
    }

    /**
     * @return
     * */
    @Override
    public int getID()
    {
        return mId;
    }

    /**
     * @param otherMessage
     * @return
     * */
    @Override
    public boolean equals(Object otherMessage)
    {
        return false;
    }

    /**
     * @return
     * */
    @Override
    public int getSenderID()
    {
        return senderID;
    }

    /**
     * @return
     * */
    @Override
    public String getMessage()
    {
        return message;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}

