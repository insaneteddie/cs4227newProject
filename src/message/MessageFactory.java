package message;

public abstract class MessageFactory
{
    public abstract Message createMessage(int senderID, int receiverID, int partyID);
}
