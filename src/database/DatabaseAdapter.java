package database;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by s_harte CS4227 Awesome Gaming  on 10/20/2016.
 */
public class DatabaseAdapter implements DatabaseInterface {


    private final SqlDatabase sqlDB;

    /** k im not sure about this....*/
    public DatabaseAdapter()
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
    public DatabaseAdapter(String databaseURL, String dbUser, String dbPass, String jdbcDriver)
    {
        sqlDB = new SqlDatabase(databaseURL,dbUser,dbPass,jdbcDriver);
    }

    /**
     * @param username
     * @param password
     * @return
     * */
    @Override
    public boolean canLogin(String username, String password) throws SQLException {
        return sqlDB.canLogin(username,password);
    }
    //this needs to be edited... seems a bit pointless to pass in the username to get the username
    /**
     * @param username
     * @return
     * */
    @Override
    public String getPlayerDetails(String username) throws SQLException {
        return sqlDB.getPlayerName(sqlDB.getUserId(username));
    }

    /**
     * @param playerID
     * @return
     * */
    @Override
    public List<Integer> getPlayerFriendList(int playerID) throws SQLException {
        return sqlDB.getFriendsList(playerID);
    }

    /**
     * @param playerID
     * @return
     * */
    @Override
    public List<Integer[]> getPlayerInvites(int playerID) throws SQLException {
        return sqlDB.getInvites(playerID);
    }

    /**
     * @param username
     * @param password
     * @param email
     * */
    @Override
    public void createPlayer(String username, String password, String email) throws SQLException {
            sqlDB.addUser(username,password,email);
    }

    //returns party_Id or 0
    /**
     * @param partyLeaderID
     * @return
     * */
    @Override
    public int createParty(int partyLeaderID) throws SQLException {
        return sqlDB.createParty(partyLeaderID);
    }

    /**
     * @param partyID
     * @param playerID
     * @return
     * */
    @Override
    public List<Integer> getPartyDetails(int partyID, int playerID) throws SQLException {
        return sqlDB.getPartyDetails(partyID,playerID);
    }

    /**
     * @param partyID
     * @return
     * */
    @Override
    public boolean isPartyFull(int partyID) throws SQLException {
        return sqlDB.isPartyFull(partyID);
    }

    /**
     * @param playerID
     * @param partyID
     * */
    @Override
    public void addPlayerToParty(int playerID, int partyID) throws SQLException {
        if(!isPartyFull(partyID))
            sqlDB.addPlayerToParty(playerID,partyID);
    }

    /**
     * @param partyID
     * @param playerID
     * */
    @Override
    public void removePlayerFromParty(int partyID, int playerID) throws SQLException {
        sqlDB.removePlayerFromParty(partyID,playerID);
    }

    /**
     * @param username
     * @param email
     * @return
     * */
    @Override
    public int checkUserNameAndEmail(String username, String email) throws SQLException {
        return sqlDB.checkNameEmail(username,email);
    }

    /**
     * @param partyID
     * @return
     * */
    @Override
    public boolean doesPartyExist(int partyID) throws SQLException {
        return sqlDB.doesPartyExist(partyID);
    }

    /**
     * @param username
     * @return
     * */
    @Override
    public int doesPlayerExist(String username) throws SQLException {
        return sqlDB.doesPlayerExist(username);
    }

    /**
     * @param playerID
     * @return
     * */
    @Override
    public boolean isPlayerInParty(int playerID) throws SQLException {
        return sqlDB.isInParty(playerID);
    }

    /**
     * @param senderID
     * @param receiverID
     * @param partyId
     * */
    @Override// String content,int type
    public void addInvite(int senderID, int receiverID, int partyId) throws SQLException {
        sqlDB.addInvite(senderID,receiverID,partyId);
    }

    /**
     * @param senderID
     * @param receiverID
     * @param inviteId
     * */
    @Override
    public void removeInvite(int senderID, int receiverID, int inviteId) throws SQLException {
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
