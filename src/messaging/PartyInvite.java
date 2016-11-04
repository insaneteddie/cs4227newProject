package messaging;

import core.utils.Log;
import database.DatabaseInterface;

/**
 * Created by Christian on 03/11/2016.
 */
public class PartyInvite implements Invite {
    private int senderID;
    private int receiverID;
    private int partyID;
    private final Log log = new Log(getClass().getName());
    private DatabaseInterface database;

    public PartyInvite(int senderId, int receiverID, int partyID) {
        this.senderID = senderId;
        this.receiverID = receiverID;
        this.partyID = partyID;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void sendInvite(DatabaseInterface databaseInstance) {
        database = databaseInstance;
        try {
            database.addInvite(senderID, receiverID, partyID);
            log.logInfo("Party Invite Sent");
        } catch (Exception ex) {
            log.logWarning(ex);
        }
    }

    @Override
    public int getSenderID() {
        return senderID;
    }

    @Override
    public int getReceiverID() {
        return receiverID;
    }

    @Override
    public int getPartyID() {
        return partyID;
    }

    @Override
    public String getMessage() {
        return "User " + senderID + " has invited you to a party";
    }
}
