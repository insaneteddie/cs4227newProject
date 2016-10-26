package message;

public class PartyInviteFactory extends InviteFactory {

    public Message createMessage(int senderID, int receiverID, String message) {
        return null;
    }

    public Message createInvite(int senderID, int receiverID, int partyID)
    {
        return new PartyInvite(senderID, receiverID, partyID);
    }
}
