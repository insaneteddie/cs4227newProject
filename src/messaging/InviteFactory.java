package messaging;

/**
 * Created by Christian on 03/11/2016.
 */
class InviteFactory extends AbstractMessagingFactory {

    @Override
    public Invite createInvite(String inviteType, int senderId, int receiverID, int partyID) {
        switch (inviteType){
            case "PartyInvite":
                return new PartyInvite(senderId, receiverID, partyID);
            case "FriendInvite" :
                return new FriendInvite(senderId,receiverID, partyID);
            default:
                // will log
                throw new IllegalArgumentException("Invalid Invite type given:" + inviteType);
        }
    }

    @Override
    public Message createMessage(String messageType, int senderId, int receiverID, String message) {return null;}
}
