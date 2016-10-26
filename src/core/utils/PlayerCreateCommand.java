package core.utils;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PlayerCreateCommand implements ICommand{
    private final String name;
    private String username;
    private String pass;
    private String email;

    public PlayerCreateCommand(String name){
        this.name = name;
    }
    @Override
    public void execute() {
        SessionInformation.getInstance().createPlayer(username, pass, email);
        username = "";
        pass = "";
        email = "";
    }

    public void execute(String username, String pass, String email){
        this.username = username;
        this.pass = pass;
        this.email = email;
        execute();
    }

    @Override
    public String getCommandName() {
        return name;
    }
}
