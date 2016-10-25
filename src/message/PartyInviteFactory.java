package message;

public class PartyInviteFactory extends InviteFactory {

    @Override
    public Message createInvite(int senderID, int receiverID, int partyID)
    {
        return new PartyInvite(senderID, receiverID, partyID);
    }
}
