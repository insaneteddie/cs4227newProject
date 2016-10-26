/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/ProcessInput.java
*   Approximate use: 40%
**/
package core.session;

import core.user.Player;
import core.utils.Log;
import database.DatabaseAdapter;
import database.DatabaseInterface;
import message.Invite;
import message.InviteFactory;
import message.Message;
import message.PartyInviteFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *  Contains variables and methods dealing with an active core.session
 * */
public class SessionInformation {

    private static SessionInformation sessionInfo = null;
    private DatabaseInterface database;
    private InviteFactory inviteFactory;
    private Player player = null;
    private Party party;
    private final Log log = new Log(getClass().getName());
    
    private SessionInformation() {
        setPlayer();
        party = new Party();
        player.attach(party);
        inviteFactory = new PartyInviteFactory();
    }

    public static SessionInformation getInstance() {
        if (sessionInfo == null) {
            sessionInfo = new SessionInformation();
        }

        return sessionInfo;
    }

    /**
     *  stores the Player instance in player
     * */
    public void setPlayer() {
        player = Player.getInstance();
    }

    public void setDbConnection(DatabaseInterface database) {
        this.database = database;
    }

    /**
     * @param username
     * @param password
     * @return
     * */
    public boolean canUserLogin(String username, String password) {
        boolean canLogin = false;
        DatabaseAdapter dbA = new DatabaseAdapter();
        try {
            canLogin = dbA.canLogin(username,password);//database.canLogin(username, password);
            if (canLogin) {
                getPlayerDetails(username);
                getPlayerFriendList();
                getPlayerInvites();
            }
        } catch (Exception e) {
            log.logException("Error logging in: ", e);
        }

        return canLogin;
    }

    /**
     *  @param username
     *  @return
     * */
    public boolean doesPlayerExist(String username) {
        boolean exists = false;

        try {
            if (database.doesPlayerExist(username) != 0) {
                exists = true;
            }
        } catch (Exception e) {
            log.logException("doesPlayerExist Error: ", e);
        }

        return exists;
    }

    /**
     * @param username
     * @param email
     * @return
     * */
    public int checkUsernameEmail(String username, String email) {
        int existsType = -1;

        try {
            existsType = database.checkUserNameAndEmail(username, email);
        } catch (Exception e) {
            log.logException("Error validating username/email: ", e);
        }

        return existsType;
    }

    /**
     * @param username
     * @param password
     * @param email
     * */
    public void createPlayer(String username, String password, String email) {
        try {

            DatabaseAdapter dbA = new DatabaseAdapter();
            dbA.createPlayer(username,password,email);
            //database.createPlayer(username, password, email);
        } catch (Exception e) {
            log.logException("Error creating player: ", e);
        }
    }

    /**
     * @param username
     * */
    public void getPlayerDetails(String username) {
        try {
            String[] details = database.getPlayerDetails(username).split(",");

            this.player.setId(Integer.parseInt(details[0]));
            this.player.setName(details[1]);
            this.player.setEmail(details[2]);
        } catch (Exception e) {
            log.logException("Error getting player details: ", e);
        }
    }

    public void getPlayerFriendList() {
        try {
            ArrayList<Integer> friends = database.getPlayerFriendList(player.getId());
            if (!friends.isEmpty()) {
                for (int i = 0; i < friends.size(); i++) {
                    player.addFriend(friends.get(i));
                }
            }
        } catch (Exception e) {
            log.logException(e);
        }
    }

    public void getPlayerInvites() {
        try {
            ArrayList<Integer[]> invites = database.getPlayerInvites(player.getId());
            if (!invites.isEmpty()) {
                for (int i = 0; i < invites.size(); i++) {
                    Message newInvite = inviteFactory.createInvite(invites.get(i)[0], player.getId(), invites.get(i)[1]);
                    player.addInvite(newInvite);
                }
            }
        } catch (Exception e) {
            log.logException(e);
        }
    }

