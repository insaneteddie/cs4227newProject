package messaging;

/**
 * Created by Christian on 03/11/2016.
 */
public class MessagingProvider
{
    /**
     * @param messageType string messageType
     * @return new instance of a concrete factory
     */
    public static AbstractMessagingFactory getFactory(String messageType)
    {
        switch (messageType) {
            case "MESSAGE":
                return new MessageFactory();

            case "INVITE":
                return new InviteFactory();
            default:
                //will log
                throw new IllegalArgumentException("Invalid Message type given:" + messageType);
        }
    }
}
