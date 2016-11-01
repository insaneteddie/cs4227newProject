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
     * @param username string username to check for login
     * @param password string pass to check for login
     * @return boolean if able to login
     * */
    boolean canLogin(String username, String password);

    /* References the local Player class */
    /**
     * @param username string username to get details of
     * @return returns a csv string of user_ID,email,bio
     * */
    String getPlayerDetails(String username);

    /**
     * @param playerID int user_Id of user requesting friends
     * @return Integer list of friends user_ID's
     * */
    List<Integer> getPlayerFriendList(int playerID);

    /**
     * @param playerID playerID to check for invites
     * @return Integer list of inviteId's
     * */
    List<Integer[]> getPlayerInvites(int playerID);

    /**
     * @param username string username to create user in table
     * @param password string pass to create user in table
     * @param email string email to create user in table
     * */
    void createPlayer(String username, String password, String email);

    /**
     * @param partyLeaderID userID to set as leader on party creation
     * @return int partyID
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
