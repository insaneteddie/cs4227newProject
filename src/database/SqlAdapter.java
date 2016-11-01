package database;

import java.sql.Connection;

/**
 * Created by funka,awesome_gameing : CS4227 Project on 28/10/2016.
 */
public class SqlAdapter implements SqlDatabaseInterface
{

    SqlDatabase sqlDB;

    public void SqlAdapter(){
        set_Up_Database_Details();
    }


    @Override
    public void set_Up_Database_Details()
    {
        sqlDB = new SqlDatabase();
    }

    @Override
    public void set_Up_New_Database(String databaseURL, String dbUser, String dbPass, String jdbcDriver) {
        sqlDB = new SqlDatabase(databaseURL,dbUser,dbPass,jdbcDriver);
    }

    @Override
    public void connect_To_Database() {
        sqlDB = new SqlDatabase();

    }


}
