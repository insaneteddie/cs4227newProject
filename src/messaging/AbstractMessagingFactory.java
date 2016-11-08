package messaging;

/**
 * Created by Christian on 03/11/2016.
 */
public abstract class AbstractMessagingFactory {
    public abstract Invite createInvite(String inviteType, int senderId, int receiverID, int partyId);
    public abstract Message createMessage(String messageType, int senderId, int receiverID, String message);
}
