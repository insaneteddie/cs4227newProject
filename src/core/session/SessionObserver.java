package core.session;

import core.user.SessionSubject;

public interface SessionObserver
{
    void update(SessionSubject s);
}
