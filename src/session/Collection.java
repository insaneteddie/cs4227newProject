package session;

import java.util.ArrayList;

public interface Collection<E> {

    void add(E item);

    E get(int id);

    ArrayList<E> getAll();

    void remove(int id, int pid);
}
