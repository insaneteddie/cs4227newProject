/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/DatabaseInterface.java
*   Approximate use: 30%
**/
package database;

import java.sql.SQLException;
import java.util.List;
/** classs that defines the interface for the database*/
public interface DatabaseInterface {

    /**
     * @param username
     * @param password
     * @return
     * */
    boolean canLogin(String username, String password) throws SQLException;

    /* References the local Player class */
    /**
     * @param username
     * @return
     * */
    String getPlayerDetails(String username) throws SQLException;

    /**
     * @param playerID
     * @return
     * */
    List<Integer> getPlayerFriendList(int playerID) throws SQLException;

    /**
     * @param playerID
     * @return
     * */
    List<Integer[]> getPlayerInvites(int playerID) throws SQLException;

    /**
     * @param username
     * @param password
     * @param email
     * */
    void createPlayer(String username, String password, String email) throws SQLException;

    /**
     * @param partyLeaderID
     * @return
     * */
    int createParty(int partyLeaderID) throws SQLException;

    /**
     * @param partyID
     * @param playerID
     * @return
     * */
    List<Integer> getPartyDetails(int partyID, int playerID) throws SQLException;

    /**
     * @param partyID
     * @return
     * */
    boolean isPartyFull(int partyID) throws SQLException;

    /**
     * @param playerID
     * @param partyID
     * */
    void addPlayerToParty(int playerID, int partyID) throws SQLException;

    /**
     * @param partyID
     * @param playerID
     * */
    void removePlayerFromParty(int partyID, int playerID) throws SQLException;

    /**
     * @param username
     * @param email
     * @return
     * */
    int checkUserNameAndEmail(String username, String email) throws SQLException;

    /**
     * @param partyID
     * @return
     * */
    boolean doesPartyExist(int partyID) throws SQLException;

    /**
     * @param username
     * @return
     * */
    int doesPlayerExist(String username) throws SQLException;

    /**
     * @param playerID
     * @return
     * */
    boolean isPlayerInParty(int playerID) throws SQLException;

    //String content, int type
    /**
     * @param senderID
     * @param receiverID
     * @param partyID
     * */
    void addInvite(int senderID, int receiverID, int partyID) throws SQLException;

    /**
     * @param senderID
     * @param receiverID
     * @param partyID
     * */
    void removeInvite(int senderID, int receiverID, int partyID) throws SQLException;

    /**
     * @param userId
     * @param friendId
     * */
    void addFriend(int userId, int friendId);
}
