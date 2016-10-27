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
    public SqlDatabase()
    {
        jdbcDriver = "com.mysql.jdbc.Driver";
        dbUrl = "jdbc:mysql://cs4227dbserver.cx7qikfelfcm.eu-west-1.rds.amazonaws.com:3306/awesome_gaming";
        user = "admin";
        pass = "teamawesome";
        connectToDb();
        logger = new Log(getClass().getName());
    }

    /**
     * @param databaseURL
     * @param dbUser
     * @param dbPass
     * @param jdbcDriver
     * */
    public SqlDatabase(String databaseURL, String dbUser, String dbPass, String jdbcDriver)
    {
        this.jdbcDriver = jdbcDriver;
        dbUrl = databaseURL;
        user = dbUser;
        pass = dbPass;
        connectToDb();
    }

    //String user,String pass - don't think its needed
    private void connectToDb()
    {
        //initialise to null
        connection = null;
        statement = null;

        try {
            //Register JDBC driver
            Class.forName(jdbcDriver);

            logger.logInfo("Attempting to Connect");
            //connection
            connection = DriverManager.getConnection(dbUrl, user, pass);

        } catch (ClassNotFoundException e) {
            logger.logWarning(e);
        } catch (SQLException se) {
            logger.logWarning(se);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }//end finally

    }//end of connection method

        //table name,columns *string is varchar, int is int.
        // users, String table name,String user_Name, String user_Pass, String email.
        // user_friends,int user_Id (from users table),int friend_Id from users.
        // user_messages.
        // user_games.
        // user_invites.
        // user_parties.

    //add a user to the user table should look at hash tables.
    /**
     * @param userName
     * @param userPass
     * @param email
     * */
    public void addUser(String userName, String userPass, String email)
    {
        logger.logInfo("Attempting to Connect");
        logger.logInfo("Inserting records into the table...");
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
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                logger.logWarning(se);
            }
        }

    }
    //method to add a friend we need to add this to the interface
    /**
     * @param userId
     * @param friendId
     * */
    public void addFriend(int userId, int friendId)
    {
        logger.logInfo("Inserting records into the table...");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);
            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO user_friends (id, user_Id, friend_Id) VALUES( ?, ?)");
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
     * @param userName
     * @param userPass
     * @return
     * */
    public boolean canLogin(String userName, String userPass)
    {
        boolean canLogin = false;
        logger.logInfo("checking login details");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);
            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM users WHERE user_Name = ? AND user_Pass = ?");
            prepStatement.setString(1,userName);
            prepStatement.setString(2,userPass);
            //setting the canLogin boolean to the boolean that's returned if there are results that match the query.
            canLogin = prepStatement.execute();
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
     * @param userId
     * @return
     * */
    public String getPlayerName(int userId)
    {
        String player_Name = "";
        System.out.println("checking player details");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);
            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT user_Name FROM users WHERE user_Id = ?");
            prepStatement.setInt(1,userId);

            ResultSet res = prepStatement.executeQuery();
            prepStatement.close();
            player_Name = res.getString("user_Name");
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
        return player_Name;
    }
    //haven't tested yet...
    /**
     * @param userName
     * @return
     * */
    public int getUserId(String userName)
    {
        int userId = 0;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT user_Id FROM users WHERE user_Name = ?");
            prepStatement.setString(1,userName);

            ResultSet res = prepStatement.executeQuery();
            prepStatement.close();
            userId = res.getInt("user_Id");
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
        return userId;
    }

    //check username,and email
    /**
     * @param userName
     * @param email
     * @return
     * */
    public int checkNameEmail(String userName, String email)
    {
        int checker = 0;
        System.out.println("checking player details");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM users WHERE user_Name = ? AND user_Email = ?");
            prepStatement.setString(1,userName);
            prepStatement.setString(2,email);

            ResultSet res = prepStatement.executeQuery();
            prepStatement.close();
            if(!res.next())
            {
                checker = 1;
                return checker;
            }

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
        return checker;
    }
    //is party full method
    /**
     * @param partyId
     * @return
     * */
    public boolean isPartyFull(int partyId)
    {
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM user_parties WHERE party_ID = ? AND (user_1_Id = null OR user_2_Id = null OR user_3_Id = null OR user_4_Id = null OR user_5_Id = null)");
            prepStatement.setInt(1, partyId);

            ResultSet res = prepStatement.executeQuery();
            prepStatement.close();
            while (res.next())
            {
                if (res.wasNull()) {
                    return false;
                }
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

    /**
     * @param userId
     * @return
     * */
    public List<Integer> getFriendsList(int userId)
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
            prepStatement.close();
            while(res.next())
            {
                friendsList.add(res.getInt(iterator));
                iterator++;
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
        return friendsList;
    }

    /**
     * @param playerId
     * @return
     * */
    public List<Integer []> getInvites(int playerId)
    {
        List<Integer []> invitesList = new ArrayList<>();
        try {
            Class.forName(jdbcDriver);
            int iterator = 0;
            connection = DriverManager.getConnection(dbUrl, user, pass);
            Integer [] user_invites = new Integer[100];
            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT invite_Id FROM user_invites WHERE user_Id = ?");
            prepStatement.setInt(1,playerId);

            ResultSet res = prepStatement.executeQuery();
            prepStatement.close();
            while(res.next())
            {
                user_invites[iterator] = res.getInt(iterator);
                iterator++;
            }
            invitesList.add(user_invites);

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
     * @param leaderId
     * @return
     * */
    public int createParty(int leaderId)
    {
        int party_Id = 0;
        System.out.println("Inserting records into the table...");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO user_parties (leader_Id) VALUES(?)");
            prepStatement.setInt(1,leaderId);
            prepStatement.executeUpdate();
            prepStatement.close();
            party_Id = 1;
            return party_Id;
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
        return party_Id;
    }
    //hacked a fix
    /**
     * @param playerID
     * @param partyID
     * */
    public void addPlayerToParty(int playerID, int partyID)
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
     * @param partyID
     * @return
     * */
    public boolean doesPartyExist(int partyID)
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
     * @param username
     * @return
     * */
    public int doesPlayerExist(String username)
    {
        int checker = 0;
        System.out.println("checking player details");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM users WHERE user_Name = ?");
            prepStatement.setString(1,username);
            ResultSet res = prepStatement.executeQuery();
            prepStatement.close();
            if(!res.next())
            {
                checker = 1;
                return checker;
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
        return checker;
    }
    //the above mentioned hacked fix
    /**
     * @param partyId
     * @return
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
     * @param playerId
     * @return
     * */
    public boolean isInParty(int playerId)
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
     * @param senderID
     * @param receiverID
     * @param partyId
     * */
    public void addInvite(int senderID, int receiverID, int partyId)
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
     * @param senderID
     * @param receiverID
     * @param inviteId
     * */
    public void removeInvite(int senderID, int receiverID, int inviteId)
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
     * @param partyID
     * @param playerID
     * */
    public void removePlayerFromParty(int partyID, int playerID)
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
     * @param userId
     * @param partyId
     * @return
     * */
    public int findUserInParty(int userId, int partyId)
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
     * @param partyID
     * @param playerID
     * @return
     * */
    public List<Integer> getPartyDetails(int partyID, int playerID) {
        List<Integer> partyList = new ArrayList<>();
        try {
            Class.forName(jdbcDriver);
            int iterator = 0;
            connection = DriverManager.getConnection(dbUrl, user, pass);
            statement = connection.createStatement();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT leader_Id,user_1_Id,user_2_Id,user_3_Id,user_4_Id,user_5_Id FROM user_parties WHERE party_Id = ?");
            prepStatement.setInt(1, partyID);

            ResultSet res = prepStatement.executeQuery();
            prepStatement.close();
            while (res.next()) {
                if(res.getInt(iterator)!= playerID)
                partyList.add(res.getInt(iterator));
                iterator++;
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
        return partyList;
    }
}
