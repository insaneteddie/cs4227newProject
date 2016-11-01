package database;


import java.util.List;

/**
 * Created by s_harte CS4227 Awesome Gaming ,awesome_gaming : CS4227 Project on 10/20/2016.
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
     * @param databaseURL string url
     * @param dbUser string user
     * @param dbPass string pass
     * @param jdbcDriver string driver
     * */
    @SuppressWarnings("unused")
    public DatabaseBridge(String databaseURL, String dbUser, String dbPass, String jdbcDriver)
    {
        sqlDB = new SqlDatabase(databaseURL,dbUser,dbPass,jdbcDriver);
    }

    /**
     * @param username string username to check
     * @param password string pass to check
     * @return boolean if details found on db
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
        return sqlDB.getPlayerDetails(username);
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
     * @param partyID int party_id to lookup if full
     * @return boolean true if full false if not
     * */
    @Override
    public boolean isPartyFull(int partyID) {
        return sqlDB.isPartyFull(partyID);
    }

    /**
     * @param playerID int player to add user_Id
     * @param partyID int party_Id to add user_Id to
     * */
    @Override
    public void addPlayerToParty(int playerID, int partyID) {
        if(!isPartyFull(partyID))
            sqlDB.addPlayerToParty(playerID,partyID);
    }

    /**
     * @param partyID int party_Id to remove user from
     * @param playerID int player_ID to remove from party
     * */
    @Override
    public void removePlayerFromParty(int partyID, int playerID) {
        sqlDB.removePlayerFromParty(partyID,playerID);
    }

    /**
     * @param username String username to check
     * @param email string email to check
     * @return int returned 0/1 - true/false respectively
     * */
    @Override
    public int checkUserNameAndEmail(String username, String email) {
        return sqlDB.checkNameEmail(username,email);
    }

    /**
     * @param partyID int party id to check
     * @return boolean if exists or not
     * */
    @Override
    public boolean doesPartyExist(int partyID) {
        return sqlDB.doesPartyExist(partyID);
    }

    /**
     * @param username string username to check exists
     * @return returns int if does
     * */
    @Override
    public int doesPlayerExist(String username) {
        return sqlDB.doesPlayerExist(username);
    }

    /**
     * @param playerID int player id to check
     * @return boolean if in party
     * */
    @Override
    public boolean isPlayerInParty(int playerID) {
        return sqlDB.isInParty(playerID);
    }

    /**
     * @param senderID int sender id
     * @param receiverID int receiver id
     * @param partyId int party id to add to party if that type of invite
     * */
    @Override// String content,int type
    public void addInvite(int senderID, int receiverID, int partyId) {
        sqlDB.addInvite(senderID,receiverID,partyId);
    }

    /**
     * @param senderID int sender's id
     * @param receiverID int receiver id
     * @param inviteId int invite id of invite to be removed
     * */
    @Override
    public void removeInvite(int senderID, int receiverID, int inviteId) {
        sqlDB.removeInvite(senderID,receiverID,inviteId);
    }

    /**
     * @param userId int userId to add a friend
     * @param friendId int friends userId to add to user_friends table
     * */
    @Override
    public void addFriend(int userId, int friendId)
    {
        sqlDB.addFriend(userId,friendId);
    }

    /**
     *
     * @param username String of username to return the user_Id
     * @return int user_ID
     */
    public int getUserId(String username)
    {
        return sqlDB.getUserId(username);
    }

    /**
     * @param userID int user_Id of user's name to return
     * @return String username of user_ID
     */
    public String getUsername(int userID)
    {
        return sqlDB.getPlayerName(userID);
    }
}
