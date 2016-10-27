package core.utils;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class FriendInviteSendCommand implements ICommand {
    private final String name;
    private int player_id;

    /**
     * @param name
     * */
    public FriendInviteSendCommand(String name){
        this.name = name;
    }
    @Override
    public void execute() {
        SessionInformation.getInstance().sendInvite(player_id);
        player_id = 0;
    }

    /**
     * @param playerId
     * */
    public void execute(int playerId){
        this.player_id = playerId;
        execute();
    }
    @Override
    public String getCommandName() {
        return name;
    }
}
