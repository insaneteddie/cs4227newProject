/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/DatabaseAccess.java
*   Approximate use: 5% 
**/
package database;

import core.utils.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** concrete database class*/
public class DatabaseAccess implements DatabaseInterface {

    private PrintWriter pWriter;
    private FileWriter fWriter;
    private File file;
    private Scanner fileReader;
    private static final String USER_DETAILS_FILE = "Resources/UserDetails.txt";
    private static final String FRIEND_DETAILS_FILE = "Resources/FriendDetails.txt";
    private static final String PARTY_DETAILS_FILE = "Resources/PartyDetails.txt";
    private static final String INVITE_DETAILS_FILE = "Resources/InviteDetails.txt";

    private Log logger;

    /** public constructor */
    public DatabaseAccess()
    {
        logger = new Log(getClass().getName());
    }

    /**
     * @param username string username to check
     * @param password string pass to check
     * @return boolean true/false if possible
     * */
    @Override
    public boolean canLogin(String username, String password) {
        try {
            file = new File(USER_DETAILS_FILE);
            if (file.exists()) {
                fileReader = new Scanner(file);
                while (fileReader.hasNextLine()) {
                    String[] lineFromFile = (fileReader.nextLine()).split(",");
                    if (lineFromFile[2].equals(password)) {
                        fileReader.close();
                        file = null;
                        return true;
                    }
                }
            }

            fileReader.close();
            file = null;
            return false;
        }catch (FileNotFoundException e){
            logger.logWarning(e);
        }
        return false;
    }

    /**
     * @param username string username to get details
     * @return comma separated string with userId,userEmail,userBio
     * */
    @Override
    public String getPlayerDetails(String username) {
        try {
            file = new File(USER_DETAILS_FILE);
            fileReader = new Scanner(file);
            StringBuilder playerDetails = new StringBuilder();
            while (fileReader.hasNextLine()) {
                String[] lineFromFile = (fileReader.nextLine()).split(",");
                if (lineFromFile[1].equals(username)) {
                    playerDetails.append(lineFromFile[0]).append(",");
                    playerDetails.append(lineFromFile[1]).append(",");
                    playerDetails.append(lineFromFile[3]).append(",");
                }
            }
            fileReader.close();
            file = null;
            return playerDetails.toString();
        }catch(FileNotFoundException e){
            logger.logWarning(e);
        }
        return null;
    }

