package messaging;

import java.util.List;

/**
 * Created by Christian on 03/11/2016.
 */
public interface Collection<E> {

    void add(E item);

    E get(int id);

    List<E> getAll();

    void remove(int id);


}
