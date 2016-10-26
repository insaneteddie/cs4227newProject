package core.session;

import core.user.SessionSubject;

/** Interface for implementing the observer side of the Observer design pattern*/
@FunctionalInterface
public interface SessionObserver
{
    /**
     * @param s
     * */
    void update(SessionSubject s);
}
