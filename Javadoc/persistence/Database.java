package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 
 * Class controls all interaction between logical components and database accesses.
 * 
 * NOTES: Make sure to sanitise input to avoid SQL Injections AND/OR implement query 
 * statements with preparedStatement objects
 *
 */
public class Database {
	// To connect to the database
	//REAL DATABASE private String uRL =  "jdbc:mysql://remotemysql.com:3306/0EZQObP0WF?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private String uRL =  "jdbc:mysql://remotemysql.com:3306/UDLdhORRPs?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
	// Private String username="0EZQObP0WF";
	// Private String password="PlYimvYZ84";
	private String username="UDLdhORRPs";
	private String password="p7b2Q4EXzq";
		
	// Singleton pattern: an instance of this class as attribute
	private static Connection connection;
	
	/**
	 * Constructor that establishes the connection if it hasn't been created
	 * @throws SQLException
	 */
	protected Database() throws SQLException {
		// Create Database instance if it doesn't exist
		if (connection == null) {
			// establish the connection to the database
			connection = DriverManager.getConnection(uRL, username, password);
		}
	}

	/**
	 * Method that gets the connection to the remote database.
	 * @throws SQLException 
	 */
	protected static Connection getConnection() throws SQLException {
		return connection;
	}

}