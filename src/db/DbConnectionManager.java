package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.cj.jdbc.Driver;

/**
 * DbConnectionManager is a Singleton object that manages the
 * connection to the database.
 * All transactions between DAO and the DB is performed by
 * this object.
 * DAO doesn't know anything about 'Connections', 'Transaction' or 
 * other DB specific techniques, this is totally managed by this class.
 * Only SQL strings, by query or PreparedStatements, and returned
 * ResultSet is exposed to DAO
 * <br><strong>Note:</strong> This connects to a postgresql database running
 * on localhost. You also need to download suitable JDBC driver (4.2 or higher)
 * from <a href="https://jdbc.postgresql.org/download.html">jdbc.postgresql.org/download</a>
 * @author kristersundlof
 * @version 2018-10-18
 */
public class DbConnectionManager {
	
	private static final String DB_NAME = "nucleardb"; // Change value to your database
	private static final String USER = "Krister"; // Change value to your user
	private static final String PASSWORD = "Kn@rkare666"; // Change value to your password
	private static final String CONNECTION_URL = "jdbc:mysql://server.titanmail.se:59997/"+DB_NAME+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	private static DbConnectionManager instance = null;
	
	private DbConnectionManager(){}
	
	public static DbConnectionManager getInstance() {
		if(instance == null) {
			instance = new DbConnectionManager();
		}
		return instance;
	}

	/**
	 * This method creates a connection to the database by a connection URL
	 * and a user name and password.
	 * It is private because all DB specific is contained within this class.	
	 * @return An instance of type java.sql.Connection
	 */
	private Connection getConnection() {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            //System.out.println("Connected to the MySQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        return connection;
	}
	private Statement getStatement(Connection connection) {
		try{
			statement =  connection.createStatement();
		}
		catch (SQLException e) {
			System.err.println("Could not create Statement");
			System.err.println(e.getMessage());
		}
		return statement;
	}
	/**
	 * Wrapper method to the 'statement'.executeQuery(String sqlString)
	 * A statement needs a connection and is mandatory to be able
	 * to send a query to the DB. This is wrapped to contain all DB 
	 * specific techniques within this class.
	 * This method is one of the few that is exposed as public and it
	 * hides the DB-connection mechanism.
	 * <strong>Because of the bare sql string this method is susceptible to 
	 * SQL-injection and should only be used for read only transactions.</strong> 
	 * @param sqlString The sql string
	 * @return A ResultSet with the result from the SQL query
	 * @throws SQLException 
	 */
	public ResultSet excecuteQuery(String sqlString) throws SQLException {
		return this.getStatement(this.getConnection()).executeQuery(sqlString);
	}
	
	/**
	 * Wrapper method to the 'connection'.prepareStatement(String sqlString)
	 * It hides the DB-connection mechanism but exposes (by public) the prepareStatement
	 * method thus needing only a String (the SQL string) and returning a ResultSet
	 * <strong>This method is not susceptible to SQL-injection and is therefore safe to use
	 * for write operations on the database.<strong>
	 * @param statementString
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement prepareStatement(String statementString) throws SQLException {
		return this.getConnection().prepareStatement(statementString);
	}
	
	public void close() {
		try{
			if(statement != null) statement.close();
			if(connection != null) connection.close();
		}catch (SQLException e) {
			System.err.println("Could not close the statement or the connection");
			System.err.println(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}