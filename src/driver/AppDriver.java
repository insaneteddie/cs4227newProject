package driver;

import userinterface.StartUpUI;


public class AppDriver {

    /**
     * Private constructor to hide implicit public constructor
     * */
    private AppDriver(){}

    /**
     *  Launches program
     * */
    public static void main(String[] args)
    {
        /* Main: Boots User Interface */
        StartUpUI startUp = new StartUpUI();
        startUp.run();
    }
}
