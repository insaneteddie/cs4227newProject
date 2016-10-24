package message;

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
     * @return
     * */
    E get(int id);

    /**
     * interface for getting list of generic types
     *  @return
     * */
    List<E> getAll();

    /**
     * @param id
     * @param pid
     * */
    void remove(int id, int pid);

    /**
     * @param mid
     * */
    void removeX(int mid);


}
