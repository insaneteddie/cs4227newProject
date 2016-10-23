package core.utils;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PlayerLogOutCommand implements ICommand{
    private String name;
    private SessionInformation sessionInformation;

    public PlayerLogOutCommand(String name){
        this.name = name;
    }

    @Override
    public void execute() {
        SessionInformation.getInstance().logPlayerOut();
    }

    @Override
    public String getCommandName() {
        return name;
    }
}
