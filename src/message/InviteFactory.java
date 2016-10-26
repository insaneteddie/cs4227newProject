package message;

public abstract class InviteFactory implements MessagingFactory
{
    public abstract Message createInvite(int senderID, int receiverID, int partyID);
}
