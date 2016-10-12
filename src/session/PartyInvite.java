package session;

public class PartyInvite extends Invite
{
    public PartyInvite(int senderID, int receiverID, int partyID)
    {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.partyID = partyID;
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
    public String getMessage() {
        return "Party Invite received from User " + senderID;
    }

    @Override
    public boolean equals(Object otherInvite) {
        if(otherInvite instanceof Object) {
            PartyInvite p = (PartyInvite) otherInvite;
            return (senderID == p.getSenderID() && partyID == p.partyID);
        }
        else
            return false;
    }

    @Override
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
