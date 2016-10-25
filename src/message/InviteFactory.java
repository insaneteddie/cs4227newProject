package message;

public abstract class InviteFactory
{
    public abstract Message createInvite(int senderID, int receiverID, int partyID);
}
