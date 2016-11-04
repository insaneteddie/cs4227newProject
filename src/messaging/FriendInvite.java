package messaging;

import database.DatabaseInterface;

/**
 * Created by Christian on 03/11/2016.
 */
class FriendInvite implements Invite{
    private int senderID;
    private int receiverID;

    FriendInvite(int senderId, int receiverID, int partyID) {
        this.senderID = senderId;
        this.receiverID = receiverID;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void sendInvite(DatabaseInterface database) {
        // need to com with DB
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
        return 0;
    }

    @Override
    public String getMessage() {
        return "User " + senderID + " would like to add you to their friends list";
    }
}
