package core.utils;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PlayerCreateCommand implements ICommand{
    private final String name;
    private String username;
    private String password;
    private String email;

    public PlayerCreateCommand(String name){
        this.name = name;
    }
    @Override
    public void execute() {
        SessionInformation.getInstance().createPlayer(username, password, email);
        username = "";
        password = "";
        email = "";
    }

    public void execute(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        execute();
    }

    @Override
    public String getCommandName() {
        return name;
    }
}
