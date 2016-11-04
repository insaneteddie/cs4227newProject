package messaging;

/**
 * Created by Christian on 03/11/2016.
 */
public interface Message {
    void sendMessage();

    int getID();

    int getSenderID();

    int getReceiverID();
}
