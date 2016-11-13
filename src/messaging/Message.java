package messaging;

import java.util.UUID;

/**
 * Created by Christian on 03/11/2016.
 */
public interface Message {
    /**
     * sendMessage Method
     */
    void sendMessage();
    /**
     * getID Method
     * @return  UUID of message
     */
    UUID getID();
    /**
     * getSenderID Method
     */
    int getSenderID();
    /**
     * getReceiverID Method
     */
    int getReceiverID();
    /**
     * getPartyID Method
     */
    int getPartyID();
    /**
     * getMessage Method
     */
    String getMessage();
}
