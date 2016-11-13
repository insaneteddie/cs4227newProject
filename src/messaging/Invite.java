package messaging;

import database.DatabaseBridge;

import java.util.UUID;

/**
 * Created by Christian on 03/11/2016.
 */
public abstract class Invite {

    protected int senderID;
    protected int receiverID;
    protected UUID messageID;
    protected int partyID = 0;
    protected DatabaseBridge sqlDB = new DatabaseBridge();

    /**
     * send Invite Method
     */
    public abstract void sendInvite();

    /**
     * getter for message ID
     * @return UUID messageId
     */
    public UUID getID(){
        return messageID;
    }
    /**
     * getter for sender ID
     * @return senderID int
     */
    public int getSenderID(){
        return senderID;
    }
    /**
     * getter for receiver ID
     * @return receiverID int
     */
    public int getReceiverID(){
        return receiverID;
    }

    /**
     * getter for partyID from message
     * @return partyID int value
     */
    public int getPartyID(){
        return partyID;
    }

    public abstract String getMessage();

    public abstract void deleteInvite();
}
