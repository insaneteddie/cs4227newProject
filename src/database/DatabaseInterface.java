/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/DatabaseInterface.java
*   Approximate use: 30%
**/
package database;

import java.util.List;
/** classs that defines the interface for the database*/
public interface DatabaseInterface {

    /**
     * @param username
     * @param password
     * @return
     * */
    boolean canLogin(String username, String password);

    /* References the local Player class */
    /**
     * @param username
     * @return
     * */
    String getPlayerDetails(String username);

    /**
     * @param playerID
     * @return
     * */
    List<Integer> getPlayerFriendList(int playerID);

    /**
     * @param playerID
     * @return
     * */
    List<Integer[]> getPlayerInvites(int playerID);

    /**
     * @param username
     * @param password
     * @param email
     * */
    void createPlayer(String username, String password, String email);

    /**
     * @param partyLeaderID
     * @return
     * */
    int createParty(int partyLeaderID);

    /**
     * @param partyID
     * @param playerID
     * @return
     * */
    List<Integer> getPartyDetails(int partyID, int playerID);

    /**
     * @param partyID
     * @return
     * */
    boolean isPartyFull(int partyID);

    /**
     * @param playerID
     * @param partyID
     * */
    void addPlayerToParty(int playerID, int partyID);

    /**
     * @param partyID
     * @param playerID
     * */
    void removePlayerFromParty(int partyID, int playerID);

    /**
     * @param username
     * @param email
     * @return
     * */
    int checkUserNameAndEmail(String username, String email);

    /**
     * @param partyID
     * @return
     * */
    boolean doesPartyExist(int partyID);

    /**
     * @param username
     * @return
     * */
    int doesPlayerExist(String username);

    /**
     * @param playerID
     * @return
     * */
    boolean isPlayerInParty(int playerID);

    //String content, int type
    /**
     * @param senderID
     * @param receiverID
     * @param partyID
     * */
    void addInvite(int senderID, int receiverID, int partyID);

    /**
     * @param senderID
     * @param receiverID
     * @param partyID
     * */
    void removeInvite(int senderID, int receiverID, int partyID);

    /**
     * @param userId
     * @param friendId
     * */
    void addFriend(int userId, int friendId);
}
