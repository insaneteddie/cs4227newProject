package messaging;

/**
 * Created by Christian on 03/11/2016.
 */
class PartyMessage implements Message {
    private int senderID;
    private int receiverID;
    private String message;

    public PartyMessage(int senderId, int receiverID, String message) {
        this.senderID = senderId;
        this.receiverID = receiverID;
        this.message = message;
    }

    @Override
    public void sendMessage() {
        // communicates the message to the DB
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public int getSenderID() {
        return senderID;
    }

    @Override
    public int getReceiverID() {
        return receiverID;
    }
}
