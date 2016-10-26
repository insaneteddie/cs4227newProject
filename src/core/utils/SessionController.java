package core.utils;

import core.session.SessionInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 22/10/2016.
 */
public class SessionController {
    private List commands;
    private static SessionController sessionController;

    public static final String FRIEND_INVITE_SEND = "FRIEND_INVITE_SEND";
    public static final String INVITE_REMOVE = "INVITE_REMOVE";
    public static final String PARTY_CREATE = "PARTY_CREATE";
    public static final String PARTY_DETAILS_RETRIEVE = "PARTY_DETAILS_RETRIEVE";
    public static final String PARTY_INVITES_RETRIEVE = "PARTY_INVITES_RETRIEVE";
    public static final String PARTY_LEAVE = "PARTY_LEAVE";
    public static final String PARTY_MEMBER_ADD = "PARTY_MEMBER_ADD";
    public static final String PARTY_MEMBER_REMOVE = "PARTY_MEMBER_REMOVE";
    public static final String PLAYER_CREATE = "PLAYER_CREATE";
    public static final String PLAYER_INVITES_RETRIEVE = "PLAYER_INVITES_RETRIEVE";
    public static final String PLAYER_LOG_OUT = "PLAYER_LOG_OUT";
    
    private SessionController(){
        commands = new ArrayList<ICommand>();
    }

    public static SessionController getInstance(){
        if(sessionController == null){
            sessionController = new SessionController();
        }
        return sessionController;
    }

    public void executeCommand(String commandName){
        getCommand(commandName).execute();
    }

    public void executeCommand(String commandName, int id){
        getCommand(commandName).execute();
    }

    private ICommand getCommand(String commandName){
        for(Object c : commands){
            if(((ICommand)c).getCommandName().matches(commandName)){
                return (ICommand)c;
            }
        }
        return null;
    }

    public void addCommand(ICommand command){
        if(commands != null){
            commands.add(command);
        }
    }

    public void removeCommand(String commandName){
        for(Object c : commands){
            if(((ICommand)c).getCommandName().matches(commandName)){
                commands.remove(c);
                return;
            }
        }
    }
}
