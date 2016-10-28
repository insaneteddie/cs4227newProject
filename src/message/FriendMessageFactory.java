package message;

/**
 * Created by Christian on 25/10/2016.
 */
public class FriendMessageFactory extends MessageFactory
{
    /**
     * @param senderID
     * @param receiverID
     * @param message
     * @return
     * */
    public Message createMessage(int senderID, int receiverID, String message)
    {
        return new FriendMessage(senderID, receiverID, message);
    }

    /**
     * @param senderID
     * @param receiverID
     * @param partyID
     * @return
     * */
    public Message createInvite(int senderID, int receiverID, int partyID) {
        return null;
    }

}
