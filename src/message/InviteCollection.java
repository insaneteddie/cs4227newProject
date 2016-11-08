package message;

import java.util.ArrayList;
import java.util.List;

public class InviteCollection implements Collection<Invite> {

    private ArrayList<Invite> invites;

    public InviteCollection() {
        invites = new ArrayList<>();
    }

    @Override
    public void add(Invite item) {
        if (invites.isEmpty()) {
            invites.add(item);
        } else {
            boolean newItem = true;
            for (int x =0; x < invites.size(); x++) {
                if (item.equals(x)) {
                    newItem = false;
                    break;
                }
                else if(invites.get(x).getSenderID() == item.getSenderID())
                {
                    invites.remove(x);
                    x--;
                }
            }
            if (newItem) {
                invites.add(item);
            }
        }
    }

    @Override
    public  Invite get(int id)
    {
        for(Invite x : invites)
        {
            if(id == x.getSenderID())
            {
                return x;
            }
        }
        return null;
    }

    @Override
    public List<Invite> getAll()
    {
        return invites;
    }

    @Override
    public void remove(int id, int pid)
    {
        for(int x = 0; x < invites.size(); x++)
        {
            if(id == invites.get(x).getSenderID() && pid == invites.get(x).getPartyID())
            {
                invites.remove(x);
                x--;
            }
        }
    }

    @Override
    public void removeX(int mid) {

    }
}