package session;

import java.util.List;

/**
 *  Template interface for a generic container
 *  @param <E>
 * */
public interface Collection<E> {

    /**
     * @param item
     * */
    void add(E item);

    /**
     * @param id
     * */
    E get(int id);

    /**
     * interface for getting list of generic types
     * */
    List<E> getAll();

    /**
     * @param id
     * @param pid
     * */
    void remove(int id, int pid);
}
