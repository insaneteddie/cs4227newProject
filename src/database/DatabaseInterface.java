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
     * @param partyID int partyID to get details for
     * @param playerID int player_Id to check narrow search criteria
     * @return Integer list of friends user_Id's
     * */
    List<Integer> getPartyDetails(int partyID, int playerID);

    /**
     * @param partyID int party_ID to check is full
     * @return boolean return if full true/false
     * */
    boolean isPartyFull(int partyID);

    /**
     * @param playerID int player id to add to party
     * @param partyID int party_ID to add user to
     * */
    void addPlayerToParty(int playerID, int partyID);

    /**
     * @param partyID int party_Id to remove user from
     * @param playerID int player_Id to remove from party
     * */
    void removePlayerFromParty(int partyID, int playerID);

    /**
     * @param username string username to check
     * @param email string email to check
     * @return return int 0/1
     * */
    int checkUserNameAndEmail(String username, String email);

    /**
     * @param partyID party_Id to check if it exists
     * @return return boolean if it does
     * */
    boolean doesPartyExist(int partyID);

    /**
     * @param username string username to check
     * @return return in 0/1 if it exists
     * */
    int doesPlayerExist(String username);

    /**
     * @param playerID int player_Id to check if in party
     * @return boolean if in party/ or not
     * */
    boolean isPlayerInParty(int playerID);

    //String content, int type
    /**
     * @param senderID int sender_Id too add to user_invites
     * @param receiverID int receiver_Id to add to user_invites
     * @param partyID int party_Id to add if party invite
     * */
    void addInvite(int senderID, int receiverID, int partyID);

    /**
     * @param senderID int sender_Id to remove invite from
     * @param receiverID int receiver_Id to remove invite
     * @param partyID int party_Id to remove if party invite
     * */
    void removeInvite(int senderID, int receiverID, int partyID);

    /**
     * @param userId int userID to add friend for
     * @param friendId int friend_Id to add as friend
     * */
    void addFriend(int userId, int friendId);
}