    public void getPartyDetails() {
        try {
            ArrayList<Integer> partyDetails = database.getPartyDetails(party.getId(), player.getId());
            
            player.clearPartyInformation();
            player.updatePartyInformation(partyDetails);
            player.update();
        } catch (Exception e) {
            log.logException(e);
        }
    }

    public boolean isPlayerInParty() {
        return party.doesPartyExist();
    }

    /**
     * @param id
     * @return
     * */
    public boolean isMemberOfParty(int id) {
        return party.isMember(id);
    }

    /**
     * @param id
     * @return
     * */
    public boolean isFriend(int id) {
        return player.isFriend(id);
    }

    /**
     *  Creates a party
     * */
    public void createParty() {
        try {
            int partyID = database.createParty(player.getId());
            player.addToPartyInformation(partyID);
            player.addToPartyInformation(player.getId());
            player.update();
        } catch (Exception e) {
            log.logException(e);
        }
    }

    /**
     *  Leaves current party
     * */
    public void leaveParty() {
        try {
            int partyID = party.getId();
            log.logException("Party ID: " + partyID);
            log.logException("Player ID: " + player.getId());
            database.removePlayerFromParty(partyID, player.getId());
            player.clearPartyInformation();
            player.update();
        } catch (Exception e) {
            log.logException(e);
        }
    }

    public String getPlayerName() {
        return player.getName();
    }

    /**
     *  log off the player
     * */
    public void logPlayerOut() {
        if (party.doesPartyExist()) {
            leaveParty();
        }
        log.logException("Logged out");
        player.resetValues();
    }

    public boolean isPartyLeader() {
        return party.isPartyLeader(player.getId());
    }

    public int getPartySize() {
        return party.getPartySize();
    }

    /**
     *  @param partyID
     * */
    public void addPlayerToParty(int partyID) {
        try {
            player.clearPartyInformation();
            player.addToPartyInformation(partyID);
            player.addToPartyInformation(player.getId());
            player.update();
            database.addPlayerToParty(player.getId(), partyID);

            getPartyDetails();
        } catch (Exception e) {
            log.logException(e);
        }
    }

    /**
     *  @param playerID
     * */
    public void removePlayerFromParty(int playerID) {
        try {
            database.removePlayerFromParty(party.getId(), playerID);
        } catch (Exception e) {
            log.logException(e);
        }
    }

    /**
     *  @return
     * */
    public List<String> getInviteMessages() {
        List<String> invMsg = new ArrayList<>();
        List<Invite> invites = player.getInvites();
        for (int i = 0; i < invites.size(); i++) {
            invMsg.add(invites.get(i).getMessage());
        }

        return invMsg;
    }

    /**
     *  @return
     * */
    public List<Integer> getPartyMembers() {
        return party.getPartyMembers();
    }

    /**
     *  @param friendToInvite
     * *///,String content, int type
    public void sendInvite(int friendToInvite) {
        try {
            database.addInvite(player.getId(), friendToInvite, party.getId() );
        } catch (Exception ex) {
            log.logException(ex);
        }
    }

    /**
     *  @param playerID
     *  @return
     * */
    public int getPartyIDFromSenderInvite(int playerID)
    {
        List<Invite> myInvites = player.getInvites();
        int partyID = 0;
        for (int i = 0; i < myInvites.size(); i++) {
            if (playerID == myInvites.get(i).getSenderID()) {
                partyID = myInvites.get(i).getPartyID();
                break;
            }
        }
        log.logException("Party ID: " + partyID);
        return partyID;
    }
    /**
     *  @param playerID
     * */
    public void removeInvite(int playerID) {
        int partyID = getPartyIDFromSenderInvite(playerID);
        player.removeInvite(playerID, partyID);
        try {
            database.removeInvite(playerID, player.getId(), partyID);
        } catch (Exception e) {
            log.logException(e);
        }
    }

    /**
     *  @return
     * */
    public int getPlayerId()
    {
        return player.getId();
    }
}
