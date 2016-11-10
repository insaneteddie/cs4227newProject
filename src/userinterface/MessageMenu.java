/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/MenuUI.java
*   Approximate use: 30%
**/
package userinterface;

import core.interceptor.ConcreteSimpleLoggingRequest;
import core.interceptor.LogDispatcher;
import core.interceptor.LoggingRequest;
import core.interceptor.Log;
import core.command.SessionController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *  UI class for displaying messaging window
 * */
public class MessageMenu extends Menu {


    /** public constructor */
    public MessageMenu() {
        showMessageMenu();

    }

    /** Builds the UI components for the menu */
    public void showMessageMenu() {
        JPanel mainMenuPanel = new JPanel();
        BorderLayout mainMenuLayout = new BorderLayout();
        mainMenuPanel.setLayout(mainMenuLayout);

        JPanel topBarPanel = new JPanel();
        FlowLayout topBarLayout = new FlowLayout();
        topBarLayout.setAlignment(FlowLayout.RIGHT);
        topBarPanel.setLayout(topBarLayout);
        JLabel uiLabel = new JLabel("Messages");
        topBarPanel.add(uiLabel);
        JLabel spacer = new JLabel("          ");
        topBarPanel.add(spacer);
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            SessionController.getInstance().executeCommand("PLAYER_LOG_OUT");
                menuMgr.getMenuFromFactory(1);
        });
        topBarPanel.add(logoutButton);
        mainMenuPanel.add(topBarPanel, BorderLayout.NORTH);

        JPanel centerMenuPanel = new JPanel();
        BorderLayout centerMenuLayout = new BorderLayout();
        centerMenuPanel.setLayout(centerMenuLayout);
        centerMenuPanel.setBackground(Color.blue);

        // list of members and leave party button on top row
        JPanel topCenterMenuPanel = new JPanel();
        FlowLayout topCenterMenuLayout = new FlowLayout();
        topCenterMenuLayout.setAlignment(FlowLayout.LEFT);
        topCenterMenuPanel.setLayout(topCenterMenuLayout);

        JPanel midCenterMenuPanel = new JPanel();
        FlowLayout midCenterMenuLayout = new FlowLayout();
        midCenterMenuLayout.setAlignment(FlowLayout.LEFT);
        midCenterMenuPanel.setLayout(midCenterMenuLayout);
        JTextArea inviteList = new JTextArea();
        JScrollPane inviteScroller = new JScrollPane(inviteList);
        inviteScroller.setPreferredSize(new Dimension(300, 150));
        populateInviteList(inviteList);
        midCenterMenuPanel.add(inviteScroller);
        JPanel inviteOptionPanel = new JPanel();
        GridLayout inviteOptionLayout = new GridLayout(3, 0);
        inviteOptionPanel.setLayout(inviteOptionLayout);
        JButton acceptInviteButton = new JButton("Accept Invite");
        acceptInviteButton.addActionListener(e -> {
                if (sessionInfo.isPlayerInParty()) {
                    JOptionPane.showMessageDialog(null, "To join a new party please leave the party you\nare currently in.", null, JOptionPane.WARNING_MESSAGE);
                } else {
                    try {//first check if already in a party
                        int userid = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the ID of the friend whose"
                                + "\ninvite you would like to accept."));
                        if (sessionInfo.isFriend(userid)) {
                            sessionInfo.addPlayerToParty(sessionInfo.getPartyIDFromSenderInvite(userid));

                            // see user/Player.java line 118
                            SessionController.getInstance().executeCommand(SessionController.PLAYER_INVITES_RETRIEVE);
                            menuMgr.getMenuFromFactory(4);
                        } else {
                            JOptionPane.showMessageDialog(null, "Not a valid friend ID.", null, JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Input invalid. Please enter the ID of a friend.", null, JOptionPane.WARNING_MESSAGE);
                        LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, ex, "Input invalid."));

                    }
                }
        });
        inviteOptionPanel.add(acceptInviteButton);
        JButton declineInviteButton = new JButton("Decline Invite");
        declineInviteButton.addActionListener(e ->{
                try {
                    int userid = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the ID of the friend whose"
                            + "\ninvite you would like to decline."));
                    if (sessionInfo.isFriend(userid)) {

                        // see user/Player.java line 118
                        SessionController.getInstance().executeCommand("PLAYER_INVITES_RETRIEVE");
                        menuMgr.getMenuFromFactory(4);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Not a valid friend ID.", null, JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Input invalid. Please enter the ID of a friend.", null, JOptionPane.WARNING_MESSAGE);

                    LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, ex, "Input invalid."));
                }
        });
        inviteOptionPanel.add(declineInviteButton);
         JButton refreshInviteButton = new JButton("Refresh Invites");
        refreshInviteButton.addActionListener(e -> {
                SessionController.getInstance().executeCommand("PLAYER_INVITES_RETRIEVE");
                populateInviteList(inviteList);
        });
        
        inviteOptionPanel.add(refreshInviteButton);
        midCenterMenuPanel.add(inviteOptionPanel);
        centerMenuPanel.add(midCenterMenuPanel, BorderLayout.CENTER);

        mainMenuPanel.add(centerMenuPanel, BorderLayout.CENTER);

        JPanel bottomBarPanel = new JPanel();
        FlowLayout bottomBarLayout = new FlowLayout();
        bottomBarLayout.setAlignment(FlowLayout.LEFT);
        bottomBarPanel.setLayout(bottomBarLayout);
        JButton returnButton = new JButton("<-Return");
        returnButton.addActionListener(e -> {
                menuMgr.getMenuFromFactory(2);
        });
        bottomBarPanel.add(returnButton);
        mainMenuPanel.add(bottomBarPanel, BorderLayout.SOUTH);
        panel = mainMenuPanel;
    }

    @Override
    public JPanel getMenuPanel() {
        return panel;
    }

    @Override
    public void setMenuManager(MenuManager menuMgr) {
        this.menuMgr = menuMgr;
    }

    private void populateInviteList(JTextArea inviteList) {
        inviteList.setText("");
        inviteList.append("Party Invitations:\n");
        if (sessionInfo != null) {
            List<String> invitations = sessionInfo.getInviteMessages(); // get invite messages
            if (!invitations.isEmpty()) {
                inviteList.append(invitations.get(0) + "\n");
                for (int i = 1; i < invitations.size(); i++) {
                    inviteList.append("" + invitations.get(i) + "\n");
                }
            }
        }
    }
}
