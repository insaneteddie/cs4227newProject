package database;

import java.util.ArrayList;

/**
 * Created by s_harte CS4227 Awesome Gaming  on 10/20/2016.
 */
public class DatabaseAdapter implements DatabaseInterface {


    public SqlDatabase sqlDB;
    // k im not sure about this....
    public DatabaseAdapter(){
        sqlDB = new SqlDatabase();

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
        return null;
    }

    @Override
    public ArrayList<Integer[]> getPlayerInvites(int playerID) throws Exception {
        return null;
    }

    @Override
    public void createPlayer(String username, String password, String email) throws Exception {
            sqlDB.add_User(username,password,email);
    }

    @Override
    public int createParty(int partyLeaderID) throws Exception {
        return 0;
    }

    @Override
    public ArrayList<Integer> getPartyDetails(int partyID, int playerID) throws Exception {
        return null;
    }

    @Override
    public boolean isPartyFull(int partyID) throws Exception {
        return false;
    }

    @Override
    public void addPlayerToParty(int playerID, int partyID) throws Exception {

    }

    @Override
    public void removePlayerFromParty(int partyID, int playerID) throws Exception {

    }

    @Override
    public int checkUserNameAndEmail(String username, String email) throws Exception {
        return 0;
    }

    @Override
    public boolean doesPartyExist(int partyID) throws Exception {
        return false;
    }

    @Override
    public int doesPlayerExist(String username) throws Exception {
        return 0;
    }

    @Override
    public boolean isPlayerInParty(int playerID) throws Exception {
        return false;
    }

    @Override
    public void addInvite(int senderID, int receiverID, int partyID) throws Exception {

    }

    @Override
    public void removeInvite(int senderID, int receiverID, int partyID) throws Exception {

    }
}
