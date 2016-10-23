package core.utils;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PartyDetailsRetrieveCommand implements ICommand {
    private final String name;

    public PartyDetailsRetrieveCommand(String name){
        this.name = name;
    }

    @Override
    public void execute() {
        SessionInformation.getInstance().getPartyDetails();
    }

    @Override
    public String getCommandName() {
        return name;
    }
}
