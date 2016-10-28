package core.utils;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PartyMemberAddCommand implements ICommand {
    private final String name;
    private int userId;

    /**
     * @param name
     * */
    public PartyMemberAddCommand(String name){
        this.name = name;
    }

    @Override
    public void execute() {
        SessionInformation.getInstance()
                .addPlayerToParty(SessionInformation.getInstance()
                        .getPartyIDFromSenderInvite(userId));
        userId = 0;
    }

    /**
     * @param userId
     * */
    public void execute(int userId){
        this.userId = userId;
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
