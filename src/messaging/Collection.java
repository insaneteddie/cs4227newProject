package messaging;

import java.util.List;
import java.util.UUID;

/**
 * Created by Christian on 03/11/2016.
 * @param <E>
 */
public interface Collection<E> {

    /**
     * @param item E
     * */
    void add(E item);

    /**
     * @param id UUID
     * @return E
     * */
    E get(UUID id);

    /**
     * @return List
     * */
    List<E> getAll();

    /**
     * @param id UUID
     * */
    void remove(UUID id);

    /**
     * @param senderID int
     * @param partyID int
     * */
    void remove(int senderID, int partyID);

    /**
     * @param item E
     * @return boolean
     */
    boolean contains(E item);
}
