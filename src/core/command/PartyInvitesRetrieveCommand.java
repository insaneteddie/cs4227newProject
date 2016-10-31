package core.command;

import core.command.ICommand;
import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PartyInvitesRetrieveCommand implements ICommand {
    private final String name;

    /**
     * @param name
     * */
    public PartyInvitesRetrieveCommand(String name){
        this.name = name;
    }

    @Override
    public void execute() {
        SessionInformation.getInstance().getPlayerInvites();
    }

    /**
     * @return
     * */
    @Override
    public String getCommandName() {
        return name;
    }
}
