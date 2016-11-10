package messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Christian on 03/11/2016.
 */
public class MessageCollection implements Collection<Message>
{
    private ArrayList<Message> messages;

    /**
     *
     */
    public MessageCollection() {
        messages = new ArrayList<>();
    }

    /**
     * @param item Message item
     */
    @Override
    public void add(Message item) {
        messages.add(item);
    }

    /**
     * @param id UUID id
     * @return Message
     */
    @Override
    public Message get(UUID id)
    {
        for(Message m : messages)
        {
            if(id.equals(m.getID()))
            {
                return m;
            }
        }
        return null;
    }

    /**
     * @return List of Messages
     */
    @Override
    public List<Message> getAll() {
        return messages;
    }

    /**
     * @param id UUID id
     */
    @Override
    public void remove(UUID id)
    {
        for(Message m : messages)
        {
            if(id.equals(m.getID()))
            {
                messages.remove(m);
            }
        }
    }

    /**
     * @param senderID int senderID
     * @param partyID int partyID
     */
    @Override
    public void remove(int senderID, int partyID) {
        for (Message m : messages)
        {
            if(senderID == m.getSenderID() && partyID == m.getPartyID())
            {
                messages.remove(m);
            }
        }
    }

    @Override
    public boolean contains(Message item) {
        boolean contains = false;
        for (Message m: messages) {
            if(m.getMessage().equals(item.getMessage()) && (m.getSenderID() == item.getSenderID()))
            {
                contains = true;
            }
        }
        return contains;
    }
}
