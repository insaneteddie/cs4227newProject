package database;

import core.interceptor.ConcreteSimpleLoggingRequest;
import core.interceptor.LogDispatcher;
import core.interceptor.LoggingRequest;


import java.util.ArrayList;
import java.sql.*;
import java.util.List;


/**
 * Created by s_harte Awesome Gaming  : CS4227 Project on 10/20/2016.
 */
 class SqlDatabase implements SqlDatabaseInterface {
    // JDBC driver name and database URL
    private String jdbcDriver;
    //set up to connect to an rds instance on my aws account
    private String dbUrl;
    private PreparedStatement prepStatement;
    //  Database credentials
    private String user;
    private String pass;
    private static final  String LOG_CHECK = "Checking player details";
    /** no-args constructor */
    SqlDatabase()
    {
        setUpDatabaseDetails();
    }
    /**
     * @param databaseURL Url of database to connect to
     * @param dbUser username for database
     * @param dbPass pass for database
     * @param jdbcDriver jdbc driver
     * */
    SqlDatabase(String databaseURL, String dbUser, String dbPass, String jdbcDriver)
    {
        setUpDatabaseDetails(databaseURL, dbUser, dbPass, jdbcDriver);
    }
    /**
     *
     * Called by constructor to set up details
     */
    @Override
    public void setUpDatabaseDetails()
    {
        this.jdbcDriver = "com.mysql.jdbc.Driver";
        this.dbUrl = "jdbc:mysql://cs4227dbserver.cx7qikfelfcm.eu-west-1.rds.amazonaws.com:3306/awesome_gaming";
        this.user = "admin";
        this.pass = "teamawesome";
    }
    /**
     * Calls by overloaded constructor to set up details from ones given
     */
    @Override
    public void setUpDatabaseDetails(String databaseURL, String dbUser, String dbPass, String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
        this.dbUrl = databaseURL;
        this.user = dbUser;
        this.pass = dbPass;
    }
        /*table name,columns *string is varchar, int is int.
         users,String user_Name, String user_Pass, String email,String user_Bio.
         user_friends,int user_Id (from users table),int friend_Id from users.
         user_messages.
         user_games.
         user_invites.
         user_parties.*/
    //add a user to the user table
    /**
     * @param userName String username to add
     * @param userPass String pass to add
     * @param email String email address to add to table
     * */
    void addUser(String userName, String userPass, String email)
    {
        Connection connection = openConnection();
        LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "Attempting to Connect"));
        LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "Inserting records into the table...."));
        try {
            prepStatement = connection.prepareStatement("INSERT INTO users ( user_Name, user_Pass, user_Email) VALUES(?,?,?)");
            prepStatement.setString(1,userName);
            prepStatement.setString(2,userPass);
            prepStatement.setString(3,email);
            prepStatement.executeUpdate();
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se)
            {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
    }
    /**
     * @param userId int userID of person who is adding friend
     * @param friendId int friend to be added's id
     * */
    void addFriend(int userId, int friendId)
    {
        Connection connection = openConnection();
        LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "Inserting records into the table..."));
        try {
            prepStatement = connection.prepareStatement("INSERT INTO user_friends ( user_Id, friend_Id) VALUES( ?, ?)");
            prepStatement.setInt(1,userId);
            prepStatement.setInt(2,friendId);
            prepStatement.executeUpdate();
            prepStatement.close();
            prepStatement = connection.prepareStatement("INSERT INTO user_friends ( user_Id, friend_Id) VALUES( ?, ?)");
            prepStatement.setInt(1,friendId);
            prepStatement.setInt(2,userId);
            prepStatement.executeUpdate();
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try
            {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se)
            {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
    }
    /**
     * @param userName String username to check
     * @param userPass String pass to check
     * @return boolean to allow/fail login
     * */
    boolean canLogin(String userName, String userPass)
    {
        boolean canLogin = false;
        Connection connection = openConnection();
        LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "Checking login details"));
        try {
            prepStatement = connection.prepareStatement("SELECT * FROM users WHERE user_Name = ? AND user_Pass = ?");
            prepStatement.setString(1,userName);
            prepStatement.setString(2,userPass);
            ResultSet res = prepStatement.executeQuery();
            if(res.isBeforeFirst())
                canLogin = true;
        } catch (SQLException e) {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        }finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se) {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
        return canLogin;
    }
    /**
     * @param userId userId to get username
     * @return String username of user_Id
     * */
    String getPlayerName(int userId)
    {
        String playerName = "";
        Connection connection = openConnection();
        LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, LOG_CHECK));
        try {
            prepStatement = connection.prepareStatement("SELECT user_Name FROM users WHERE user_Id = ?");
            prepStatement.setInt(1,userId);
            ResultSet res = prepStatement.executeQuery();
            res.next();
            playerName = res.getString("user_Name");
            return playerName;
        } catch (SQLException e) {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se) {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
        return playerName;
    }
    /**
     * @param userName String username to get userID of
     * @return return user's user_ID
     * */
    int getUserId(String userName)
    {
        Connection connection = openConnection();
        int userId = 0;
        try {
            prepStatement = connection.prepareStatement("SELECT user_Id FROM users WHERE user_Name = ?");
            prepStatement.setString(1,userName);
            ResultSet res = prepStatement.executeQuery();
            res.next();
            userId = res.getInt(1);
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se)
            {

                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
        return userId;
    }
    /**
     * @param userName string username to check
     * @param email string email to check
     * @return int 0/1 true/false
     * */
    int checkNameEmail(String userName, String email)
    {
        int aChecker = 0;
        Connection connection = openConnection();
        LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, LOG_CHECK));
        try {
            prepStatement = connection.prepareStatement("SELECT * FROM users WHERE user_Name = ? AND user_Email = ?");
            prepStatement.setString(1,userName);
            prepStatement.setString(2,email);
            ResultSet res = prepStatement.executeQuery();
            if(!res.next())
            {
                aChecker = 1;
                return aChecker;
            }
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        }finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se)
            {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
        return aChecker;
    }
    /**
     * @param partyId int partyId to lookup
     * @return boolean if full or not
     * */
    boolean isPartyFull(int partyId)
    {
        int toCheck = 3;
        Connection connection = openConnection();
        try {
            prepStatement = connection.prepareStatement("SELECT * FROM user_parties WHERE party_ID = ?");
            prepStatement.setInt(1, partyId);
            int resCols = 8;
            ResultSet res = prepStatement.executeQuery();
            res.next();
            while(toCheck < resCols)
            {
                int temp = res.getInt(toCheck);
                if (temp == 0)
                {
                    return false;
                }
                toCheck++;
            }
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se)
            {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
        return true;
    }

    /**
     * @param userId userId to get friend of
     * @return Integer list of friends Id
     * */
    List<Integer> getFriendsList(int userId)
    {
        List<Integer> friendsList = new ArrayList<>();
        Connection connection = openConnection();
        try {
            prepStatement = connection.prepareStatement("SELECT friend_Id FROM user_friends WHERE user_Id = ?");
            prepStatement.setInt(1,userId);
            ResultSet res = prepStatement.executeQuery();
            res.beforeFirst();
            while(res.next())
            {
                friendsList.add(res.getInt("friend_Id"));
            }
            return friendsList;
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se)
            {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
        return friendsList;
    }
    /**
     * @param playerId takes in int player/userId to select from table
     * @return Integer list of friends user_IDs
     * */
    List<Integer []> getInvites(int playerId)
    {
        List<Integer []> invitesList = new ArrayList<>();
        Connection connection = openConnection();
        try {
            Integer [] userInvites = new Integer[100];
            prepStatement = connection.prepareStatement("SELECT party_Id,sender_Id FROM user_invites WHERE user_Id = ?");
            prepStatement.setInt(1,playerId);
            ResultSet res = prepStatement.executeQuery();
            while(res.next())
            {
                userInvites[0] = res.getInt("party_Id");
                userInvites[1] = res.getInt("sender_Id");
                invitesList.add(userInvites);
            }
            return invitesList;
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se)
            {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
        return invitesList;
    }

    /**
     * @param leaderId takes in user_Id and sets it as leader of party
     * @return int of partyId
     * */
    int createParty(int leaderId)
    {
        int partyId;
        Connection connection = openConnection();
        LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "Inserting records into the table..."));
        try {
            prepStatement = connection.prepareStatement("INSERT INTO user_parties (leader_Id) VALUES(?)");
            prepStatement.setInt(1,leaderId);
            prepStatement.executeUpdate();
            prepStatement.close();
            prepStatement = connection.prepareStatement("SELECT party_Id from user_parties where leader_Id = ?");
            prepStatement.setInt(1,leaderId);
            ResultSet res = prepStatement.executeQuery();
            res.next();
            partyId = res.getInt("party_Id");
            return partyId;
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se)
            {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
        return 1;
    }
    /**
     * @param playerID int user_Id to lookup parties table
     * @param partyID int party_Id narrow down lookup criteria
     * */
    void addPlayerToParty(int playerID, int partyID)
    {
        Connection connection = openConnection();
        try {
            int colId = findNullFromParties(partyID);
            int offSet = 1;//to ignore leader in getColName
            String sqlCol = getColName(colId + offSet);
            String query = "update user_parties set " + sqlCol + " = ? where party_Id = ?";
            // have to assign the column name like this because it was putting it into the prep
            // statement with single quotes around it which breaks the syntax
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1,playerID);
            prepStatement.setInt(2,partyID);
            prepStatement.executeUpdate();
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se)
            {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
    }
    /**
     * @param partyID int party to check if exists
     * @return return boolean if so
     * */
    boolean doesPartyExist(int partyID)
    {
        Connection connection = openConnection();
        try {
            prepStatement = connection.prepareStatement("SELECT * FROM user_parties WHERE party_Id = ?");
            prepStatement.setInt(1, partyID);
            ResultSet res = prepStatement.executeQuery();
            if (res.next()) {
                return false;
            }
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se)
            {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
        return true;
    }
    /**
     * @param username username to check
     * @return int 0/1 (true or false)
     * */
    int doesPlayerExist(String username)
    {
        int bChecker = 0;
        LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, LOG_CHECK));
        Connection connection = openConnection();
        try {
            prepStatement = connection.prepareStatement("SELECT * FROM users WHERE user_Name = ?");
            prepStatement.setString(1,username);
            ResultSet res = prepStatement.executeQuery();
            res.next();
            if(!res.next())
            {
                bChecker = 1;
                return bChecker;
            }
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                    prepStatement.close();
                    closeConnection(connection);
            } catch (SQLException se) {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
        return bChecker;
    }
    /**
     * @param partyId int partyId to check
     * @return int null for check
     * */
   private int findNullFromParties(int partyId)
    {
        int counter = 0;
        Connection connection = openConnection();
        try {
            counter = 1;
            int resultCols = 6;
            prepStatement = connection.prepareStatement("SELECT user_1_Id,user_2_Id,user_3_Id,user_4_Id,user_5_Id FROM user_parties WHERE party_ID = ? ");
            prepStatement.setInt(1,partyId);
            ResultSet res = prepStatement.executeQuery();
            int tester;
            res.next();
            while(counter < resultCols)
            {
                tester = res.getInt(counter);
                if(tester == 0)
                    return counter;
                else
                    counter++;
            }
    } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
    } finally {
        try {
            prepStatement.close();
            closeConnection(connection);
        } catch (SQLException se)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
        }
    }
        return counter;
    }
    /**
     * @param playerId int user-ID to check if in party
     * @return boolean returned / if or not
     * */
    boolean isInParty(int playerId)
    {
        Connection connection = openConnection();
        try {
            prepStatement = connection.prepareStatement("SELECT * FROM user_parties where (leader_Id = ? OR user_1_Id = ? OR user_2_Id = ? OR user_3_Id = ? OR user_4_Id = ? OR user_5_Id = ?)");
            prepStatement.setInt(1, playerId);
            prepStatement.setInt(2, playerId);
            prepStatement.setInt(3, playerId);
            prepStatement.setInt(4, playerId);
            prepStatement.setInt(5, playerId);
            prepStatement.setInt(6, playerId);
            ResultSet res = prepStatement.executeQuery();
            if (res.next())
            {
                return true;
            }
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se)
            {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
        return false;
    }
    /**
     * @param senderID int sender_ID to add to table
     * @param receiverID int user_ID to add to table
     * @param partyId int party_Id to add to table
     * */
    void addInvite(int senderID, int receiverID, int partyId)
    {
        Connection connection = openConnection();
        try {
            prepStatement = connection.prepareStatement("INSERT INTO user_invites (sender_Id,invite_content,user_Id,party_Id)  VALUES( ?, ?, ?, ?)");
            prepStatement.setInt(1,senderID);
            prepStatement.setString(2,"this is content");
            prepStatement.setInt(3,receiverID);
            prepStatement.setInt(4,partyId);
            prepStatement.executeUpdate();
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se)
            {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
    }

    /**
     *
     * @param partyID int partyID of party to delete
     */
    private void deleteParty(int partyID)
    {
        Connection connection = openConnection();
        try{
            prepStatement = connection.prepareStatement("DELETE FROM user_parties WHERE party_Id = ?");
            prepStatement.setInt(1, partyID);
            prepStatement.executeUpdate();
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se)
            {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
    }
    /**
     * @param senderID int sender_Id to remove invite from
     * @param receiverID int receiver_ID to remove from table
     * @param partyID int party_id of party to who's invite is to be deletes
     * */
    void removeInvite(int senderID, int receiverID, int partyID)
    {
        Connection connection = openConnection();
        try {
            prepStatement = connection.prepareStatement("DELETE FROM user_invites WHERE sender_Id = ? AND user_Id = ? AND party_Id = ?");
            prepStatement.setInt(1, senderID);
            prepStatement.setInt(2, receiverID);
            prepStatement.setInt(3, partyID);
            prepStatement.executeUpdate();
        } catch (SQLException e)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se)
            {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
    }

    /**
     * @param partyID party_Id to remove from
     * @param playerID player_ID to remove from
     * */
    void removePlayerFromParty(int partyID, int playerID)
    {
        Connection connection = openConnection();
        try {
            int colId = findUserInParty(playerID,partyID);
            String sqlCol = getColName(colId);
            if("leader_Id".equals(sqlCol))
            {
                deleteParty(partyID);
            }//to sanitize the colName
            else {
                StringBuilder sqB = new StringBuilder("Update user_parties SET ");
                sqB.append(sqlCol);
                sqB.append(" = 0 where party_Id = ?");
                String sqlQuery = sqB.toString();
                prepStatement = connection.prepareStatement(sqlQuery);
                prepStatement.setInt(1, partyID);
                prepStatement.executeUpdate();
            }
        } catch (SQLException se) {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException seF) {

                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, seF, ""));
            }
        }
    }
    /**
     * @param userId int user_Id to check for
     * @param partyId int party id to check
     * @return int counter returned
     * */
    private int findUserInParty(int userId, int partyId)
    {
        int counter;
        Connection connection = openConnection();
        try {
            counter = 1;
            prepStatement = connection.prepareStatement("SELECT leader_Id,user_1_Id,user_2_Id,user_3_Id,user_4_Id,user_5_Id FROM user_parties WHERE party_ID = ? ");
            prepStatement.setInt(1,partyId);
            ResultSet res = prepStatement.executeQuery();
            res.next();
            int tester;
            int resCols = 7;
            while(counter < resCols)
            {
                tester = res.getInt(counter);
                if(tester == userId)
                {
                    return counter;
                }
                counter++;
            }
        } catch (SQLException e) {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se) {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
        return -1;
    }
    private String getColName(int colId)
    {
        String colName = "";
        switch(colId)
        {
            case 1: colName = "leader_Id";
                break;
            case 2: colName = "user_1_Id";
                break;
            case 3: colName = "user_2_Id";
                break;
            case 4: colName = "user_3_Id";
                break;
            case 5: colName = "user_4_Id";
                break;
            case 6: colName = "user_5_Id";
                break;
            default:
                break;
        }
        return colName;
    }
    /**
     * @param partyID int for looking up table - use both to cut down on false positives
     * @return integer list of user_Ids in party
     * */
    List<Integer> getPartyDetails(int partyID) {
        List<Integer> partyList = new ArrayList<>();
        Connection connection = openConnection();
        try {
            int iterator = 1;
            prepStatement = connection.prepareStatement("SELECT leader_Id,user_1_Id,user_2_Id,user_3_Id,user_4_Id,user_5_Id FROM user_parties WHERE party_Id = ?");
            prepStatement.setInt(1, partyID);
            ResultSet res = prepStatement.executeQuery();
            partyList.add(partyID);
            res.next();
            while (iterator < 8)
            {
                partyList.add(res.getInt(iterator));
                iterator++;
            }

        } catch (SQLException e) {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se) {

                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
        return partyList;
    }

    /**
     * @param userName String username to check on table
     * @return String with user_Id,user_Email,user_Bio
     */
    String getPlayerDetails(String userName)
    {
        Connection connection = openConnection();
        String userdetails = "";
        try {
            prepStatement = connection.prepareStatement("SELECT user_Id,user_Email,user_Bio FROM users WHERE user_Name = ?");
            prepStatement.setString(1, userName);
            ResultSet res = prepStatement.executeQuery();
            String email;
            String bio;
            int id;
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, null, "" + res.absolute(1)));
            id = res.getInt("user_Id");
            email = res.getString("user_Email");
            bio = res.getString("user_Bio");
            userdetails = id + "," + email + "," +bio;
        } catch (SQLException e) {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, e, ""));
        } finally {
            try {
                prepStatement.close();
                closeConnection(connection);
            } catch (SQLException se) {
                LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
            }
        }
        return userdetails;
    }
    @Override
    public Connection openConnection()
    {
        Connection connection;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);
            return connection;
        }catch(SQLException|ClassNotFoundException se)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
        }

       return null;
    }

    /**
     *
     * @param c Connection to close is taken in as param
     */
    @Override
    public void closeConnection(Connection c){
        try{
            c.close();
        }catch(SQLException se)
        {
            LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.WARNING, se, ""));
        }
    }
}
