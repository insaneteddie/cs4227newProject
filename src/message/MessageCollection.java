package message;

import java.util.ArrayList;
import java.util.List;

public class MessageCollection implements Collection<Message>
{
	private ArrayList<Message> messages;

	public MessageCollection()
	{
		messages = new ArrayList<>();
	}

	@Override
	public void add(Message m)
	{
		if(!messages.contains(m))
		{
			messages.add(m);
		}

	}

	@Override
	public Message get(int id)
	{
		for (Message m : messages)
		{
			// should we have each message have an id? date code?
			// much faster and I'm unsure as to why we are "getting" like this
			if(id == m.getID())
			{
				return m;
			}
		}
		// should we return an error instead of null?
		return null;
	}

	@Override
	public List<Message> getAll()
	{
		return messages;
	}

	@Override
	public void remove(int id, int pid)
	{
		// no implementation yet
		// if we had an id associated with each message this would
		// be much easier
		/*
		for()
		*/
	}

	@Override
	public void removeX(int mid)
	{
		for (Message m : messages)
		{
			if(mid == m.getID())
			{
				messages.remove(m);
				break;
			}
		}
	}
}