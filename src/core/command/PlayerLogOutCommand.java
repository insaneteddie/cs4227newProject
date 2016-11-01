package core.command;

import core.command.ICommand;
import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PlayerLogOutCommand implements ICommand {
    private final String name;

    /**
     * @param name
     * */
    public PlayerLogOutCommand(String name){
        this.name = name;
    }

    @Override
    public void execute() {
        SessionInformation.getInstance().logPlayerOut();
    }

    /**
     * @return
     * */
    @Override
    public String getCommandName() {
        return name;
    }
}
