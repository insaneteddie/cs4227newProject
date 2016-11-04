package messaging;

/**
 * Created by Christian on 03/11/2016.
 */
class FriendMessage implements Message {
    private int senderID;
    private int receiverID;
    private String message;

    public FriendMessage(int senderId, int receiverID, String message) {
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
        //call to db to get ID
        return 0;
    }

    @Override
    public int getSenderID() {
        return senderID;
    }

    @Override
    public int getReceiverID()
    {
        return receiverID;
    }
}
