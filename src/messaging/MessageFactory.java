package messaging;

/**
 * Created by Christian on 03/11/2016.
 */
class MessageFactory extends AbstractMessagingFactory {

    /**
     * @param messageType String messageType
     * @param senderId int senderID
     * @param receiverID int receiverID
     * @param partyID int partyID
     * @return new Invite
     */
    @Override
    public Invite createInvite(String messageType, int senderId, int receiverID, int partyID) {return null;}

    /**
     * @param messageType String messageType
     * @param senderId int senderID
     * @param receiverID int receiverID
     * @param message String message
     * @return new Message
     */
    @Override
    public Message createMessage(String messageType, int senderId, int receiverID, String message) {
        switch (messageType) {
            case "FRIEND_MESSAGE":
                return new FriendMessage(senderId, receiverID, message);
            case "PARTY_MESSAGE":
                return new PartyMessage(senderId, receiverID, message);
            default:
                // will log
                throw new IllegalArgumentException("Invalid Message type given:" + messageType);
        }
    }
}
