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
    static final String DB_URL = "jdbc:cs4227dbserver.cx7qikfelfcm.eu-west-1.rds.amazonaws.com:3306";

    //  Database credentials
    static final String USER = "admin";
    static final String PASS = "teamawesome";

    public void connectToDb(String user,String pass){


        Connection connection = null;
        Statement statement = null;

        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            System.out.println("Attempting to Connect");
            //connection
            if(user==null || pass==null) {
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            }else
            {
                connection = DriverManager.getConnection(DB_URL,user,pass);
            }
            //creating db to test
            statement = connection.createStatement();

            String sqlString = "CREATE DATABASE AWESOME_PLAYERS";
            statement.execute(sqlString);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
                se2.printStackTrace();//nothing else to be done
            }try {
                if(connection!=null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }//end finally


    }//end of connection method

}
