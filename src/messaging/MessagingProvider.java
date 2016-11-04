package messaging;

/**
 * Created by Christian on 03/11/2016.
 */
public class MessagingProvider
{
    public static AbstractMessagingFactory getFactory(String messageType)
    {
        switch (messageType) {
            case "Message":
                return new MessageFactory();

            case "Invite":
                return new InviteFactory();
            default:
                //will log
                throw new IllegalArgumentException("Invalid Message type given:" + messageType);
        }
    }
}
