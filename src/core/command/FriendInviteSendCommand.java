package core.command;

import core.command.ICommand;
import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class FriendInviteSendCommand implements ICommand {
    private final String name;
    private int playerId;

    /**
     * @param name
     * */
    public FriendInviteSendCommand(String name){
        this.name = name;
    }
    @Override
    public void execute() {
        SessionInformation.getInstance().sendInvite(playerId);
        playerId = 0;
    }

    /**
     * @param playerId
     * */
    public void execute(int playerId){
        this.playerId = playerId;
        execute();
    }
    @Override
    public String getCommandName() {
        return name;
    }
}
