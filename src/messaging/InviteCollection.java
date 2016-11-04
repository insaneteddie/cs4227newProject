package messaging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 03/11/2016.
 */
public class InviteCollection implements Collection<Invite>
{
    private ArrayList<Invite> invites;

    public InviteCollection() {
        invites = new ArrayList<>();
    }

    @Override
    public void add(Invite item) {
        invites.add(item);
    }

    @Override
    public Invite get(int id)
    {
        for (Invite i : invites)
        {
            if(id == i.getID())
            {
                return i;
            }
        }
        return null;
    }

    @Override
    public List<Invite> getAll() {
        return null;
    }

    @Override
    public void remove(int id)
    {
        for (Invite i : invites)
        {
            if(id == i.getID())
            {
                invites.remove(i);
            }
        }
    }
}
