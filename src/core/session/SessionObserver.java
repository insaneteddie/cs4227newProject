package core.session;

import core.user.SessionSubject;

public interface SessionObserver
{
    public abstract void update(SessionSubject s);
}
