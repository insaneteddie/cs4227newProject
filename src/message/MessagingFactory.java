package message;

/**
 * Created by Christian on 25/10/2016.
 */
public interface MessagingFactory
{
    Message createMessage(int senderID, int receiverID, String message);
    Message createInvite(int senderID, int receiverID, int partyID);
}
