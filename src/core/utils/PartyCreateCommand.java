package core.utils;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PartyCreateCommand implements ICommand {
    private String name;

    public PartyCreateCommand(String name){
        this.name = name;
    }

    @Override
    public void execute() {
        SessionInformation.getInstance().createParty();
    }

    @Override
    public String getCommandName() {
        return name;
    }
}
