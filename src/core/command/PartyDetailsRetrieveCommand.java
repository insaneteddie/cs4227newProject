package core.command;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PartyDetailsRetrieveCommand implements ICommand {
    private final String name;

    /**
     * @param name
     * */
    public PartyDetailsRetrieveCommand(String name){
        this.name = name;
    }

    @Override
    public void execute() {
        SessionInformation.getInstance().getPartyDetails();
    }

    /**
     * @return
     * */
    @Override
    public String getCommandName() {
        return name;
    }
}
