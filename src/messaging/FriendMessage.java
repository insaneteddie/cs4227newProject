package messaging;

import java.util.UUID;

/**
 * Created by Christian on 03/11/2016.
 */
class FriendMessage implements Message {
    private int senderID;
    private int receiverID;
    private String message;
    private UUID id;

    /**
     * @param senderId int senderID
     * @param receiverID receiverID
     * @param message String message
     */
    FriendMessage(int senderId, int receiverID, String message) {
        this.senderID = senderId;
        this.receiverID = receiverID;
        this.message = message;
    }

    /**
     * communicates the message to the DB
     */
    @Override
    public void sendMessage() {
        // communicates the message to the DB
    }

    /**
     * @return id
     */
    @Override
    public UUID getID() {
        //call to db to get ID
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
     * @return partyID which is 0 for all messages
     */
    @Override
    public int getPartyID() {
        return 0;
    }

    /**
     * @return receiverID
     */
    @Override
    public int getReceiverID()
    {
        return receiverID;
    }

    /**
     * @return the message within the Message object
     */
    @Override
    public String getMessage()
    {
        return message;
    }
}
