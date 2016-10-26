package core.session;

import core.user.SessionSubject;

/** Interface for implementing the observer side of the Observer design pattern*/
@FunctionalInterface
public interface SessionObserver
{
    /** Abstract method for updating observers of the passed in subject */
    void update(SessionSubject s);
}
