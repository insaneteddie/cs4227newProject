package core.utils;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PartyInvitesRetrieveCommand implements ICommand {
    private final String name;

    public PartyInvitesRetrieveCommand(String name){
        this.name = name;
    }

    @Override
    public void execute() {
        SessionInformation.getInstance().getPlayerInvites();
    }

    @Override
    public String getCommandName() {
        return name;
    }
}
