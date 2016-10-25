package database;

import java.util.ArrayList;
import java.sql.*;


/**
 * Created by s_harte CS4227 Awesome Gaming  on 10/20/2016.
 */
public class SqlDatabase {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //set up to connect to an rds instance on my aws account
    static final String DB_URL = "jdbc:mysql://cs4227dbserver.cx7qikfelfcm.eu-west-1.rds.amazonaws.com:3306/awesome_gaming";
    private int userCount = 100;
    private int friendDB_Id = 100;
    private int partyDB_Id = 100;
    //  Database credentials
    static final String USER = "admin";
    static final String PASS = "teamawesome";

    protected static final String userDB = "users";
    protected static final String friendDB = "user_friends";
    protected static final String gameDB = "user_games";
    protected static final String inviteDB = "user_invites";
    protected static final String messagesDB = "user_messages";

    public Connection connection;
    public Statement statement;

    public SqlDatabase(){
        connectToDb();
    }
    //String user,String pass - don't think its needed
    private void connectToDb() {
        //initialise to null
        connection = null;
        statement = null;

        try {
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            System.out.println("Attempting to Connect");
            //connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);


            // String sqlString = "CREATE DATABASE "+ dbName;
            // statement.execute(sqlString);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                /*if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
                se2.printStackTrace();//nothing else to be done
            }try {*/
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }//end finally

    }//end of connection method

                //table name,columns *string is varchar, int is int
                // users, String table name,String user_Name, String user_Pass, String email
                // user_friends,int user_Id (from users table),int friend_Id from users
                // user_messages,
                // user_games,
                // user_invites,
                // user_parties
    //add a user to the user table should look at hash tables.
    public void add_User(String user_Name, String user_Pass, String email)
    {


        System.out.println("Attempting to Connect");
        //connection

        System.out.println("Inserting records into the table...");
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            userCount++;
            statement = connection.createStatement();
            //stupid mfking sql stuff
            PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO users ( user_Name, user_Pass, user_Email) VALUES(?,?,?)");


            prepStatement.setString(1,user_Name);
            prepStatement.setString(2,user_Pass);
            prepStatement.setString(3,email);
            prepStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
            ce.getException();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }
    //method to add a friend we need to add this to the interface
    public void add_Friend(int user_Id, int friend_Id)
    {
        System.out.println("Inserting records into the table...");
        try {
            friendDB_Id++;
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

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
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

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
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

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
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

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
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

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
        boolean checker = true;

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM user_parties WHERE party_ID = ? AND (user_1_Id = null OR user_2_Id = null OR user_3_Id = null OR user_4_Id = null OR user_5_Id = null)");
            prepStatement.setInt(1,party_Id);
            //prepStatement.setString(2,email);

            ResultSet res = prepStatement.executeQuery();
            if(res.next())
            {
                checker = false;
                return checker;
            }else
                return checker;

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

    public ArrayList<Integer> get_FriendsList(int user_Id)
    {
        ArrayList<Integer> friendsList = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);
            int iterator = 0;
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

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
            Class.forName(JDBC_DRIVER);
            int iterator = 0;
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
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
        int party_Id;
        System.out.println("Inserting records into the table...");
        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO user_parties (leader_Id) VALUES(?)");


            prepStatement.setInt(1,leader_Id);


            prepStatement.executeUpdate();
            party_Id = partyDB_Id;
            partyDB_Id++;
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
        return 0;
    }
    //sent norah an email in regards to this query.
    // not sure exactly how to make it go to the first null field it finds,
    // might require writing another method to find first col with null value.
    public void addPlayerToParty(int playerID, int partyID)
    {
        try {
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("UPDATE ? INTO user_parties user_1_Id ifnull()  WHERE party_Id = ? AND (user_1_Id = null OR user_2_Id = null OR user_3_Id = null OR user_4_Id = null OR user_5_Id = null)");
            prepStatement.setInt(1,playerID);
            prepStatement.setInt(2,partyID);



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
        boolean full = true;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            statement = connection.createStatement();
            //stupid mfking sql stuff
            //creating the prepared statement.
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM user_parties WHERE party_Id = ?");
            prepStatement.setInt(1, partyID);

            ResultSet res = prepStatement.executeQuery();
            if (res.next()) {
                full = false;
                return full;
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
        return full;
    }
    //should work
    public int does_Player_Exist(String username)
    {
        int checker = 0;
        System.out.println("checking player details");
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

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


}
