/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/ProcessInput.java
*   Approximate use: 40%
**/
package core.session;

import core.command.*;
import core.user.Player;

import core.utils.Log;

import database.DatabaseBridge;

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
    private DatabaseBridge sqlDB = new DatabaseBridge();

    SessionInformation() {
        setUpController();
        setPlayer();
        party = new Party();
        player.attach(party);
        inviteFactory = new PartyInviteFactory();
    }

    static SessionInformation getInstance() {
        if (sessionInfo == null) {
            sessionInfo = new SessionInformation();
        }

        return sessionInfo;
    }

    /**
     * instantiate a SessionController object and add default commands */
     void setUpController(){
        SessionController.getInstance().addCommand(new FriendInviteSendCommand("FRIEND_INVITE_SEND"));
        SessionController.getInstance().addCommand(new InviteRemoveCommand("INVITE_REMOVE"));
        SessionController.getInstance().addCommand(new PartyCreateCommand("PARTY_CREATE"));
        SessionController.getInstance().addCommand(new PartyDetailsRetrieveCommand("PARTY_DETAILS_RETRIEVE"));
        SessionController.getInstance().addCommand(new PartyInvitesRetrieveCommand("PARTY_INVITES_RETRIEVE"));
        SessionController.getInstance().addCommand(new PartyLeaveCommand("PARTY_LEAVE"));
        SessionController.getInstance().addCommand(new PartyMemberAddCommand("PARTY_MEMBER_ADD"));
        SessionController.getInstance().addCommand(new PartyMemberRemoveCommand("PARTY_MEMBER_REMOVE"));
        SessionController.getInstance().addCommand(new PlayerCreateCommand("PLAYER_CREATE"));
        SessionController.getInstance().addCommand(new PlayerInvitesRetrieveCommand("PLAYER_INVITES_RETRIEVE"));
        SessionController.getInstance().addCommand(new PlayerLogOutCommand("PLAYER_LOG_OUT"));
    }

    /**
     *  stores the Player instance in player
     * */
     void setPlayer() {
        player = Player.getInstance();
    }

     void setDbConnection(DatabaseInterface database) {
        this.database = database;
    }

    /**
     * @param username
     * @param password
     * @return
     * */
     boolean canUserLogin(String username, String password) {
        boolean canLogin = false;

        try {
            canLogin = sqlDB.canLogin(username,password);
            if (canLogin) {
                getPlayerDetails(username);
                getPlayerFriendList();
                getPlayerInvites();
            }
        } catch (Exception e) {
            log.logWarning(e, "Error logging in: ");
        }

        return canLogin;
    }

    /**
     *  @param username
     *  @return
     * */
    boolean doesPlayerExist(String username) {
        boolean exists = false;

        try {
            if (sqlDB.doesPlayerExist(username) != 0) {
                exists = true;
            }
        } catch (Exception e) {
            log.logWarning(e, "doesPlayerExist Error: ");
        }

        return exists;
    }

    /**
     * @param username
     * @param email
     * @return
     * */
    int checkUsernameEmail(String username, String email) {
        int existsType = -1;

        try {
            existsType = sqlDB.checkUserNameAndEmail(username, email);
        } catch (Exception e) {
            log.logWarning(e, "Error validating username/email: ");
        }

        return existsType;
    }

    /**
     * @param username
     * @param password
     * @param email
     * */
    void createPlayer(String username, String password, String email) {
        try {
            sqlDB.createPlayer(username,password,email);
        } catch (Exception e) {
            log.logWarning(e, "Error creating player: ");
        }
    }

    /**
     * @param username
     * */
    void getPlayerDetails(String username) {
        try {

            String[] details = sqlDB.getPlayerDetails(username).split(",");

            this.player.setId(Integer.parseInt(details[0]));
            this.player.setName(username);
            this.player.setEmail(details[1]);
            //this.player.setEmail(details[2]);
        } catch (Exception e) {
            log.logWarning(e, "Error getting player details: "+e.getMessage());
        }
    }

    void getPlayerFriendList() {
        try {//database.getPlayerFriendList
            List<Integer> friends = sqlDB.getPlayerFriendList(player.getId());
            if (!friends.isEmpty()) {
                for (int i = 0; i < friends.size(); i++) {
                    player.addFriend(friends.get(i));
                }
            }
        } catch (Exception e) {
            log.logWarning(e);
        }
    }

    void getPlayerInvites() {
        try {//database
            List<Integer[]> invites = sqlDB.getPlayerInvites(player.getId());
            if (!invites.isEmpty()) {
                for (int i = 0; i < invites.size(); i++) {
                    Message newInvite = inviteFactory.createInvite(invites.get(i)[0], player.getId(), invites.get(i)[1]);
                    player.addInvite(newInvite);
                }
            }
        } catch (Exception e) {
            log.logWarning(e);
        }
    }

    void getPartyDetails() {
        try {
            List<Integer> partyDetails = sqlDB.getPartyDetails(party.getId(), player.getId());
            
            player.clearPartyInformation();
            player.updatePartyInformation(partyDetails);
            player.update();
        } catch (Exception e) {
            log.logWarning(e);
        }
    }

    boolean isPlayerInParty() {
        return party.doesPartyExist();
    }

    /**
     * @param id
     * @return
     * */
    boolean isMemberOfParty(int id) {
        return party.isMember(id);
    }

    /**
     * @param id
     * @return
     * */
    boolean isFriend(int id) {
        return player.isFriend(id);
    }

    /**
     *  Creates a party
     * */
    void createParty() {
        try {
            int partyID = database.createParty(player.getId());
            player.addToPartyInformation(partyID);
            player.addToPartyInformation(player.getId());
            player.update();
        } catch (Exception e) {
            log.logWarning(e);
        }
    }

    /**
     *  Leaves current party
     * */
    void leaveParty() {
        try {
            int partyID = party.getId();
            database.removePlayerFromParty(partyID, player.getId());
            player.clearPartyInformation();
            player.update();
        } catch (Exception e) {
            log.logWarning(e);
        }
    }

    String getPlayerName() {
        return player.getName();
    }

    /**
     *  log off the player
     * */
    void logPlayerOut() {
        if (party.doesPartyExist()) {
            leaveParty();
        }
        log.logInfo("Logged out");
        player.resetValues();
    }

    boolean isPartyLeader() {
        return party.isPartyLeader(player.getId());
    }

    int getPartySize() {
        return party.getPartySize();
    }

    /**
     *  @param partyID
     * */
    void addPlayerToParty(int partyID) {
        try {
            player.clearPartyInformation();
            player.addToPartyInformation(partyID);
            player.addToPartyInformation(player.getId());
            player.update();
            database.addPlayerToParty(player.getId(), partyID);

            getPartyDetails();
        } catch (Exception e) {
            log.logWarning(e);
        }
    }

    /**
     *  @param playerID
     * */
    void removePlayerFromParty(int playerID) {
        try {
            database.removePlayerFromParty(party.getId(), playerID);
        } catch (Exception e) {
            log.logWarning(e);
        }
    }

    /**
     *  @return
     * */
    List<String> getInviteMessages() {
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
    List<Integer> getPartyMembers() {
        return party.getPartyMembers();
    }

    /**
     *  @param friendToInvite
     * *///,String content, int type
    void sendInvite(int friendToInvite) {
        try {
            database.addInvite(player.getId(), friendToInvite, party.getId() );
        } catch (Exception ex) {
            log.logWarning(ex);
        }
    }

    /**
     *  @param playerID
     *  @return
     * */
    int getPartyIDFromSenderInvite(int playerID)
    {
        List<Invite> myInvites = player.getInvites();
        int partyID = 0;
        for (int i = 0; i < myInvites.size(); i++) {
            if (playerID == myInvites.get(i).getSenderID()) {
                partyID = myInvites.get(i).getPartyID();
                break;
            }
        }
        log.logInfo("Party ID: " + partyID);
        return partyID;
    }
    /**
     *  @param playerID
     * */
    void removeInvite(int playerID) {
        int partyID = getPartyIDFromSenderInvite(playerID);
        player.removeInvite(playerID, partyID);
        try {
            database.removeInvite(playerID, player.getId(), partyID);
        } catch (Exception e) {
            log.logWarning(e);
        }
    }

    /**
     *  @return
     * */
    int getPlayerId()
    {
        return player.getId();
    }
}
