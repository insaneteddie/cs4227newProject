package core.command;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PlayerInvitesRetrieveCommand implements ICommand {
    private final String name;

    /**
     * @param name
     * */
    public PlayerInvitesRetrieveCommand(String name){
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
