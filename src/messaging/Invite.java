package messaging;

import java.util.UUID;

/**
 * Created by Christian on 03/11/2016.
 */
public interface Invite {

    void sendInvite();

    UUID getID();

    int getSenderID();

    int getReceiverID();

    int getPartyID();

    String getMessage();

    void deleteInvite();
}
