package core.command;

import core.command.ICommand;
import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PartyMemberRemoveCommand implements ICommand {
    private final String name;
    private int memberId;

    /**
     * @param name
     * */
    public PartyMemberRemoveCommand(String name){
        this.name = name;
    }
    @Override
    public void execute() {
        SessionInformation.getInstance().removePlayerFromParty(memberId);
        memberId = 0;
    }

    /**
     * @param memberId
     * */
    public void execute(int memberId){
        this.memberId = memberId;
        execute();
    }

    /**
     * @return
     * */
    @Override
    public String getCommandName() {
        return name;
    }
}
