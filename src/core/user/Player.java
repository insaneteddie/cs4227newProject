/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/Player.java
*   Approximate use: 20%
**/
package core.user;

import core.session.SessionObserver;
import message.*;

import java.util.ArrayList;
import java.util.List;

public class Player extends User implements SessionSubject {

    private static Player player = null;
    ArrayList<Integer> friends;
    ArrayList<Integer> partyInformation;
    ArrayList<SessionObserver> observers;
    Collection inviteCollection;
    private String email;

    private Player() {
        observers = new ArrayList<>();
        friends = new ArrayList<>();
        partyInformation = new ArrayList<>();
        inviteCollection = new MessageCollection();
    }

    public static Player getInstance() {
        if (player == null) {
            player = new Player();
        }
        return player;
    }

    @Override
    public void attach(SessionObserver o) {
        observers.add(o);
    }

    @Override
    public void detach(SessionObserver o) {
        if (observers.contains(o)) {
            observers.remove(o);
        }
    }

    @Override
    public void update() {
        if (!observers.isEmpty()) {
            for (SessionObserver observer : observers) {
                observer.update(this);
            }
        }
    }

    public void resetValues() {
        id = 0;
        name = "";
        email = "";
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addFriend(int friendID) {
        friends.add(friendID);
    }

    public void addInvite(Message invite) {
        inviteCollection.add(invite);
    }

    public void removeInvite(int senderID, int partyID) {
        inviteCollection.remove(senderID, partyID);
    }

    public List<Invite> getInvites() {
        return inviteCollection.getAll();
    }
    @Override
    public ArrayList<Integer> getState() {
        return partyInformation;
    }

    public void addToPartyInformation(int info)
    {
        partyInformation.add(info);
    }
    
    public void updatePartyInformation(ArrayList<Integer> info) {
        partyInformation = info;
    }

    public void clearPartyInformation() {
        partyInformation.clear();
    }

    public boolean isFriend(int id) {
        return friends.contains(id);
    }
}
