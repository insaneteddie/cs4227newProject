package core.session;

import core.user.SessionSubject;

/** Interface for implementing the observer side of the Observer design pattern*/
public interface SessionObserver
{
    void update(SessionSubject s);
}
