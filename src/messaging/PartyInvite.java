package messaging;

import core.interceptor.ConcreteSimpleLoggingRequest;
import core.interceptor.LogDispatcher;
import core.interceptor.LoggingRequest;

import java.util.UUID;

/**
 * Created by Christian on 03/11/2016.
 */
class PartyInvite extends Invite {

    /**
     * @param senderId int senderID
     * @param receiverID int receiverID
     * @param partyID int partyID
     */
    PartyInvite(int senderId, int receiverID, int partyID) {
        super.senderID = senderId;
        super.receiverID = receiverID;
        super.partyID = partyID;
        messageID =  UUID.randomUUID();
    }

    /**
     * sendsInvite to db
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
     * @return Message for invite
     */
    @Override
    public String getMessage() {
        String friend;
        try{
            friend = sqlDB.getUsername(senderID);
            return "User " + friend + " has invited you to a party";
        } catch (Exception ex) {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, ex, ""));
        }

        return "User " + senderID + " has invited you to a party";
    }

    /**
     *  deletes the invite from the DB
     */
    @Override
    public void deleteInvite()
    {
        try {
            sqlDB.removeInvite(senderID, receiverID, partyID);
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "Invite from " + senderID + " to " + receiverID + " for party " + partyID + " has been deleted"));
        } catch (Exception ex) {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, ex, ""));
        }
    }
}
