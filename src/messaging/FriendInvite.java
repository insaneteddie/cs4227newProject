package messaging;

import core.interceptor.ConcreteSimpleLoggingRequest;
import core.interceptor.LogDispatcher;
import core.interceptor.LoggingRequest;
import database.DatabaseBridge;
import core.interceptor.Log;

import java.util.UUID;

/**
 * Created by Christian on 03/11/2016.
 */
class FriendInvite implements Invite{
    private int senderID;
    private int receiverID;
    private UUID messageID;
    private int partyID = 0;
    private DatabaseBridge sqlDB = new DatabaseBridge();
    private final Log log = new Log(getClass().getName());

    /**
     * @param senderId int senderID
     * @param receiverID int receiverID
     * @param partyID int partyID
     */
    FriendInvite(int senderId, int receiverID, int partyID) {
        this.senderID = senderId;
        this.receiverID = receiverID;
        messageID = UUID.randomUUID();
    }

    /**
     * @return messageID
     */
    @Override
    public UUID getID() {
        return messageID;
    }

    /**
     * sends the invite to the DB
     */
    @Override
    public void sendInvite() {
        try {
            sqlDB.addInvite(senderID, receiverID, partyID);
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
        return 0;
    }

    /**
     * @return the message for the invite
     */
    @Override
    public String getMessage() {
        return "User " + senderID + " would like to add you to their friends list";
    }

    /**
     * deletes an invite from the DB
     */
    @Override
    public void deleteInvite()
    {
        try {
            sqlDB.removeInvite(senderID, receiverID, partyID);
            //log.logInfo("Invite from " + senderID + " to " + receiverID + " has been deleted");
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "Invite from " + senderID + " to " + receiverID + " has been deleted"));
        } catch (Exception ex) {
            //log.logWarning(ex);
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, ex, ""));
        }
    }
}
