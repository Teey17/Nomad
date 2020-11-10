package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import userManager.*;

public class userDatabase extends Database {

	// DATABASE CONSTANT NAMES
	// Table Student and its columns
	private final String T_STUDENT = "Student";
	// Private final String TS_ID = "studentID";
	private final String TS_USER = "userName";
	private final String TS_PASS = "userPassword";
	
	// Table organiser and its columns
	private final String T_ORGANIZER = "Organizer";
	// Private final String TO_ID = "orgID";
	private final String TO_USER = "userName";
	private final String TO_PASS = "userPassword";
	
	// Connection object
	private static Connection connection;
	
	// ResultSet object
	private static ResultSet result;
	
	// Database object
	private static userDatabase db;
	
	// Singleton pattern
	private userDatabase() throws SQLException {
		if (connection == null) {
			connection = getConnection();
		}
	}
	
	/**
	 * Function returns the instance of this user object
	 * @return
	 * @throws SQLException 
	 */
	public userDatabase getInstance() throws SQLException {
		if (db == null) {
			db = new userDatabase();
		}
		return db;
	}
	
	/**
	 * Function checks if the username and password correspond to a registered user in the system.
	 * It check the user table corresponding to the boolean value of <i>userChoice</i>.
	 * userChoice == True -> user is a Student. Otherwise -> user is an Organizer
	 * @param userName
	 * @param userPassword
	 * @param userChoice true if user is student, false if user is organiser
	 * @return User logged in. Null if no user was found.
	 */
	public User login(String userName, String userPassword, boolean userChoice) {
		String query;
		if (userChoice) {	//Student logs in
			query = "SELECT * FROM "+T_STUDENT+" WHERE "+TS_USER+" = ? AND "+TS_PASS+" = ?";
		} else {	//Organizer logs in
			query = "SELECT * FROM "+T_ORGANIZER+" WHERE "+TO_USER+" = ? AND "+TO_PASS+" = ?";
		}
		//Try-with-resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, userName);
			statement.setString(2, userPassword);
			result = statement.executeQuery();
			
			//Check result only contains one row
			int num_rows;
			result.last(); 					//Move cursor to the last row
			num_rows = result.getRow(); 	//get number of the last row == amount of rows in resultSet
			if (num_rows != 1) return null; //Check it only returned 1
			result.beforeFirst();			//Move cursor back to the initial position
			
			//Check obtain data
			while(result.next()) {
				if (userChoice) { //Create student object
					if (result.getString(TS_USER).equals(userName) && result.getString(TS_PASS).equals(userPassword)) {
						return new Student(userName, userPassword);
					}
				} else { 		//Create organizer object
					if (result.getString(TO_USER).equals(userName) && result.getString(TO_PASS).equals(userPassword)) {
						return new Organizer(userName, userPassword);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage()+"\nError trying to access Organizer table");
		}	
		
		return null;	
	}

	/**
	 * Function inserts into the database's student table the user data received by parameters.
	 * It first checks that the username is not already used in the Organizer table and in the Student
	 * table before inserting the new data.
	 * @param userName
	 * @param userPassword
	 * @return true if the registration went ok. False otherwise.
	 */
	public boolean register(String userName, String userPassword, boolean userChoice) {
		String insert;
		String query;
		
		if (userChoice) {	//Check student table
			query = "SELECT "+TS_USER+" FROM "+T_STUDENT+" WHERE "+TS_USER+" LIKE '"+userName+"';";
			insert = "INSERT INTO "+T_STUDENT+" ("+TS_USER+", "+TS_PASS+") VALUES ('"+userName+"', '"+userPassword+"');";
		} else {
			query = "SELECT "+TO_USER+" FROM "+T_ORGANIZER+" WHERE "+TO_USER+" LIKE '"+userName+"';";
			insert = "INSERT INTO "+T_ORGANIZER+" ("+TO_USER+", "+TO_PASS+") VALUES ('"+userName+"', '"+userPassword+"');";
		}
		
		//Try-with-resources
		try (Statement statement = connection.createStatement()) {
			//Check if user exists
			result = statement.executeQuery(query);
			if (result.next()) return false;
			
			//If userName doesn't exist for any table, insert 
			int result = statement.executeUpdate(insert);
			
			if (result == 1) return true;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * Function that changes the password of an user.
	 * @param userName
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public boolean changePassword(String userName, String oldPassword, String newPassword) {
		
		/*
		 * TO BE CHECKED: THEY PASS OBJECT OR ADD BOOLEAN ATTRIBUTE
		 */
		String orgQuery = "UPDATE "+T_ORGANIZER+" SET "+TO_PASS+" = '"+newPassword+"' WHERE "+TO_USER+" = '"+userName+"' AND "+TO_PASS+" = '"+oldPassword+"';";
		String stdQuery = "UPDATE "+T_STUDENT+" SET "+TS_PASS+" = '"+newPassword+"' WHERE "+TS_USER+" = '"+userName+"' AND "+TS_PASS+" = '"+oldPassword+"';";
		int r;
		
		try {
			Statement statement = connection.createStatement();
			r = statement.executeUpdate(orgQuery);
			if (r == 1) return true;
			
			r = statement.executeUpdate(stdQuery);
			if (r == 1) return true;
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}
	
}
