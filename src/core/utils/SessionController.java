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
