package message;

import java.util.ArrayList;

public class PartyInvite extends Message
{
    private int partyID;

    public PartyInvite(int senderID, int receiverID, int partyID)
    {
        this.senderID = senderID;
        this.receiverID.add(receiverID);
        this.partyID = partyID;
    }
    public int getSenderID() {
        return senderID;
    }

    public ArrayList<Integer> getReceiverID() {
        return receiverID;
    }

    public String getMessage() {
        return message;
    }

    public int getID()
    {
        return mId;
    }

    @Override
    public boolean equals(Object otherInvite) {
        if(otherInvite != null) {
            PartyInvite p = (PartyInvite) otherInvite;
            return senderID == p.getSenderID() && partyID == p.partyID;
        }
        else
            return false;
    }

    public int getPartyID() {
        return partyID;
    }

    /**
     * @return
     * */
    @Override
    public int hashCode(){
        return super.hashCode();
    }
}
