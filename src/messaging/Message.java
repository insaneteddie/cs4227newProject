package messaging;

import java.util.UUID;

/**
 * Created by Christian on 03/11/2016.
 */
public interface Message {
    void sendMessage();

    /**
     * @return UUID
     * */
    UUID getID();

    /**
     * @return int
     * */
    int getSenderID();

    /**
     * @return int
     * */
    int getReceiverID();

    /**
     * @return int
     * */
    int getPartyID();

    /**
     * @return String
     * */
    String getMessage();
}
