package messaging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 03/11/2016.
 */
public class MessageCollection implements Collection<Message>
{
    private ArrayList<Message> messages;

    public MessageCollection() {
        messages = new ArrayList<>();
    }

    @Override
    public void add(Message item) {
        messages.add(item);
    }

    @Override
    public Message get(int id)
    {
        for(Message m : messages)
        {
            if(id == m.getID())
            {
                return m;
            }
        }
        return null;
    }

    @Override
    public List<Message> getAll() {
        return messages;
    }

    @Override
    public void remove(int id)
    {
        for(Message m : messages)
        {
            if(id == m.getID())
            {
                messages.remove(m);
            }
        }
    }
}
