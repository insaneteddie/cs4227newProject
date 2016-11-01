package database;

import java.sql.*;
/**
 * Created by funka,awesome_gameing : CS4227 Project on 28/10/2016.
 */
 interface SqlDatabaseInterface {

     void set_Up_Database_Details();

     void set_Up_New_Database(String databaseURL, String dbUser, String dbPass, String jdbcDriver);

     void connect_To_Database();



}
