package core.user;

import core.session.SessionObserver;

import java.util.ArrayList;

public interface SessionSubject
{
    void attach(SessionObserver o);
    
    void detach(SessionObserver o);
    
    void update();
    
    ArrayList<Integer> getState();
}
