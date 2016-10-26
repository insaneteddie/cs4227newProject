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

    /**
     * @param o
     * */
    void detach(SessionObserver o);

    /** concrete implementers will override this to notify observers*/
    void update();

    /**
     * @return 
     * */
    List<Integer> getState();
}
