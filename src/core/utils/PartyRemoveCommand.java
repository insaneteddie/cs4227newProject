package core.utils;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PartyRemoveCommand implements ICommand{
    private final String name;
    private int member_id;

    public PartyRemoveCommand(String name){
        this.name = name;
    }
    @Override
    public void execute() {
        SessionInformation.getInstance().removePlayerFromParty(member_id);
        member_id = 0;
    }

    public void execute(int member_id){
        this.member_id = member_id;
        execute();
    }

    @Override
    public String getCommandName() {
        return name;
    }
}
