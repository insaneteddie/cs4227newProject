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

    public abstract void sendInvite();

    /**
     * @return UUID
     * */
    public UUID getID(){
        return messageID;
    }

    /**
     * @return int
     * */
    public int getSenderID(){
        return senderID;
    }

    /**
     * @return int
     * */
    public int getReceiverID(){
        return receiverID;
    }

    /**
     * @return int
     * */
    public int getPartyID(){
        return partyID;
    }

    /**
     * @return String
     * */
    public abstract String getMessage();

    public abstract void deleteInvite();
}
