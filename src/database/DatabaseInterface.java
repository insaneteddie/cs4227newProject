/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/DatabaseInterface.java
*   Approximate use: 30%
**/

package database;

import java.util.ArrayList;

public interface DatabaseInterface {

    boolean canLogin(String username, String password) throws Exception;

    /* References the local Player class */
    String getPlayerDetails(String username) throws Exception;

    ArrayList<Integer> getPlayerFriendList(int playerID) throws Exception;

    ArrayList<Integer[]> getPlayerInvites(int playerID) throws Exception;

    void createPlayer(String username, String password, String email) throws Exception;

    int createParty(int partyLeaderID) throws Exception;

    ArrayList<Integer> getPartyDetails(int partyID, int playerID) throws Exception;

    boolean isPartyFull(int partyID) throws Exception;

    void addPlayerToParty(int playerID, int partyID) throws Exception;

    void removePlayerFromParty(int partyID, int playerID) throws Exception;

    int checkUserNameAndEmail(String username, String email) throws Exception;

    boolean doesPartyExist(int partyID) throws Exception;

    int doesPlayerExist(String username) throws Exception;

    boolean isPlayerInParty(int playerID) throws Exception;

    void addInvite(int senderID, int receiverID, int partyID) throws Exception;

    void removeInvite(int senderID, int receiverID, int partyID) throws Exception;
}
