package database;

import core.utils.Log;

import java.util.ArrayList;
import java.sql.*;


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

    public SqlDatabase()
    {
        jdbcDriver = "com.mysql.jdbc.Driver";
        dbUrl = "jdbc:mysql://cs4227dbserver.cx7qikfelfcm.eu-west-1.rds.amazonaws.com:3306/awesome_gaming";
        user = "admin";
        pass = "teamawesome";
        connectToDb();
        logger = new Log(getClass().getName());
    }

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
    public void addUser(String userName, String userPass, String email)
    {


        logger.logInfo("Attempting to Connect");
        //connection

        logger.logInfo("Inserting records into the table...");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
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
    public void addFriend(int user_Id, int friend_Id)
    {
        System.out.println("Inserting records into the table...");
        try {

            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO user_friends (id, user_Id, friend_Id) VALUES( ?, ?)");


            prepStatement.setInt(1,user_Id);
            prepStatement.setInt(2,friend_Id);

            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    //checks the username and password from the users database
    public boolean can_Login(String user_Name,String user_Pass)
    {
        boolean canLogin = false;
        System.out.println("checking login details");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM users WHERE user_Name = ? AND user_Pass = ?");

            prepStatement.setString(1,user_Name);
            prepStatement.setString(2,user_Pass);
            //setting the canLogin boolean to the boolean that's returned if there are results that match the query.
            canLogin = prepStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return canLogin;
    }
    //needs testing too...
    public String get_PlayerName(int user_Id)
    {
        String player_Name = "";
        System.out.println("checking player details");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT user_Name FROM users WHERE user_Id = ?");
            prepStatement.setInt(1,user_Id);

            ResultSet res = prepStatement.executeQuery();
            player_Name = res.getString("user_Name");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return player_Name;
    }
    //haven't tested yet...
    public int get_UserId(String user_Name)
    {
        int userId = 0;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT user_Id FROM users WHERE user_Name = ?");
            prepStatement.setString(1,user_Name);

            ResultSet res = prepStatement.executeQuery();
            userId = res.getInt("user_Id");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return userId;
    }

    //check username,and email
    public int check_Name_Email(String user_Name,String email)
    {
        int checker = 0;
        System.out.println("checking player details");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM users WHERE user_Name = ? AND user_Email = ?");
            prepStatement.setString(1,user_Name);
            prepStatement.setString(2,email);

            ResultSet res = prepStatement.executeQuery();
            if(!res.next())
            {
                checker = 1;
                return checker;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return checker;
    }
    //is party full method
    public boolean isPartyFull(int party_Id)
    {
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM user_parties WHERE party_ID = ? AND (user_1_Id = null OR user_2_Id = null OR user_3_Id = null OR user_4_Id = null OR user_5_Id = null)");
            prepStatement.setInt(1, party_Id);

            //prepStatement.setString(2,email);

            ResultSet res = prepStatement.executeQuery();

            while (res.next())
            {
                if (res.wasNull()) {

                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return true;
    }

    public ArrayList<Integer> get_FriendsList(int user_Id)
    {
        ArrayList<Integer> friendsList = new ArrayList<>();
        try {
            Class.forName(jdbcDriver);
            int iterator = 0;
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM user_friends WHERE user_Id = ?");
            prepStatement.setInt(1,user_Id);

            ResultSet res = prepStatement.executeQuery();
            while(res.next())
            {
                friendsList.add(res.getInt(iterator));
                iterator++;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return friendsList;
    }

    public ArrayList<Integer []> get_Invites(int player_Id)
    {
        ArrayList<Integer []> invitesList = new ArrayList<>();
        try {
            Class.forName(jdbcDriver);
            int iterator = 0;
            connection = DriverManager.getConnection(dbUrl, user, pass);
            Integer [] user_invites = new Integer[100];
            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT invite_Id FROM user_invites WHERE user_Id = ?");
            prepStatement.setInt(1,player_Id);

            ResultSet res = prepStatement.executeQuery();
            while(res.next())
            {
                user_invites[iterator] = res.getInt(iterator);

                iterator++;
            }
            invitesList.add(user_invites);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return invitesList;
    }
    //takes in party creator user_Id and sets it as leader_Id in the table :)
    public int create_Party(int leader_Id)
    {
        int party_Id = 0;
        System.out.println("Inserting records into the table...");
        try {

            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO user_parties (leader_Id) VALUES(?)");


            prepStatement.setInt(1,leader_Id);


            prepStatement.executeUpdate();
            party_Id = 1;
            return party_Id;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {

                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return party_Id;
    }
    //hacked a fix
    public void addPlayerToParty(int playerID, int partyID)
    {
        try {
            Class.forName(jdbcDriver);

            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            int colId = find_Null_From_Parties(partyID);
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

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    //sorted
    public boolean does_Party_Exist(int partyID)
    {

        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM user_parties WHERE party_Id = ?");
            prepStatement.setInt(1, partyID);

            ResultSet res = prepStatement.executeQuery();
            if (res.next()) {

                return false;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return true;
    }
    //should work
    public int does_Player_Exist(String username)
    {
        int checker = 0;
        System.out.println("checking player details");
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM users WHERE user_Name = ?");
            prepStatement.setString(1,username);


            ResultSet res = prepStatement.executeQuery();
            if(!res.next())
            {
                checker = 1;
                return checker;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return checker;
    }
    //the above mentioned hacked fix
    private int find_Null_From_Parties(int party_Id)
    {
        int counter = 0;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);
            counter = 1;

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM user_parties WHERE party_ID = ? ");

            prepStatement.setInt(1,party_Id);


            ResultSet res = prepStatement.executeQuery();
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
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        e.getException();
    } finally {
        try {
            if (statement != null)
                connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
        return counter;
    }

    public boolean is_In_Party(int player_Id)
    {
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT ? FROM user_parties where party_Id != ?");
            prepStatement.setInt(1, player_Id);
            prepStatement.setInt(2, player_Id);

            ResultSet res = prepStatement.executeQuery();
            if (res.next())
            {
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return false;
    }
//int typeID,String invite_content,
    public void add_Invite(int senderID, int receiverID, int partyId)
    {
        try {
            Class.forName(jdbcDriver);

            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.

            PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO user_invites (sender_Id,invite_content,user_Id,typeId,party_Id)  VALUES( ?, ?, ?, ?, ?)");
            prepStatement.setInt(1,senderID);
            prepStatement.setString(2,"this is content");
            prepStatement.setInt(3,receiverID);
            prepStatement.setInt(4,4);
            prepStatement.setInt(5,partyId);

            prepStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void remove_Invite(int senderID, int receiverID, int inviteId)
    {
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM user_invites WHERE sender_Id = ? AND user_Id = ? AND invite_Id = ?)");
            prepStatement.setInt(1, senderID);
            prepStatement.setInt(2,receiverID);
            prepStatement.setInt(3,inviteId);

            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void removePlayerFromParty(int partyID, int playerID)
    {
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            int colId = find_User_In_Party(playerID,partyID);
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


            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("UPDATE user_parties SET ? = ? WHERE party_Id = ? AND (user_1_Id = ? OR user_2_Id = ? OR user_3_Id = ? OR user_4_Id = ? OR user_5_Id = ? )");

            prepStatement.setString(1,sqlCol);
            prepStatement.setInt(2,playerID);
            prepStatement.setInt(3,partyID);




            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public int find_User_In_Party(int user_Id, int party_Id)
    {
        int counter = 0;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbUrl, user, pass);
            counter = 0;

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM user_parties WHERE party_ID = ? ");

            prepStatement.setInt(1,party_Id);


            ResultSet res = prepStatement.executeQuery();
            while(res.next())
            {
                counter++;
                if(res.getInt(counter) == user_Id)
                {
                    return counter;
                }

            }
            counter = 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return counter;
    }

    public ArrayList<Integer> get_PartyDetails(int partyID, int playerID) {
        ArrayList<Integer> partyList = new ArrayList<>();
        try {
            Class.forName(jdbcDriver);
            int iterator = 0;
            connection = DriverManager.getConnection(dbUrl, user, pass);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT leader_Id,user_1_Id,user_2_Id,user_3_Id,user_4_Id,user_5_Id FROM user_parties WHERE party_Id = ?");
            prepStatement.setInt(1, partyID);

            ResultSet res = prepStatement.executeQuery();
            while (res.next()) {
                if(res.getInt(iterator)!= playerID)
                partyList.add(res.getInt(iterator));
                iterator++;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return partyList;
    }
}
