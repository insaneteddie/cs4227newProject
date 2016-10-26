/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/MenuUI.java
*   Approximate use: 30%
**/
package userinterface;

import core.utils.SessionController;

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
 *  UI class for party menu
 * */
public class PartyMenu extends Menu {

    public PartyMenu() {
        showPartyMenu();
    }

    public void showPartyMenu() {
        JPanel mainMenuPanel = new JPanel();
        BorderLayout mainMenuLayout = new BorderLayout();
        mainMenuPanel.setLayout(mainMenuLayout);

        JPanel topBarPanel = new JPanel();
        FlowLayout topBarLayout = new FlowLayout();
        topBarLayout.setAlignment(FlowLayout.RIGHT);
        topBarPanel.setLayout(topBarLayout);
        JLabel uiLabel = new JLabel("Party Menu");
        topBarPanel.add(uiLabel);
        JLabel spacer = new JLabel("          ");
        topBarPanel.add(spacer);
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
                //sessionInfo.logPlayerOut();
                SessionController.getInstance().executeCommand("PLAYER_LOG_OUT");
                menuMgr.getMenuFromFactory(1);
        });
        topBarPanel.add(logoutButton);
        mainMenuPanel.add(topBarPanel, mainMenuLayout.NORTH);

        JPanel centerMenuPanel = new JPanel();
        BorderLayout centerMenuLayout = new BorderLayout();
        centerMenuPanel.setLayout(centerMenuLayout);
        centerMenuPanel.setBackground(Color.blue);

        // list of members and leave party button on top row
        JPanel topCenterMenuPanel = new JPanel();
        FlowLayout topCenterMenuLayout = new FlowLayout();
        topCenterMenuLayout.setAlignment(FlowLayout.LEFT);
        topCenterMenuPanel.setLayout(topCenterMenuLayout);

        // populate list with party members
        JTextArea memberList = new JTextArea();
        JScrollPane listScroller = new JScrollPane(memberList);
        listScroller.setPreferredSize(new Dimension(300, 150));
        topCenterMenuPanel.add(listScroller);
        // populate list
        populateMembersList(memberList);

        JButton leavePartyButton = new JButton("Leave Party");
        leavePartyButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "You have left the party");
                //sessionInfo.leaveParty();
                SessionController.getInstance().executeCommand("PARTY_LEAVE");
                menuMgr.getMenuFromFactory(2);
        });
        topCenterMenuPanel.add(leavePartyButton);
        centerMenuPanel.add(topCenterMenuPanel, centerMenuLayout.NORTH);

        JPanel centerMenuButtonsPanel = new JPanel();
        GridLayout centerMenuButtonsLayout = new GridLayout(4, 1);
        centerMenuButtonsPanel.setLayout(centerMenuButtonsLayout);
        JButton refreshButton = new JButton("Refresh Members List");
        refreshButton.addActionListener(e -> {
                //sessionInfo.getPartyDetails();
                SessionController.getInstance().executeCommand("PARTY_DETAILS_RETRIEVE");
                if (sessionInfo.isPlayerInParty()) {
                    populateMembersList(memberList);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "You have been removed from the party.\nReturning to main menu", null, JOptionPane.WARNING_MESSAGE);
                    menuMgr.getMenuFromFactory(2);
                }
        });
        centerMenuButtonsPanel.add(refreshButton);
        JButton inviteButton = new JButton("Invite Friend");
        inviteButton.addActionListener(e -> {

                try {
                    int friendToInvite = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the ID of the friend you would"
                            + "\nlike to invite to join the party"));
                    if (sessionInfo.isFriend(friendToInvite)) {
                        sessionInfo.sendInvite(friendToInvite);
                    } else {
                        JOptionPane.showMessageDialog(null, "Not a valid friend ID.", null, JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Input invalid. Please enter the ID of a friend.", null, JOptionPane.WARNING_MESSAGE);
                }

        });
        centerMenuButtonsPanel.add(inviteButton);
        JButton removeMemberButton = new JButton("Remove Member");
        removeMemberButton.addActionListener(e -> {
                if (sessionInfo.isPartyLeader()) {
                    if (sessionInfo.getPartySize() > 1) {
                        // select member to remove
                        boolean validID = false;

                        try {
                            int memberToRemove = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the ID of the member you would"
                                    + "\nlike to remove from the party"));
                            if(memberToRemove == sessionInfo.getPlayerId())
                            {
                                JOptionPane.showMessageDialog(null, "To leave party, please select the 'Leave Party' button\nor log out.", null, JOptionPane.WARNING_MESSAGE);
                            }
                            else if (sessionInfo.isMemberOfParty(memberToRemove)) {

                                sessionInfo.removePlayerFromParty(memberToRemove);
                                //sessionInfo.getPartyDetails();
                                SessionController.getInstance().executeCommand("PARTY_DETAILS_RETRIEVE");
                                menuMgr.getMenuFromFactory(3);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Input must be an integer number", null, JOptionPane.WARNING_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "You are the only member in the party.\n"
                                + "To leave the party click the 'Leave Party' button or\nlog out.", null, JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You must be Party Leader to remove members", null, JOptionPane.WARNING_MESSAGE);
                }
        });
        centerMenuButtonsPanel.add(removeMemberButton);
        JButton gameButton = new JButton("Games");
        gameButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Component not integrated"));
        centerMenuButtonsPanel.add(gameButton);
        centerMenuPanel.add(centerMenuButtonsPanel, centerMenuLayout.CENTER);

        mainMenuPanel.add(centerMenuPanel, mainMenuLayout.CENTER);

        JPanel bottomBarPanel = new JPanel();
        FlowLayout bottomBarLayout = new FlowLayout();
        bottomBarLayout.setAlignment(FlowLayout.LEFT);
        bottomBarPanel.setLayout(bottomBarLayout);
        JButton returnButton = new JButton("<-Return");
        returnButton.addActionListener(e -> menuMgr.getMenuFromFactory(2));
        bottomBarPanel.add(returnButton);
        mainMenuPanel.add(bottomBarPanel, mainMenuLayout.SOUTH);
        panel = mainMenuPanel;

    }

    public void populateMembersList(JTextArea memberList) {
        memberList.setText("");
        memberList.append("Members:\n");
        if (sessionInfo != null) {
            List<Integer> members = sessionInfo.getPartyMembers();

            memberList.append("" + members.get(0) + " (host)\n");
            for (int i = 1; i < members.size(); i++) {
                memberList.append("" + members.get(i) + "\n");
            }
        }
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
