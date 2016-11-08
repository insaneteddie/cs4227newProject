package messaging;

import core.interceptor.ConcreteSimpleLoggingRequest;
import core.interceptor.LogDispatcher;
import core.interceptor.LoggingRequest;
import core.interceptor.Log;
import database.DatabaseBridge;

import java.util.UUID;

/**
 * Created by Christian on 03/11/2016.
 */
class PartyInvite implements Invite {
    private int senderID;
    private int receiverID;
    private int partyID;
    private UUID messageID;
    private final Log log = new Log(getClass().getName());
    private DatabaseBridge sqlDb = new DatabaseBridge();

    /**
     * @param senderId int senderID
     * @param receiverID int receiverID
     * @param partyID int partyID
     */
    PartyInvite(int senderId, int receiverID, int partyID) {
        this.senderID = senderId;
        this.receiverID = receiverID;
        this.partyID = partyID;
        messageID =  UUID.randomUUID();
    }

    /**
     * @return messageID
     */
    @Override
    public UUID getID() {
        return messageID;
    }

    /**
     * sendsInvite to db
     */
    @Override
    public void sendInvite() {
        try {
            sqlDb.addInvite(senderID, receiverID, partyID);
            //log.logInfo("Party Invite Sent");
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "Party Invite Sent"));
        } catch (Exception ex) {
            //log.logWarning(ex);
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, ex, ""));
        }

    }

    /**
     * @return senderID
     */
    @Override
    public int getSenderID() {
        return senderID;
    }

    /**
     * @return receiverID
     */
    @Override
    public int getReceiverID() {
        return receiverID;
    }

    /**
     * @return partyID
     */
    @Override
    public int getPartyID() {
        return partyID;
    }

    /**
     * @return Message for invite
     */
    @Override
    public String getMessage() {
        return "User " + senderID + " has invited you to a party";
    }

    /**
     *  deletes the invite from the DB
     */
    public void deleteInvite()
    {
        try {
            sqlDb.removeInvite(senderID, receiverID, partyID);
            //log.logInfo("Invite from " + senderID + " to " + receiverID + " for party " + partyID + " has been deleted");
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "Invite from " + senderID + " to " + receiverID + " for party " + partyID + " has been deleted"));
        } catch (Exception ex) {
            //log.logWarning(ex);
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, ex, ""));
        }
    }
}
