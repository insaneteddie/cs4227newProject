package message;

public abstract class MessageFactory implements MessagingFactory
{
    public abstract Message createMessage(int senderID, int receiverID, String message);
}
