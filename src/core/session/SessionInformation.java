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
import core.interceptor.ConcreteSimpleLoggingRequest;
import core.interceptor.LogDispatcher;
import core.interceptor.LoggingRequest;
import core.user.Player;

import core.utils.Log;
import database.DatabaseBridge;

import database.DatabaseInterface;
import messaging.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Contains variables and methods dealing with an active core.session
 * */
public class SessionInformation {

    private static SessionInformation sessionInfo = null;
    private DatabaseInterface database;
    private AbstractMessagingFactory abstractMessagingFactory;
    private Player player = null;
    private Party party;
    private DatabaseBridge sqlDB = new DatabaseBridge();

    private SessionInformation() {
        setUpController();
        setPlayer();
        party = new Party();
        player.attach(party);
        abstractMessagingFactory = MessagingProvider.getFactory("INVITE");
    }

    public static SessionInformation getInstance() {
        if (sessionInfo == null) {
            sessionInfo = new SessionInformation();
        }

        return sessionInfo;
    }

    /**
     * instantiate a SessionController object and add default commands */
    private void setUpController(){
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
    private void setPlayer() {
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

        try {
            canLogin = sqlDB.canLogin(username,password);
            if (canLogin) {
                getPlayerDetails(username);
                getPlayerFriendList();
                getPlayerInvites();
            }
        } catch (Exception e) {
            //log.logWarning(e, "Error logging in: ");
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, "Error logging in: "));
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
            if (sqlDB.doesPlayerExist(username) != 0) {
                exists = true;
            }
        } catch (Exception e) {
            //log.logWarning(e, "doesPlayerExist Error: ");
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, "doesPlayerExist Error: "));
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
            existsType = sqlDB.checkUserNameAndEmail(username, email);
        } catch (Exception e) {
            //log.logWarning(e, "Error validating username/email: ");
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, "Error validating username/email: "));
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
            sqlDB.createPlayer(username,password,email);
        } catch (Exception e) {
            //log.logWarning(e, "Error creating player: ");
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, "Error creating player: "));
        }
    }

    /**
     * @param username
     * */
    public void getPlayerDetails(String username) {
        try {
            System.out.print("Testing player details method");
            String[] details = sqlDB.getPlayerDetails(username).split(",");
            System.out.print("After DB call");
            this.player.setId(Integer.parseInt(details[0]));
            this.player.setName(username);
            this.player.setEmail(details[1]);
            //this.player.setEmail(details[2]);
        } catch (Exception e) {
            //log.logWarning(e, "Error getting player details: "+e.getMessage());
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, "Error getting player details: "+e.getMessage()));
        }
    }

    public void getPlayerFriendList() {
        try {//database.getPlayerFriendList
            List<Integer> friends = sqlDB.getPlayerFriendList(player.getId());
            if (!friends.isEmpty()) {
                for (int i = 0; i < friends.size(); i++) {
                    player.addFriend(friends.get(i));
                }
            }
        } catch (Exception e) {
            //log.logWarning(e);
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        }
    }

    public void getPlayerInvites() {
        try {//database
            List<Integer[]> invites = sqlDB.getPlayerInvites(player.getId());
            if (!invites.isEmpty()) {
                for (int i = 0; i < invites.size(); i++) {
                    Invite newInvite = abstractMessagingFactory.createInvite("PARTY_INVITE", player.getId(), invites.get(i)[1], invites.get(i)[2]);
                    player.addInvite(newInvite);
                }
            }
        } catch (Exception e) {
            //log.logWarning(e);
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        }
    }

    public void getPartyDetails() {
        try {
            List<Integer> partyDetails = sqlDB.getPartyDetails(party.getId(), player.getId());
            
            player.clearPartyInformation();
            player.updatePartyInformation(partyDetails);
            player.update();
        } catch (Exception e) {
            //log.logWarning(e);
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
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
            //log.logWarning(e);
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        }
    }

    /**
     *  Leaves current party
     * */
    public void leaveParty() {
        try {
            int partyID = party.getId();
            database.removePlayerFromParty(partyID, player.getId());
            player.clearPartyInformation();
            player.update();
        } catch (Exception e) {
            //log.logWarning(e);
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
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
        //log.logInfo("Logged out");
        LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "Logged out"));
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
            //log.logWarning(e);
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        }
    }

    /**
     *  @param playerID
     * */
    public void removePlayerFromParty(int playerID) {
        try {
            database.removePlayerFromParty(party.getId(), playerID);
        } catch (Exception e) {
            //log.logWarning(e);
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
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
            //log.logWarning(ex);
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, ex, ""));
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
        //log.logInfo("Party ID: " + partyID);
        LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "Party ID: " + partyID));
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
            //log.logWarning(e);
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
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
