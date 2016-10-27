package core.utils;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PartyCreateCommand implements ICommand {
    private final String name;

    /**
     * @param name
     * */
    public PartyCreateCommand(String name){
        this.name = name;
    }

    @Override
    public void execute() {
        SessionInformation.getInstance().createParty();
    }

    /**
     * @return
     * */
    @Override
    public String getCommandName() {
        return name;
    }
}
