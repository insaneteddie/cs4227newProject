package database;

/**
 * Created by funka,awesome_gameing : CS4227 Project on 28/10/2016.
 */
 interface SqlDatabaseInterface {
    /**
     * setup the database
     */
     void setUpDatabaseDetails();

    /**
     * overloaded database setup
     * @param databaseURL string database url
     * @param dbUser string database user
     * @param dbPass string database pass
     * @param jdbcDriver string jdbc driver
     */
     void setUpDatabaseDetails(String databaseURL, String dbUser, String dbPass, String jdbcDriver);

    /**
     * connection method
     */
     void connectToDatabase();



}
