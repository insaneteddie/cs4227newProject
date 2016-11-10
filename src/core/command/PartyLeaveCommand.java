package core.command;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PartyLeaveCommand implements ICommand {
    private final String name;

    /**
     * @param name
     * */
    public PartyLeaveCommand(String name){
        this.name  = name;
    }

    @Override
    public void execute() {
        SessionInformation.getInstance().leaveParty();
    }

    /**
     * @return
     * */
    @Override
    public String getCommandName() {
        return name;
    }
}
