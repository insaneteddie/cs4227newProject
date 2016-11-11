/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/MenuUI.java
**/
package userinterface;

import core.command.SessionController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainMenu extends Menu {
    private static final String Comp = "Component Not Integrated yet";
    public MainMenu() {
        showMainMenu();
    }

    public void showMainMenu() {
        JPanel mainMenuPanel = new JPanel();
        BorderLayout mainMenuLayout = new BorderLayout();
        mainMenuPanel.setLayout(mainMenuLayout);

        JPanel topBarPanel = new JPanel();
        FlowLayout topBarLayout = new FlowLayout();
        topBarLayout.setAlignment(FlowLayout.RIGHT);
        topBarPanel.setLayout(topBarLayout);
        JLabel welcomeLabel = new JLabel("Welcome ");
        topBarPanel.add(welcomeLabel);
        JLabel spacer = new JLabel("          ");
        topBarPanel.add(spacer);
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {

                SessionController.getInstance().executeCommand(SessionController.PLAYER_LOG_OUT);
                menuMgr.getMenuFromFactory(1);
        });
        topBarPanel.add(logoutButton);
        mainMenuPanel.add(topBarPanel, BorderLayout.NORTH);

        JPanel centerMenuPanel = new JPanel();
        BorderLayout centerMenuLayout = new BorderLayout();
        centerMenuPanel.setLayout(centerMenuLayout);
        centerMenuPanel.setBackground(Color.blue);

        JPanel centerMenuButtonsPanel = new JPanel();
        GridLayout centerMenuButtonsLayout = new GridLayout(3, 3);
        centerMenuButtonsPanel.setLayout(centerMenuButtonsLayout);
        JButton gameButton = new JButton("Games");
        gameButton.addActionListener(e -> JOptionPane.showMessageDialog(null, Comp));
        centerMenuButtonsPanel.add(gameButton);
        JButton profileButton = new JButton("Profile");
        profileButton.addActionListener(e -> JOptionPane.showMessageDialog(null, Comp));
        centerMenuButtonsPanel.add(profileButton);
        JButton friendsButton = new JButton("Friends List");
        friendsButton.addActionListener(e -> JOptionPane.showMessageDialog(null, Comp));
        centerMenuButtonsPanel.add(friendsButton);
        JButton communityButton = new JButton("Communities");
        communityButton.addActionListener(e -> JOptionPane.showMessageDialog(null,  Comp));
        centerMenuButtonsPanel.add(communityButton);
        JButton partyButton = new JButton("Party");
        partyButton.addActionListener(e -> {
                if (sessionInfo.isPlayerInParty()) {
                    SessionController.getInstance().executeCommand(SessionController.PARTY_DETAILS_RETRIEVE);
                    menuMgr.getMenuFromFactory(3);
                } else {
                    int choice = JOptionPane.showConfirmDialog(null, "You are Currently not a member of a party.\nWould you like to create a new party?", "Create a Party", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        SessionController.getInstance().executeCommand(SessionController.PARTY_CREATE);
                    }
                }
        });
        centerMenuButtonsPanel.add(partyButton);
        centerMenuButtonsPanel.add(communityButton);
        JButton messageButton = new JButton("Messages");
        messageButton.addActionListener(e ->{
                SessionController.getInstance().executeCommand(SessionController.PARTY_INVITES_RETRIEVE);
                menuMgr.getMenuFromFactory(4);
        });
        centerMenuButtonsPanel.add(messageButton);
        centerMenuPanel.add(centerMenuButtonsPanel, BorderLayout.CENTER);
        centerMenuPanel.add(centerMenuButtonsPanel, BorderLayout.CENTER);

        mainMenuPanel.add(centerMenuPanel, BorderLayout.CENTER);
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
}
