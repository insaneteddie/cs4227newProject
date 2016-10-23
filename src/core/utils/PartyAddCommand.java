package core.utils;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PartyAddCommand implements ICommand {
    private final String name;
    private int userId;

    public PartyAddCommand(String name){
        this.name = name;
    }

    @Override
    public void execute() {
        SessionInformation.getInstance()
                .addPlayerToParty(SessionInformation.getInstance()
                        .getPartyIDFromSenderInvite(userId));
        userId = 0;
    }

    public void execute(int userId){
        this.userId = userId;
        execute();
    }

    @Override
    public String getCommandName() {
        return name;
    }
}
