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
import java.sql.SQLException;
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
    public DatabaseAccess() throws Exception {
        logger = new Log(getClass().getName());
    }

    /**
     * @param username
     * @param password
     * @return
     * */
    @Override
    public boolean canLogin(String username, String password) throws SQLException {
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
     * @param username
     * @return
     * */
    @Override
    public String getPlayerDetails(String username) throws SQLException {
        try {
            file = new File(USER_DETAILS_FILE);
            fileReader = new Scanner(file);
            StringBuilder playerDetails = new StringBuilder();
            while (fileReader.hasNextLine()) {
                String[] lineFromFile = (fileReader.nextLine()).split(",");
                if (lineFromFile[1].equals(username)) {
                    playerDetails.append(lineFromFile[0] + ",");
                    playerDetails.append(lineFromFile[1] + ",");
                    playerDetails.append(lineFromFile[3] + ",");
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
     * @param playerID
     * @return
     * */
    @Override
    public List<Integer> getPlayerFriendList(int playerID) throws SQLException {
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
        return null;
    }

    /**
     * @param playerID
     * @return
     * */
    @Override
    public List<Integer[]> getPlayerInvites(int playerID) throws SQLException {
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
        return null;
    }

    /**
     * @param username
     * @param password
     * @param email
     * */
    @Override
    public void createPlayer(String username, String password, String email) throws SQLException {
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
     * @param partyLeaderID
     * @return
     * */
    @Override
    public int createParty(int partyLeaderID) throws SQLException {
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
     * @param partyID
     * @param playerID
     * @return
     * */
    @Override
    public List<Integer> getPartyDetails(int partyID, int playerID) throws SQLException {
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
        return null;
    }

    /**
     * @param partyID
     * @return
     * */
    @Override
    public boolean isPartyFull(int partyID) throws SQLException {
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
     * @param partyID
     * @param playerID
     * */
    @Override
    public void addPlayerToParty(int playerID, int partyID) throws SQLException {
        file = new File(PARTY_DETAILS_FILE);
        List<String> parties = fileToList();
    
        for (int i = 0; i < parties.size(); i++) {
            String[] lineFromFile = parties.get(i).split(",");
            if (partyID == Integer.parseInt(lineFromFile[0])) {
                StringBuilder amendedParty = new StringBuilder(parties.get(i));
                amendedParty.append("," + playerID);
                parties.remove(i);
                parties.add(i, amendedParty.toString());
                break;
            }
        }
        writeToFile(parties, false);
        file = null;
    }

    /**
     * @param partyID
     * @param playerID
     * */
    @Override
    public void removePlayerFromParty(int partyID, int playerID) throws SQLException {
        file = new File(PARTY_DETAILS_FILE);
        List<String> parties = fileToList();

        for (int i = 0; i < parties.size(); i++) {
            StringBuilder amendedParty = new StringBuilder();
            String[] partyInfo = (parties.get(i)).split(",");
            if (partyID == Integer.parseInt(partyInfo[0])) {
                amendedParty.append(partyInfo[0]);
                for (int j = 1; j < partyInfo.length; j++) {
                    if (playerID != Integer.parseInt(partyInfo[j])) {
                        amendedParty.append("," + partyInfo[j]);
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
     * @param username
     * @param email
     * @return
     * */
    @Override
    public int checkUserNameAndEmail(String username, String email) throws SQLException {
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
     * @param partyID
     * @return
     * */
    @Override
    public boolean doesPartyExist(int partyID) throws SQLException {
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
     * @param username
     * @return
     * */
    @Override
    public int doesPlayerExist(String username) throws SQLException {
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
     * @param playerID
     * @return
     * */
    @Override
    public boolean isPlayerInParty(int playerID) throws SQLException {
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
     * @param senderID
     * @param receiverID
     * @param partyID
     * */
    @Override  //,String content, int type
    public void addInvite(int senderID, int receiverID, int partyID) throws SQLException {
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
     * @param senderID
     * @param receiverID
     * @param partyID
     * */
    @Override
    public void removeInvite(int senderID, int receiverID, int partyID) throws SQLException {
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
     * @param userId
     * @param friendId
     * */
    @Override
    public void addFriend(int userId, int friendId) {
        /** insert implementation here*/
    }

    /**
     * @return
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
     * @param list
     * @param amended
     * */
    private void writeToFile(List<String> list, boolean amended) {
        try {
            fWriter = new FileWriter(file, amended);
            pWriter = new PrintWriter(fWriter);
            for (int i = 0; i < list.size(); i++) {
                String aList = list.get(i);
                pWriter.println(aList);
            }

            pWriter.close();
            fWriter.close();
        } catch (Exception e) {
            logger.logWarning(e);
        }
    }
}
