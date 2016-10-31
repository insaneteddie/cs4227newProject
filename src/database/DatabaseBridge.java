package database;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by s_harte CS4227 Awesome Gaming  on 10/20/2016.
 */
public class DatabaseBridge implements DatabaseInterface {


    private final SqlDatabase sqlDB;

    /** k im not sure about this....*/
    public DatabaseBridge()
    {
        sqlDB = new SqlDatabase();

    }
    //overloaded to allow connection to own database
    /**
     * @param databaseURL
     * @param dbUser
     * @param dbPass
     * @param jdbcDriver
     * */
    @SuppressWarnings("unused")
    public DatabaseBridge(String databaseURL, String dbUser, String dbPass, String jdbcDriver)
    {
        sqlDB = new SqlDatabase(databaseURL,dbUser,dbPass,jdbcDriver);
    }

    /**
     * @param username
     * @param password
     * @return
     * */
    @Override
    public boolean canLogin(String username, String password) {
        return sqlDB.canLogin(username,password);
    }
    //this needs to be edited... seems a bit pointless to pass in the username to get the username
    /**
     * @param username Looks up user table with user_Name
     * @return String with user_Id,user_Email,user_Bio
     * */
    @Override
    public String getPlayerDetails(String username) {
        return sqlDB.get_Player_Details(username);
    }

    /**
     * @param playerID int identifier to use for lookup
     * @return Integer List containing user_Ids for friends
     * */
    @Override
    public List<Integer> getPlayerFriendList(int playerID) {
        return sqlDB.getFriendsList(playerID);
    }

    /**
     * @param playerID uses playerId to look up invites table
     * @return Integer List of invite ID's
     * */
    @Override
    public List<Integer[]> getPlayerInvites(int playerID){
        return sqlDB.getInvites(playerID);
    }

    /**Creates a new user in the userTable
     * @param username String Name to add to table
     * @param password String pass to add to table
     * @param email String email to add to table
     * */
    @Override
    public void createPlayer(String username, String password, String email){
            sqlDB.addUser(username,password,email);
    }


    /**
     * @param partyLeaderID takes party creator Id as Leader
     * @return Int of partyId
     * */
    @Override
    public int createParty(int partyLeaderID) {
        return sqlDB.createParty(partyLeaderID);
    }

    /**
     * @param partyID Used to lookup parties Table
     * @param playerID used to lookup parties table
     * @return Integer List of party member Id's
     * */
    @Override
    public List<Integer> getPartyDetails(int partyID, int playerID) {
        return sqlDB.getPartyDetails(partyID,playerID);
    }

    /**
     * @param partyID
     * @return boolean true if full false if not
     * */
    @Override
    public boolean isPartyFull(int partyID) {
        return sqlDB.isPartyFull(partyID);
    }

    /**
     * @param playerID
     * @param partyID
     * */
    @Override
    public void addPlayerToParty(int playerID, int partyID) {
        if(!isPartyFull(partyID))
            sqlDB.addPlayerToParty(playerID,partyID);
    }

    /**
     * @param partyID
     * @param playerID
     * */
    @Override
    public void removePlayerFromParty(int partyID, int playerID) {
        sqlDB.removePlayerFromParty(partyID,playerID);
    }

    /**
     * @param username
     * @param email
     * @return
     * */
    @Override
    public int checkUserNameAndEmail(String username, String email) {
        return sqlDB.checkNameEmail(username,email);
    }

    /**
     * @param partyID
     * @return
     * */
    @Override
    public boolean doesPartyExist(int partyID) {
        return sqlDB.doesPartyExist(partyID);
    }

    /**
     * @param username
     * @return
     * */
    @Override
    public int doesPlayerExist(String username) {
        return sqlDB.doesPlayerExist(username);
    }

    /**
     * @param playerID
     * @return
     * */
    @Override
    public boolean isPlayerInParty(int playerID) {
        return sqlDB.isInParty(playerID);
    }

    /**
     * @param senderID
     * @param receiverID
     * @param partyId
     * */
    @Override// String content,int type
    public void addInvite(int senderID, int receiverID, int partyId) {
        sqlDB.addInvite(senderID,receiverID,partyId);
    }

    /**
     * @param senderID
     * @param receiverID
     * @param inviteId
     * */
    @Override
    public void removeInvite(int senderID, int receiverID, int inviteId) {
        sqlDB.removeInvite(senderID,receiverID,inviteId);
    }

    /**
     * @param userId
     * @param friendId
     * */
    @Override
    public void addFriend(int userId, int friendId)
    {
        sqlDB.addFriend(userId,friendId);
    }


}
