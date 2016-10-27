package database;

import java.util.ArrayList;

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
     * @param JDBCDriver
     * */
    @SuppressWarnings("unused")
    public DatabaseAdapter(String databaseURL, String dbUser, String dbPass, String JDBCDriver)
    {
        sqlDB = new SqlDatabase(databaseURL,dbUser,dbPass,JDBCDriver);
    }

    /**
     * @param username
     * @param password
     * @return
     * */
    @Override
    public boolean canLogin(String username, String password) throws Exception {
        return sqlDB.can_Login(username,password);
    }
    //this needs to be edited... seems a bit pointless to pass in the username to get the username
    /**
     * @param username
     * @return
     * */
    @Override
    public String getPlayerDetails(String username) throws Exception {
        return sqlDB.get_PlayerName(sqlDB.get_UserId(username));
    }

    /**
     * @param playerID
     * @return
     * */
    @Override
    public ArrayList<Integer> getPlayerFriendList(int playerID) throws Exception {
        return sqlDB.get_FriendsList(playerID);
    }

    /**
     * @param playerID
     * @return
     * */
    @Override
    public ArrayList<Integer[]> getPlayerInvites(int playerID) throws Exception {
        return sqlDB.get_Invites(playerID);
    }

    /**
     * @param username
     * @param password
     * @param email
     * */
    @Override
    public void createPlayer(String username, String password, String email) throws Exception {
            sqlDB.add_User(username,password,email);
    }

    //returns party_Id or 0
    /**
     * @param partyLeaderID
     * @return
     * */
    @Override
    public int createParty(int partyLeaderID) throws Exception {
        return sqlDB.create_Party(partyLeaderID);
    }

    /**
     * @param partyID
     * @param playerID
     * @return
     * */
    @Override
    public ArrayList<Integer> getPartyDetails(int partyID, int playerID) throws Exception {
        return sqlDB.get_PartyDetails(partyID,playerID);
    }

    /**
     * @param partyID
     * @return
     * */
    @Override
    public boolean isPartyFull(int partyID) throws Exception {
        return sqlDB.isPartyFull(partyID);
    }

    /**
     * @param playerID
     * @param partyID
     * */
    @Override
    public void addPlayerToParty(int playerID, int partyID) throws Exception {
        if(!isPartyFull(partyID))
            sqlDB.addPlayerToParty(playerID,partyID);
    }

    /**
     * @param partyID
     * @param playerID
     * */
    @Override
    public void removePlayerFromParty(int partyID, int playerID) throws Exception {
        sqlDB.removePlayerFromParty(partyID,playerID);
    }

    /**
     * @param username
     * @param email
     * @return
     * */
    @Override
    public int checkUserNameAndEmail(String username, String email) throws Exception {
        return sqlDB.check_Name_Email(username,email);
    }

    /**
     * @param partyID
     * @return
     * */
    @Override
    public boolean doesPartyExist(int partyID) throws Exception {
        return sqlDB.does_Party_Exist(partyID);
    }

    /**
     * @param username
     * @return
     * */
    @Override
    public int doesPlayerExist(String username) throws Exception {
        return sqlDB.does_Player_Exist(username);
    }

    /**
     * @param playerID
     * @return
     * */
    @Override
    public boolean isPlayerInParty(int playerID) throws Exception {
        return sqlDB.is_In_Party(playerID);
    }

    /**
     * @param senderID
     * @param receiverID
     * @param partyId
     * */
    @Override// String content,int type
    public void addInvite(int senderID, int receiverID, int partyId) throws Exception {
        sqlDB.add_Invite(senderID,receiverID,partyId);
    }

    /**
     * @param senderID
     * @param receiverID
     * @param inviteId
     * */
    @Override
    public void removeInvite(int senderID, int receiverID, int inviteId) throws Exception {
        sqlDB.remove_Invite(senderID,receiverID,inviteId);
    }

    /**
     * @param userId
     * @param friendId
     * */
    @Override
    public void addFriend(int userId, int friendId)
    {
        sqlDB.add_Friend(userId,friendId);
    }


}
