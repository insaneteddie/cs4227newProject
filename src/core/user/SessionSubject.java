package core.user;

import core.session.SessionObserver;

import java.util.List;

public interface SessionSubject
{
    void attach(SessionObserver o);
    
    void detach(SessionObserver o);
    
    void update();
    
    List<Integer> getState();
}
