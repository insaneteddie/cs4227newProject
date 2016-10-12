package driver;

import userinterface.StartUpUI;

/**
 *  Class containing main method
 * */
public class AppDriver {

    /**
     * Private constructor to hide implicit public constructor
     * */
    private AppDriver(){}

    /**
     *  Launches program
     *  @param args
     * */
    public static void main(String[] args)
    {
        /* Main: Boots User Interface */
        StartUpUI startUp = new StartUpUI();
        startUp.run();
    }
}
