package messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Christian on 03/11/2016.
 */
public class InviteCollection implements Collection<Invite>
{
    private ArrayList<Invite> invites;

    /**
     */
    public InviteCollection() {
        invites = new ArrayList<>();
    }

    /**
     * @param item Invite invite
     */
    @Override
    public void add(Invite item) {
        invites.add(item);
    }

    /**
     * @param id UUID id
     * @return invite
     */
    @Override
    public Invite get(UUID id)
    {
        for (Invite i : invites)
        {
            if(id.equals(i.getID()))
            {
                return i;
            }
        }
        return null;
    }

    /**
     * @return list of invites
     */
    @Override
    public List<Invite> getAll() {
        return invites;
    }

    /**
     * @param id UUID id
     */
    @Override
    public void remove(UUID id)
    {
        for (Invite i : invites)
        {
            if(id.equals(i.getID()))
            {
                invites.remove(i);
                i.deleteInvite();
            }
        }
    }

    /**
     * @param senderID int senderID
     * @param partyID int partyID
     */
    @Override
    public void remove(int senderID, int partyID)
    {
        for (Invite i : invites)
        {
            if(senderID == i.getSenderID() && partyID == i.getPartyID())
            {
                invites.remove(i);
                //delete message from DB
                i.deleteInvite();
            }
        }
    }
}
