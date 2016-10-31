package core.command;

/**
 * Created by David on 22/10/2016.
 */
public interface ICommand {

    /** abstract method to be overriden by concrete command class to execute specific commands*/
    void execute();

    /**
     * @return
     * */
    String getCommandName();
}
