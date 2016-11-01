package database;


/**
 * Created by funka,awesome_gameing : CS4227 Project on 28/10/2016.
 */
public class SqlAdapter implements SqlDatabaseInterface
{

    SqlDatabase sqlDB;

    /**
     * constructor for adapter
     */
    public SqlAdapter(){
        setUpDatabaseDetails();
    }

    /**
     * set up the database
     */
    @Override
    public void setUpDatabaseDetails()
    {
        sqlDB = new SqlDatabase();
    }

    /**
     * overloaded set up
     * @param databaseURL string database url
     * @param dbUser string database user
     * @param dbPass string database pass
     * @param jdbcDriver string jdbc driver
     */
    @Override
    public void setUpDatabaseDetails(String databaseURL, String dbUser, String dbPass, String jdbcDriver) {
        sqlDB = new SqlDatabase(databaseURL,dbUser,dbPass,jdbcDriver);
    }

    /**
     * overridden connection from interface
     */
    @Override
    public void connectToDatabase() {
        sqlDB = new SqlDatabase();

    }


}
