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

    public UUID getID(){
        return messageID;
    }

    public int getSenderID(){
        return senderID;
    }

    public int getReceiverID(){
        return receiverID;
    }

    public int getPartyID(){
        return getPartyID();
    }

    public abstract String getMessage();

    public abstract void deleteInvite();
}
