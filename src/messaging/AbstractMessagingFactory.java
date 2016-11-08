package messaging;

/**
 * Created by Christian on 03/11/2016.
 */
public abstract class AbstractMessagingFactory {
    /**
     * @param inviteType String
     * @param senderId int
     * @param receiverID int
     * @param partyId int
     * @return Invite
     * */
    public abstract Invite createInvite(String inviteType, int senderId, int receiverID, int partyId);
    /**
     * @param messageType String
     * @param senderId int
     * @param receiverID int
     * @param message String
     * @return Message
     * */
    public abstract Message createMessage(String messageType, int senderId, int receiverID, String message);
}
