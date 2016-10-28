package core.utils;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class InviteRemoveCommand implements ICommand {
    private final String name;
    private int userId;

    /**
     * @param name
     * */
    public InviteRemoveCommand(String name){
        this.name = name;
    }
    @Override
    public void execute() {
        SessionInformation.getInstance().removeInvite(userId);
        userId = 0;
    }

    /**
     * @param userId
     * */
    public void execute(int userId){
        this.userId = userId;
        execute();
    }

    @Override
    public String getCommandName() {
        return name;
    }
}
