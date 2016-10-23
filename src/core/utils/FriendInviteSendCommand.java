package core.utils;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class FriendInviteSendCommand implements ICommand {
    private final String name;
    private int player_id;

    public FriendInviteSendCommand(String name){
        this.name = name;
    }
    @Override
    public void execute() {
        SessionInformation.getInstance().sendInvite(player_id);
        player_id = 0;
    }

    public void execute(int player_id){
        this.player_id = player_id;
        execute();
    }
    @Override
    public String getCommandName() {
        return name;
    }
}
