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

        SessionController.getInstance().addCommand(new PartyCreateCommand("PARTY_CREATE"));
        SessionController.getInstance().addCommand(new PartyDetailsRetrieveCommand("PARTY_DETAILS_RETRIEVE"));
        SessionController.getInstance().addCommand(new PartyInvitesRetrieveCommand("PARTY_INVITES_RETRIEVE"));
        SessionController.getInstance().addCommand(new PartyLeaveCommand("PARTY_LEAVE"));
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

            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, "Error creating player: "));
        }
    }

    /**
     * @param username
     * */
    public void getPlayerDetails(String username) {
        try {
            String[] details = sqlDB.getPlayerDetails(username).split(",");
            this.player.setId(Integer.parseInt(details[0]));
            this.player.setName(username);
            this.player.setEmail(details[1]);

        } catch (Exception e) {

            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, "Error getting player details: "+e.getMessage()));
        }
    }

    public void getPlayerFriendList() {
        try {
            List<Integer> friends = sqlDB.getPlayerFriendList(player.getId());
            if (!friends.isEmpty()) {
                for (int i = 0; i < friends.size(); i++) {
                    player.addFriend(friends.get(i));
                }
            }
        } catch (Exception e) {

            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        }
    }

    public void getPlayerInvites() {
        try {
            List<Integer[]> invites = sqlDB.getPlayerInvites(player.getId());
            if (!invites.isEmpty()) {
                for (int i = 0; i < invites.size(); i++) {
                    Invite newInvite = abstractMessagingFactory.createInvite("PARTY_INVITE",  invites.get(i)[1],player.getId(), invites.get(i)[0]);
                    player.addInvite(newInvite);
                }
            }
        } catch (Exception e) {

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
     * @param userName of player to check
     * @return
     * */
    public boolean isFriend(String userName)
    {
        if(sqlDB.doesPlayerExist(userName) == 1)
        {
            int  id = sqlDB.getUserId(userName);
            System.out.println(id);
            return player.isFriend(id);
        }
        return false;

    }

    /**
     *  Creates a party
     * */
    public void createParty() {
        try {
            int partyID = sqlDB.createParty(player.getId());

            player.addToPartyInformation(partyID);
            player.addToPartyInformation(player.getId());
            player.update();
        } catch (Exception e) {

            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        }
    }

    /**
     *  Leaves current party
     * */
    public void leaveParty() {
        try {
            int partyID = party.getId();
            sqlDB.removePlayerFromParty(partyID,player.getId());

            player.clearPartyInformation();
            player.update();
        } catch (Exception e) {
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
     *  @param partyID int partyID to add player to
     * */
    public void addPlayerToParty(int partyID) {
        try {
            player.clearPartyInformation();
            player.addToPartyInformation(partyID);
            player.addToPartyInformation(player.getId());
            player.update();
            sqlDB.addPlayerToParty(player.getId(),partyID);


            getPartyDetails();
        } catch (Exception e) {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        }
    }

    /**
     *  @param playerID
     * */
    public void removePlayerFromParty(int playerID) {
        try {
            sqlDB.removePlayerFromParty(party.getId(),playerID);

        } catch (Exception e) {

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
     * */
    public void sendInvite(String friendToInvite) {
        try {
            sqlDB.addInvite(player.getId(), sqlDB.getUserId(friendToInvite), party.getId() );
        } catch (Exception ex) {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, ex, ""));
        }
    }

    /**
     *  @param playerName
     *  @return
     * */
    public int getPartyIDFromSenderInvite(String playerName)
    {
        int playerID = sqlDB.getUserId(playerName);
        List<Invite> myInvites = player.getInvites();
        int partyID = 1;
        for (int i = 0; i < myInvites.size(); i++) {

            if (playerID == myInvites.get(i).getSenderID()) {
                partyID = myInvites.get(i).getPartyID();
                break;
            }
        }

        LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "Party ID: " + partyID));
        return partyID;
    }
    /**
     *  @param playerName
     * */
    public void removeInvite(String playerName) {
        int playerID = sqlDB.getUserId(playerName);
        int partyID = getPartyIDFromSenderInvite(playerName);
        player.removeInvite(playerID, partyID);
        try {
            sqlDB.removeInvite(playerID, player.getId(), partyID);
        } catch (Exception e) {

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
