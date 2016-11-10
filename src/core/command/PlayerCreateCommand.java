package core.command;

import core.session.SessionInformation;

/**
 * Created by David on 23/10/2016.
 */
public class PlayerCreateCommand implements ICommand {
    private final String name;
    private String username;
    private String pass;
    private String email;

    /**
     * @param name
     * */
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

    /**
     * @param username
     * @param pass
     * @param email
     * */
    public void execute(String username, String pass, String email){
        this.username = username;
        this.pass = pass;
        this.email = email;
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
