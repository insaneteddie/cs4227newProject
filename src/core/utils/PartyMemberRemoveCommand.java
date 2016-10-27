package core.utils;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PartyMemberRemoveCommand implements ICommand{
    private final String name;
    private int memberId;

    public PartyMemberRemoveCommand(String name){
        this.name = name;
    }
    @Override
    public void execute() {
        SessionInformation.getInstance().removePlayerFromParty(memberId);
        memberId = 0;
    }

    public void execute(int memberId){
        this.memberId = memberId;
        execute();
    }

    @Override
    public String getCommandName() {
        return name;
    }
}