    /**
     * @param playerID int playerID of user to returns friends
     * @return Integer list of friend id's
     * */
    @Override
    public List<Integer> getPlayerFriendList(int playerID) {
        try {
            String pidStr = Integer.toString(playerID);
            List<Integer> friendIDs = new ArrayList<>();
            file = new File(FRIEND_DETAILS_FILE);
            fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                String[] lineFromFile = (fileReader.nextLine()).split(",");
                if (pidStr.equals(lineFromFile[0])) {
                    friendIDs.add(Integer.parseInt(lineFromFile[1]));
                } else if (pidStr.equals(lineFromFile[1])) {
                    friendIDs.add(Integer.parseInt(lineFromFile[0]));
                }
            }
            fileReader.close();
            file = null;

            return friendIDs;
        }catch (FileNotFoundException e){
            logger.logWarning(e);
        }
        return new ArrayList<>();
    }

    /**
     * @param playerID user id to get invites of
     * @return Integer list of invite id's
     * */
    // should return invite objects - Christian 3/11/16
    @Override
    public List<Integer[]> getPlayerInvites(int playerID) {
        try {
            ArrayList<Integer[]> idList = new ArrayList<>();
            Integer[] ids = new Integer[2];
            file = new File(INVITE_DETAILS_FILE);
            fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                String[] lineFromFile = (fileReader.nextLine()).split(",");
                if (playerID == Integer.parseInt(lineFromFile[1])) {
                    ids[0] = Integer.parseInt(lineFromFile[0]);
                    ids[1] = Integer.parseInt(lineFromFile[2]);
                    if (!idList.contains(ids)) {
                        idList.add(ids);
                    }
                }
            }
            fileReader.close();
            file = null;
            return idList;
        }catch (FileNotFoundException e){
            logger.logWarning(e);
        }
        return new ArrayList<>();
    }

    /**
     * @param username string username
     * @param password string pass
     * @param email string email address
     * */
    @Override
    public void createPlayer(String username, String password, String email) {
        file = new File(USER_DETAILS_FILE);
        List<String> userDetails = fileToList();
        String playerDetails = "";

        if (!userDetails.isEmpty()) {
            String[] lastEntry = (userDetails.get(userDetails.size() - 1)).split(",");
            int newPlayerID = Integer.parseInt(lastEntry[0]) + 1;
            playerDetails = newPlayerID + "," + username + "," + password + "," + email;
        } else {
            playerDetails += 1 + "," + username + "," + password + "," + email;
        }
        try {
            fWriter = new FileWriter(file, true);
            pWriter = new PrintWriter(fWriter);
            pWriter.println(playerDetails);
            pWriter.close();
            fWriter.close();
        }catch (IOException e){
            logger.logWarning(e);
        }
        file = null;
    }

    /**
     * @param partyLeaderID int userId to make leader in party creation
     * @return int partyId of created party
     * */
    @Override
    public int createParty(int partyLeaderID) {
        file = new File(PARTY_DETAILS_FILE);
        List<String> parties = fileToList();
        String newParty = "";
        int newPartyID = 1;
        if(!parties.isEmpty()){
		String [] lastEntry = (parties.get(parties.size() - 1)).split(",");
		newPartyID = Integer.parseInt(lastEntry[0]) + 1;
		newParty += newPartyID + "," + partyLeaderID;
                }
                else{
                    newParty += 1 + "," +  partyLeaderID;
                }
        try {
            fWriter = new FileWriter(file, true);
            pWriter = new PrintWriter(fWriter);
            pWriter.println(newParty);
            pWriter.close();
            fWriter.close();
        }catch(IOException e){
            logger.logWarning(e);
        }
        file = null;

        return newPartyID;
    }

    /**
     * @param partyID partyID of party to get details
     * @param playerID userId of a party member to narrow search
     * @return Integer List of party member id's
     * */
    @Override
    public List<Integer> getPartyDetails(int partyID, int playerID){
        try {
            file = new File(PARTY_DETAILS_FILE);
            fileReader = new Scanner(file);
            ArrayList<Integer> partyDetails = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                String[] lineFromFile = fileReader.nextLine().split(",");
                if (partyID == Integer.parseInt(lineFromFile[0])) {
                    for (String x : lineFromFile) {
                        partyDetails.add(Integer.parseInt(x));
                    }
                }
            }
            fileReader.close();
            file = null;
            if (!partyDetails.contains(playerID)) {
                partyDetails.clear();
            }
            return partyDetails;
        }catch(FileNotFoundException e){
            logger.logWarning(e);
        }
        return new ArrayList<>();
    }

    /**
     * @param partyID int partyID to check
     * @return boolean if full or not
     * */
    @Override
    public boolean isPartyFull(int partyID) {
        try {
            boolean partyFull = true;
            file = new File(PARTY_DETAILS_FILE);
            fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                String[] lineFromFile = fileReader.nextLine().split(",");
                if (partyID == Integer.parseInt(lineFromFile[0]) && lineFromFile.length < 6) {
                    partyFull = false;
                    break;
                }
            }
            fileReader.close();
            file = null;
            return partyFull;
        }catch(FileNotFoundException e){
            logger.logWarning(e);
        }
        return true;
    }

    /**
     * @param partyID partyId to add user to
     * @param playerID user to add to party
     * */
    @Override
    public void addPlayerToParty(int playerID, int partyID) {
        file = new File(PARTY_DETAILS_FILE);
        List<String> parties = fileToList();
    
        for (int i = 0; i < parties.size(); i++) {
            String[] lineFromFile = parties.get(i).split(",");
            if (partyID == Integer.parseInt(lineFromFile[0])) {
                parties.remove(i);
                parties.add(i, parties.get(i) + "," + playerID);
                break;
            }
        }
        writeToFile(parties, false);
        file = null;
    }

    /**
     * @param partyID partyId to remove user from
     * @param playerID user to remove from party
     * */
    @Override
    public void removePlayerFromParty(int partyID, int playerID) {
        file = new File(PARTY_DETAILS_FILE);
        List<String> parties = fileToList();

        for (int i = 0; i < parties.size(); i++) {
            StringBuilder amendedParty = new StringBuilder();
            String[] partyInfo = (parties.get(i)).split(",");
            if (partyID == Integer.parseInt(partyInfo[0])) {
                amendedParty.append(partyInfo[0]);
                for (int j = 1; j < partyInfo.length; j++) {
                    if (playerID != Integer.parseInt(partyInfo[j])) {
                        amendedParty.append(",").append(partyInfo[j]);
                    }
                }
                parties.remove(i);
                if (amendedParty.toString().contains(",")) {
                    parties.add(i, amendedParty.toString());
                }
                break;
            }
        }
        writeToFile(parties, false);
        file = null;
    }

    /**
     * @param username username to check
     * @param email string email to check
     * @return return int 0/1 if successful
     * */
    @Override
    public int checkUserNameAndEmail(String username, String email) {
        try {
            file = new File(USER_DETAILS_FILE);
            fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                String[] lineFromFile = (fileReader.nextLine()).split(",");
                if (lineFromFile[1].equals(username)) {
                    fileReader.close();
                    file = null;
                    return 0;
                } else if (lineFromFile[3].equals(email)) {
                    fileReader.close();
                    file = null;
                    return 1;
                }
            }
            fileReader.close();
            file = null;
            return 2;
        }catch (FileNotFoundException e){
            logger.logWarning(e);
        }
        return 2;
    }

    /**
     * @param partyID partyId to check exists
     * @return boolean if it does/doesn't
     * */
    @Override
    public boolean doesPartyExist(int partyID) {
        file = new File(PARTY_DETAILS_FILE);
        try {
            fileReader = new Scanner(file);
            boolean partyExists = false;
            while (fileReader.hasNextLine()) {
                String[] lineFromFile = fileReader.nextLine().split(",");
                if (partyID == Integer.parseInt(lineFromFile[0])) {
                    partyExists = true;
                }
            }
            fileReader.close();
            file = null;
            return partyExists;
        }catch(FileNotFoundException e){
            logger.logWarning(e);
        }
        return false;
    }

    /**
     * @param username string username to check exists
     * @return boolean if does/doesn't
     * */
    @Override
    public int doesPlayerExist(String username) {
        try {
            file = new File(USER_DETAILS_FILE);
            fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                String[] lineFromFile = fileReader.nextLine().split(",");
                if (username.equals(lineFromFile[1])) {
                    fileReader.close();
                    file = null;
                    return Integer.parseInt(lineFromFile[0]);
                }
            }
            fileReader.close();
            file = null;
            return 0;
        }catch(FileNotFoundException e){
            logger.logWarning(e);
        }
        return 0;
    }

    /**
     * @param playerID user_Id to check if in party
     * @return boolean if in party/not in party
     * */
    @Override
    public boolean isPlayerInParty(int playerID) {
        try {
            boolean isInParty = false;
            file = new File(PARTY_DETAILS_FILE);
            fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                String[] lineFromFile = fileReader.nextLine().split(",");
                for (int i = 1; i < lineFromFile.length; i++) {
                    if (playerID == Integer.parseInt(lineFromFile[i])) {
                        isInParty = true;
                        break;
                    }
                }
            }
            fileReader.close();
            file = null;

            return isInParty;
        }catch (FileNotFoundException e){
            logger.logWarning(e);
        }
        return false;
    }

    /**
     * @param senderID int senders id
     * @param receiverID int receiver id
     * @param partyID int party id if party invite
     * */
    @Override  //,String content, int type
    public void addInvite(int senderID, int receiverID, int partyID) {
        try {
            file = new File(INVITE_DETAILS_FILE);
            String newInvite = Integer.toString(senderID) + "," + receiverID + "," + partyID;

            fWriter = new FileWriter(file, true);
            pWriter = new PrintWriter(fWriter);
            pWriter.println(newInvite);
            pWriter.close();
            fWriter.close();
            file = null;
        }catch (IOException e){
            logger.logWarning(e);
        }
    }

    /**
     * @param senderID int senders id
     * @param receiverID int receiver id
     * @param partyID int party id to remove invite from if is one
     * */
    @Override
    public void removeInvite(int senderID, int receiverID, int partyID) {
        file = new File(INVITE_DETAILS_FILE);
        List<String> invites = fileToList();
    
        for (int i = 0; i < invites.size(); i++) {
            String[] lineFromFile = invites.get(i).split(",");
            if (senderID == Integer.parseInt(lineFromFile[0]) && receiverID == Integer.parseInt(lineFromFile[1])
                    && partyID == Integer.parseInt(lineFromFile[2])) {
                invites.remove(i);
                break;
            }
        }
        writeToFile(invites, false);
        file = null;
    }

    /**
     * @param userId int user to add friend
     * @param friendId int user to add as friend
     * */
    @Override
    public void addFriend(int userId, int friendId) {
        /* insert implementation here*/
    }

    /**
     * @return list of string after reading from file
     * */
    private List<String> fileToList() {
        List<String> list = new ArrayList<>();
        try {
            fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                list.add(fileReader.nextLine());
            }
            fileReader.close();
        } catch (Exception e) {
            logger.logWarning(e);
        }
        return list;
    }

    /**
     * @param list writes string list to file
     * @param amended boolean if amending or not
     * */
    private void writeToFile(List<String> list, boolean amended) {
        try {
            fWriter = new FileWriter(file, amended);
            pWriter = new PrintWriter(fWriter);
            for (String aList : list) {
                pWriter.println(aList);
            }

            pWriter.close();
            fWriter.close();
        } catch (Exception e) {
            logger.logWarning(e);
        }
    }
}
