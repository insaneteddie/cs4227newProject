package messaging;

import database.DatabaseInterface;

/**
 * Created by Christian on 03/11/2016.
 */
public interface Invite {

    void sendInvite(DatabaseInterface database);

    int getID();

    int getSenderID();

    int getReceiverID();

    int getPartyID();

    String getMessage();
}
