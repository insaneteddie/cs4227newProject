/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/BootUI.java
**/
package userinterface;

import core.interceptor.ConcreteSimpleLoggingRequest;
import core.interceptor.LogDispatcher;
import core.interceptor.LoggingRequest;
import core.interceptor.Log;
import core.session.SessionInformation;
/** class to launch gui */
public class StartUpUI {
    /** public constructor */
    public StartUpUI() {}

    /** Handles initial set up*/
    public void run() {
        try {
            /* Creates window to display GUI components */
            /* Observer to PanelManager */
            Screen screen = new Screen();
            /* Starts database connection */
            /* Will process input taken from client GUI */
            SessionInformation sessionInfo = SessionInformation.getInstance();
            /* Panel factory to display panels on window */
            MenuFactory menuFac = new MenuFactory(sessionInfo);
            /* Subject in observer pattern */
            MenuManager menuMgr = new MenuManager(menuFac);
            menuMgr.registerObserver(screen);
        } catch (Exception e) {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.SEVERE, e, ""));
        }
    }
}
