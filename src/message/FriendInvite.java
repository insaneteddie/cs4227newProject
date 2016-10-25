package message;

/**
 * Created by Christian on 24/10/2016.
 */
public class FriendInvite extends Message
{

    public FriendInvite(int senderID, int receiverID, String message)
    {
        this.senderID = senderID;
        this.receiverID.add(receiverID);
        this.message = message;
    }

    @Override
    public int getID()
    {
        return mId;
    }

    @Override
    public boolean equals(Object otherMessage)
    {
        return false;
    }

    @Override
    public int getSenderID()
    {
        return senderID;
    }

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

