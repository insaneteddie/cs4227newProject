package messaging;

/**
 * Created by Christian on 03/11/2016.
 */
class InviteFactory extends AbstractMessagingFactory {

    /**
     * @param inviteType String inviteType
     * @param senderId int senderID
     * @param receiverID int receiverID
     * @param partyID int partyID
     * @return new Invite
     */
    @Override
    public Invite createInvite(String inviteType, int senderId, int receiverID, int partyID) {
        switch (inviteType){
            case "PARTY_INVITE":
                return new PartyInvite(senderId, receiverID, partyID);
            case "FRIEND_INVITE" :
                return new FriendInvite(senderId,receiverID, partyID);
            default:
                // will log
                throw new IllegalArgumentException("Invalid Invite type given: " + inviteType);
        }
    }

    /**
     * @param messageType String messageType
     * @param senderId int senderID
     * @param receiverID int receiverID
     * @param message String Message
     * @return new Message
     */
    @Override
    public Message createMessage(String messageType, int senderId, int receiverID, String message) {return null;}
}
