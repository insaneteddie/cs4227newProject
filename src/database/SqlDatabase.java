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
    static final String DB_URL = "jdbc:mysql:cs4227dbserver.cx7qikfelfcm.eu-west-1.rds.amazonaws.com/awesome_gaming:3306";
    private int userCount = 100;
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

    //String user,String pass - don't think its needed
    public void connectToDb(String dbName) {
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
    public void add_User(String user_Name, String user_Pass, String email) {
        System.out.println("Inserting records into the table...");
        try {
            userCount++;
            statement = connection.createStatement();
            String sqlStatement = "INSERT INTO " + userDB +
                    " VALUES (" + userCount + ", '" + user_Name + "', '" + user_Pass + "', " + email + ")";
            statement.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
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
    public void add_Friend(int user_Id, int friend_Id) {
        System.out.println("Inserting records into the table...");
        try {
            statement = connection.createStatement();
            String sqlStatement = "INSERT INTO " + friendDB +
                    " VALUES ( , '" + user_Id + "', '" + friend_Id + "',)";
            statement.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
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
    public boolean can_Login(String user_Name,String user_Pass){
        boolean canLogin = false;
        System.out.println("checking login details");
        try {
            statement = connection.createStatement();
            String sqlStatement = "SELECT EXISTS ( SELECT * FROM "+userDB+" WHERE username = "+user_Name +"AND password ="+user_Pass+")";
            canLogin = statement.execute(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
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

    

}
