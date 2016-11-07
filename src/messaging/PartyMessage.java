package messaging;

import java.util.UUID;

/**
 * Created by Christian on 03/11/2016.
 */
class PartyMessage implements Message {
    private int senderID;
    private int receiverID;
    private String message;
    private UUID id;

    /**
     * @param senderId int senderID
     * @param receiverID int receiverID
     * @param message String Message
     */
    PartyMessage(int senderId, int receiverID, String message) {
        this.senderID = senderId;
        this.receiverID = receiverID;
        this.message = message;
    }

    /**
     * sends the message to the db
     */
    @Override
    public void sendMessage() {
        // communicates the message to the DB
    }

    /**
     * @return the ID of the message
     */
    @Override
    public UUID getID() {
        return id;
    }

    /**
     * @return senderID
     */
    @Override
    public int getSenderID() {
        return senderID;
    }

    /**
     * @return the partyID which is 0 for all messages
     */
    @Override
    public int getPartyID() {
        return 0;
    }

    /**
     * @return receiverID
     */
    @Override
    public int getReceiverID() {
        return receiverID;
    }

    /**
     * @return the message in the Message object
     */
    @Override
    public String getMessage()
    {
        return message;
    }
}
