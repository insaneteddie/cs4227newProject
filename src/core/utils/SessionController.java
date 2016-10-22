package core.utils;

import core.session.SessionInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 22/10/2016.
 */
public class SessionController {
    private List commands;
    private SessionInformation sessionInformation;
    private static SessionController sessionController;

    private SessionController(){
        commands = new ArrayList<ICommand>();
        sessionInformation = SessionInformation.getInstance();
    }

    public SessionController getInstance(){
        if(sessionController == null){
            sessionController = new SessionController();
        }
        return sessionController;
    }

    public void executeCommand(String commandName){
        for(Object c : commands){
            if(((ICommand)c).getCommandName().matches(commandName)){
                ((ICommand)c).execute();
                return;
            }
        }
    }
}
