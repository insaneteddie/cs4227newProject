package message;

public class FriendMessage extends Message
{
    private int senderID;
    private int receiverID;

    public FriendMessage(int senderID, int receiverID, String message)
    {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public String getMessage() {
        return "Message received from User " + senderID;
    }

    public int getID()
    {
        return this.mId;
    }

    public boolean equals(Object message) {
        if(message != null) {
            Message m = (Message) message;
            return this.mId == m.getID();
        }
        else
            return false;
    }

    /**
     * @return
     * */
    @Override
    public int hashCode(){
        return super.hashCode();
    }
}
