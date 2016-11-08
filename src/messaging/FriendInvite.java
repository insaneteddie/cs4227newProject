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
class FriendInvite extends Invite{

    /**
     * @param senderId int senderID
     * @param receiverID int receiverID
     * @param partyID int partyID
     */
    FriendInvite(int senderId, int receiverID, int partyID) {
        super.senderID = senderId;
        super.receiverID = receiverID;
        super.messageID = UUID.randomUUID();
    }

    /**
     * sends the invite to the DB
     */
    @Override
    public void sendInvite() {
        try {
            sqlDB.addInvite(senderID, receiverID, partyID);
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "Party Invite Sent"));
        } catch (Exception ex) {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, ex, ""));
        }

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
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "Invite from " + senderID + " to " + receiverID + " has been deleted"));
        } catch (Exception ex) {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, ex, ""));
        }
    }
}
