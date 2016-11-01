package database;

import core.utils.Log;

import java.util.ArrayList;
import java.sql.*;
import java.util.List;


/**
 * Created by s_harte Awesome Gaming  : CS4227 Project on 10/20/2016.
 */
class SqlDatabase {
    // JDBC driver name and database URL
    private final String jdbcDriver;
    //set up to connect to an rds instance on my aws account
    private final String dbUrl;

    //  Database credentials
    private final  String user;
    private final  String pass;
    
    private Connection connection;
    private Statement statement;

    private Log logger;

    /** no-args constructor */
    SqlDatabase()
    {
        logger = new Log(getClass().getName());
        jdbcDriver = "com.mysql.jdbc.Driver";
        dbUrl = "jdbc:mysql://cs4227dbserver.cx7qikfelfcm.eu-west-1.rds.amazonaws.com:3306/awesome_gaming";
        user = "admin";
        pass = "teamawesome";

    }

    /**
     * @param databaseURL Url of database to connect to
     * @param dbUser username for database
     * @param dbPass pass for database
     * @param jdbcDriver jdbc driver
     * */
    SqlDatabase(String databaseURL, String dbUser, String dbPass, String jdbcDriver)
    {
        this.jdbcDriver = jdbcDriver;
        dbUrl = databaseURL;
        user = dbUser;
        pass = dbPass;

    }

        //table name,columns *string is varchar, int is int.
        // users,String user_Name, String user_Pass, String email,String user_Bio.
        // user_friends,int user_Id (from users table),int friend_Id from users.
        // user_messages.
        // user_games.
        // user_invites.
        // user_parties.

    //add a user to the user table should look at hash tables.
    /**
     * @param userName String username to add
     * @param userPass String pass to add
     * @param email String email address to add to table
     * */
    public void addUser(String userName, String userPass, String email)
    {
        logger.logInfo("Attempting to Connect");
        logger.logInfo("Inserting records into the table....");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO users ( user_Name, user_Pass, user_Email) VALUES(?,?,?)");
            prepStatement.setString(1,userName);
            prepStatement.setString(2,userPass);
            prepStatement.setString(3,email);
            prepStatement.executeUpdate();

            prepStatement.close();
        } catch (SQLException e) {
            logger.logWarning(e);
        } catch (ClassNotFoundException ce) {
            logger.logWarning(ce);
            ce.getException();
        } finally {
            try {
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }

    }
    //method to add a friend we need to add this to the interface
    /**
     * @param userId int userID of person who is adding friend
     * @param friendId int friend to be added's id
     * */
    void addFriend(int userId, int friendId)
    {
        logger.logInfo("Inserting records into the table...");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);
            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO user_friends ( user_Id, friend_Id) VALUES( ?, ?)");
            prepStatement.setInt(1,userId);
            prepStatement.setInt(2,friendId);

