package messaging;

import java.util.List;
import java.util.UUID;

/**
 * Created by Christian on 03/11/2016.
 */
public interface Collection<E> {

    void add(E item);

    E get(UUID id);

    List<E> getAll();

    void remove(UUID id);

    void remove(int senderID, int partyID);
}
