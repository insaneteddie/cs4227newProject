package core.interceptor;

import java.util.List;

/**
 * Created by Cian Bolster on 29/10/2016.
 *
 * Base implementation for the Interceptor class that will as of now log errors.
 */
public class Interceptor implements IInterceptor{

    private List<String> events;
    private List<String> messages;

    @Override
    public void takeAction() {

    }

    public String getMessages(int index){
        return messages.get(index);
    }

    public void setMessages(String newMessage){
        messages.add(newMessage);
    }

    public String getEvents(int index){
        return events.get(index);
    }

    public void setEvents(String newEvent){
        events.add(newEvent);
    }

}
