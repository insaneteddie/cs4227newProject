package database;

import java.util.ArrayList;

/**
 * Created by s_harte CS4227 Awesome Gaming  on 10/20/2016.
 */
public class DatabaseAdapter implements DatabaseInterface {


    private final SqlDatabase sqlDB;
    // k im not sure about this....
    public DatabaseAdapter()
    {
        sqlDB = new SqlDatabase();

    }
    //overloaded to allow connection to own database
    @SuppressWarnings("unused")
    public DatabaseAdapter(String databaseURL, String dbUser, String dbPass, String JDBCDriver)
    {
        sqlDB = new SqlDatabase(databaseURL,dbUser,dbPass,JDBCDriver);
    }
    @Override
    public boolean canLogin(String username, String password) throws Exception {
        return sqlDB.can_Login(username,password);
    }
    //this needs to be edited... seems a bit pointless to pass in the username to get the username
    @Override
    public String getPlayerDetails(String username) throws Exception {
        return sqlDB.get_PlayerName(sqlDB.get_UserId(username));
    }

    @Override
    public ArrayList<Integer> getPlayerFriendList(int playerID) throws Exception {
        return sqlDB.get_FriendsList(playerID);
    }

    @Override
    public ArrayList<Integer[]> getPlayerInvites(int playerID) throws Exception {
        return sqlDB.get_Invites(playerID);
    }

    @Override
    public void createPlayer(String username, String password, String email) throws Exception {
            sqlDB.add_User(username,password,email);
    }

    //returns party_Id or 0
    @Override
    public int createParty(int partyLeaderID) throws Exception {
        return sqlDB.create_Party(partyLeaderID);
    }

    @Override
    public ArrayList<Integer> getPartyDetails(int partyID, int playerID) throws Exception {
        return sqlDB.get_PartyDetails(partyID,playerID);
    }

    @Override
    public boolean isPartyFull(int partyID) throws Exception {
        return sqlDB.isPartyFull(partyID);
    }

    @Override
    public void addPlayerToParty(int playerID, int partyID) throws Exception {
        if(!isPartyFull(partyID))
            sqlDB.addPlayerToParty(playerID,partyID);
    }

    @Override
    public void removePlayerFromParty(int partyID, int playerID) throws Exception {
        sqlDB.removePlayerFromParty(partyID,playerID);
    }

    @Override
    public int checkUserNameAndEmail(String username, String email) throws Exception {
        return sqlDB.check_Name_Email(username,email);

    }

    @Override
    public boolean doesPartyExist(int partyID) throws Exception {
        return sqlDB.does_Party_Exist(partyID);
    }

    @Override
    public int doesPlayerExist(String username) throws Exception {
        return sqlDB.does_Player_Exist(username);
    }

    @Override
    public boolean isPlayerInParty(int playerID) throws Exception {
        return sqlDB.is_In_Party(playerID);
    }

    @Override// String content,int type
    public void addInvite(int senderID, int receiverID, int partyId) throws Exception {
        sqlDB.add_Invite(senderID,receiverID,partyId);
    }

    @Override
    public void removeInvite(int senderID, int receiverID, int inviteId) throws Exception {
        sqlDB.remove_Invite(senderID,receiverID,inviteId);
    }

    @Override
    public void addFriend(int user_Id, int friend_Id)
    {
        sqlDB.add_Friend(user_Id,friend_Id);
    }


}
