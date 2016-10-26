package core.user;

import core.session.SessionObserver;

import java.util.List;

/** interface for subject part of the Observer Design Pattern*/
public interface SessionSubject
{
    /**
     * @param o 
     * */
    void attach(SessionObserver o);
    
    void detach(SessionObserver o);
    
    void update();
    
    List<Integer> getState();
}
