package messaging;

/**
 * Created by Christian on 03/11/2016.
 */
class MessageFactory extends AbstractMessagingFactory {

    @Override
    public Invite createInvite(String messageType, int senderId, int receiverID, int partyID) {return null;}

    @Override
    public Message createMessage(String messageType, int senderId, int receiverID, String message) {
        switch (messageType) {
            case "FriendMessage":
                return new FriendMessage(senderId, receiverID, message);
            case "PartyMessage":
                return new PartyMessage(senderId, receiverID, message);
            default:
                // will log
                throw new IllegalArgumentException("Invalid Message type given:" + messageType);
        }
    }
}
