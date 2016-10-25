package message;

/**
 * Created by Christian on 25/10/2016.
 */
public class FriendMessageFactory extends MessageFactory
{
    public Message createMessage(int senderID, int receiverID, String message)
    {
        return new FriendMessage(senderID, receiverID, message);
    }


    public Message createInvite(int senderID, int receiverID, int partyID) {
        return null;
    }

}