            prepStatement.executeUpdate();
            prepStatement.close();
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
    }
    //checks the username and password from the users database
    /**
     * @param userName String username to check
     * @param userPass String pass to check
     * @return boolean to allow/fail login
     * */
    boolean canLogin(String userName, String userPass)
    {
        boolean canLogin = false;
        logger.logInfo("Checking login details");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);
            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM users WHERE user_Name = ? AND user_Pass = ?");
            prepStatement.setString(1,userName);
            prepStatement.setString(2,userPass);
            //setting the canLogin boolean to the boolean that's returned if there are results that match the query.

            ResultSet res = prepStatement.executeQuery();
            if(res.isBeforeFirst())
                canLogin = true;
            prepStatement.close();
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        }finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
        return canLogin;
    }
    //needs testing too...
    /**
     * @param userId userId to get username
     * @return String username of user_Id
     * */
    String getPlayerName(int userId)
    {
        String playerName = "";
        logger.logInfo("checking player details...");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);
            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT user_Name FROM users WHERE user_Id = ?");
            prepStatement.setInt(1,userId);

            ResultSet res = prepStatement.executeQuery();
            playerName = res.getString("user_Name");
            prepStatement.close();
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
        return playerName;
    }
    //haven't tested yet...
    /**
     * @param userName String username to get userID of
     * @return return user's user_ID
     * */
    int getUserId(String userName)
    {
        int userId = 0;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT user_Id FROM users WHERE user_Name = ?");
            prepStatement.setString(1,userName);

            ResultSet res = prepStatement.executeQuery();
            userId = res.getInt("user_Id");
            System.out.println(userId);
            prepStatement.close();
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
        return userId;
    }

    //check username,and email
    /**
     * @param userName string username to check
     * @param email string email to check
     * @return int 0/1 true/false
     * */
    int checkNameEmail(String userName, String email)
    {
        int checker = 0;
        logger.logInfo("checking player details...");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM users WHERE user_Name = ? AND user_Email = ?");
            prepStatement.setString(1,userName);
            prepStatement.setString(2,email);
            ResultSet res = prepStatement.executeQuery();

            if(!res.next())
            {
                checker = 1;
                return checker;
            }
            prepStatement.close();
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
        return checker;
    }
    //is party full method
    /**
     * @param partyId int partyId to lookup
     * @return boolean if full or not
     * */
    boolean isPartyFull(int partyId)
    {
        int toCheck = 0;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM user_parties WHERE party_ID = ? AND (user_1_Id = null OR user_2_Id = null OR user_3_Id = null OR user_4_Id = null OR user_5_Id = null)");
            prepStatement.setInt(1, partyId);

            ResultSet res = prepStatement.executeQuery();

            while (res.next())
            {
                int temp = res.getInt(toCheck);
                if (res.wasNull()) {
                    toCheck++;
                }
            }
            prepStatement.close();
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
        return toCheck <= 0;
    }

    /**
     * @param userId userId to get friend of
     * @return Integer list of friends Id
     * */
    List<Integer> getFriendsList(int userId)
    {
        List<Integer> friendsList = new ArrayList<>();
        try {
            Class.forName(jdbcDriver);
            int iterator = 0;
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM user_friends WHERE user_Id = ?");
            prepStatement.setInt(1,userId);

            ResultSet res = prepStatement.executeQuery();
            while(res.next())
            {
                friendsList.add(res.getInt(iterator));
                iterator++;
            }
            prepStatement.close();
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
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
        try {
            Class.forName(jdbcDriver);
            int iterator = 0;
            connection = DriverManager.getConnection(dbUrl, user, pass);
            Integer [] userInvites = new Integer[100];
            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT invite_Id FROM user_invites WHERE user_Id = ?");
            prepStatement.setInt(1,playerId);

            ResultSet res = prepStatement.executeQuery();
            prepStatement.close();
            while(res.next())
            {
                userInvites[iterator] = res.getInt(iterator);
                iterator++;
            }
            invitesList.add(userInvites);

        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
        return invitesList;
    }
    //takes in party creator user_Id and sets it as leader_Id in the table :)
    /**
     * @param leaderId takes in user_Id and sets it as leader of party
     * @return int of partyId
     * */
    int createParty(int leaderId)
    {
        int partyId;
        logger.logInfo("Inserting records into the table...");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO user_parties (leader_Id) VALUES(?)");
            prepStatement.setInt(1,leaderId);
            prepStatement.executeUpdate();
            prepStatement.close();
            prepStatement = connection.prepareStatement("SELECT party_Id from user_parties where leader_Id = ?");
            prepStatement.setInt(1,leaderId);
            ResultSet res = prepStatement.executeQuery();
            partyId = res.getInt("party_Id");
            prepStatement.close();

            return partyId;
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
        return 1;
    }
    //hacked a fix
    /**
     * @param playerID int user_Id to lookup parties table
     * @param partyID int party_Id narrow down lookup criteria
     * */
    void addPlayerToParty(int playerID, int partyID)
    {
        try {
            Class.forName(jdbcDriver);

            connection = DriverManager.getConnection(dbUrl, user, pass);
            statement = connection.createStatement();
            int colId = findNullFromParties(partyID);
            String sqlCol = "";
            switch(colId)
            {
                case 1: sqlCol ="user_1_Id";
                    break;
                case 2: sqlCol = "user_2_Id";
                    break;
                case 3: sqlCol = "user_3_Id";
                    break;
                case 4: sqlCol = "user_4_Id";
                    break;
                case 5: sqlCol = "user_5_Id";
                    break;
                default:
            }
            PreparedStatement prepStatement = connection.prepareStatement("UPDATE ? INTO user_parties ?  WHERE party_Id = ?");
            prepStatement.setInt(1,playerID);
            prepStatement.setString(2,sqlCol);
            prepStatement.setInt(3,partyID);
            prepStatement.executeUpdate();
            prepStatement.close();
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
    }
    //sorted
    /**
     * @param partyID int party to check if exists
     * @return return boolean if so
     * */
    boolean doesPartyExist(int partyID)
    {
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);
            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM user_parties WHERE party_Id = ?");
            prepStatement.setInt(1, partyID);

            ResultSet res = prepStatement.executeQuery();
            prepStatement.close();
            if (res.next()) {
                return false;
            }
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
        return true;
    }
    //should work
    /**
     * @param username username to check
     * @return int 0/1 (true or false)
     * */
    int doesPlayerExist(String username)
    {
        int checker = 0;
        logger.logInfo("Checking player details***");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM users WHERE user_Name = ?");
            prepStatement.setString(1,username);
            ResultSet res = prepStatement.executeQuery();

            if(!res.next())
            {
                checker = 1;
                return checker;
            }
            prepStatement.close();
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
        return checker;
    }
    //the above mentioned hacked fix
    /**
     * @param partyId int partyId to check
     * @return int null for check
     * */
    private int findNullFromParties(int partyId)
    {
        int counter = 0;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);
            counter = 1;
            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM user_parties WHERE party_ID = ? ");
            prepStatement.setInt(1,partyId);
            ResultSet res = prepStatement.executeQuery();
            prepStatement.close();
                while(res.next())
                {
                    if(res.wasNull())
                    {
                        return counter;
                    }else {
                        counter++;
                    }
                }
            counter = 0;
    } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
    } finally {
        try {
            if (statement != null)
                connection.close();
        } catch (SQLException se) {
            logger.logWarning(se);
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
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT ? FROM user_parties where party_Id != ?");
            prepStatement.setInt(1, playerId);
            prepStatement.setInt(2, playerId);
            ResultSet res = prepStatement.executeQuery();
            prepStatement.close();
            if (res.next())
            {
                return true;
            }
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
        return false;
    }
    //int typeID,String invite_content,
    /**
     * @param senderID int sender_ID to add to table
     * @param receiverID int user_ID to add to table
     * @param partyId int party_Id to add to table
     * */
    void addInvite(int senderID, int receiverID, int partyId)
    {
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO user_invites (sender_Id,invite_content,user_Id,typeId,party_Id)  VALUES( ?, ?, ?, ?, ?)");
            prepStatement.setInt(1,senderID);
            prepStatement.setString(2,"this is content");
            prepStatement.setInt(3,receiverID);
            prepStatement.setInt(4,4);
            prepStatement.setInt(5,partyId);

            prepStatement.executeQuery();
            prepStatement.close();
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
    }

    /**
     * @param senderID int sender_Id to remove invite from
     * @param receiverID int receiver_ID to remove from table
     * @param inviteId int invite_id to remove from
     * */
    void removeInvite(int senderID, int receiverID, int inviteId)
    {
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM user_invites WHERE sender_Id = ? AND user_Id = ? AND invite_Id = ?)");
            prepStatement.setInt(1, senderID);
            prepStatement.setInt(2,receiverID);
            prepStatement.setInt(3,inviteId);

            prepStatement.executeUpdate();
            prepStatement.close();
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
    }

    /**
     * @param partyID party_Id to remove from
     * @param playerID player_ID to remove from
     * */
    void removePlayerFromParty(int partyID, int playerID)
    {
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);
            statement = connection.createStatement();
            int colId = findUserInParty(playerID,partyID);
            String sqlCol = "";
            switch(colId)
            {
                case 1: sqlCol ="user_1_Id";
                    break;
                case 2: sqlCol = "user_2_Id";
                    break;
                case 3: sqlCol = "user_3_Id";
                    break;
                case 4: sqlCol = "user_4_Id";
                    break;
                case 5: sqlCol = "user_5_Id";
                    break;
                default:
            }
            PreparedStatement prepStatement = connection.prepareStatement("UPDATE user_parties SET ? = ? WHERE party_Id = ? AND (user_1_Id = ? OR user_2_Id = ? OR user_3_Id = ? OR user_4_Id = ? OR user_5_Id = ? )");
            prepStatement.setString(1,sqlCol);
            prepStatement.setInt(2,playerID);
            prepStatement.setInt(3,partyID);
            prepStatement.executeUpdate();
            prepStatement.close();
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
    }

    /**
     * @param userId int user_Id to check for
     * @param partyId int party id to check
     * @return int counter returned
     * */
    int findUserInParty(int userId, int partyId)
    {
        int counter = 0;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);
            counter = 0;
            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM user_parties WHERE party_ID = ? ");
            prepStatement.setInt(1,partyId);
            ResultSet res = prepStatement.executeQuery();
            prepStatement.close();
            while(res.next())
            {
                counter++;
                if(res.getInt(counter) == userId)
                {
                    return counter;
                }
            }

            counter = 0;
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
        return counter;
    }

    /**
     * @param partyID int for looking up table - use both to cut down on false positives
     * @param playerID int for table lookup - use both to cut down on false positives
     * @return integer list of user_Ids in party
     * */
    List<Integer> getPartyDetails(int partyID, int playerID) {
        List<Integer> partyList = new ArrayList<>();
        try {
            Class.forName(jdbcDriver);
            int iterator = 0;
            connection = DriverManager.getConnection(dbUrl, user, pass);
            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT leader_Id,user_1_Id,user_2_Id,user_3_Id,user_4_Id,user_5_Id FROM user_parties WHERE party_Id = ?");
            prepStatement.setInt(1, partyID);

            ResultSet res = prepStatement.executeQuery();

            while (res.next()) {
                if(res.getInt(iterator)!= playerID){
                partyList.add(res.getInt(iterator));
                iterator++;}
            }
            prepStatement.close();
        } catch (SQLException|ClassNotFoundException e) {
            logger.logWarning(e);
        } finally {
            try {
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
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
        String userdetails = "";

        try {
            Class.forName(jdbcDriver);

            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT user_Id,user_Email,user_Bio FROM users WHERE user_Name = ?");
            prepStatement.setString(1, userName);

            ResultSet res = prepStatement.executeQuery();

            String email;
            String bio;
            int id;
            id = res.getInt("user_Id");
            email = res.getString("user_Email");
            bio = res.getString("user_Bio");

            userdetails = id + "," + email + "," +bio;

            prepStatement.close();
        } catch (SQLException|ClassNotFoundException e) {
            logger.logInfo("testing is it here?");
            logger.logWarning(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }
        return userdetails;
    }
}
