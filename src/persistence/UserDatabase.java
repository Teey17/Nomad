package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import userManager.*;

/**
 * Implements all the interfaces needed from the logic layer to access user functionality to
 * register, log in, change passwords and delete information of user objects.
 * 
 * Uses the connection object of its superclass to create and execute SQL statements. 
 * 
 * @version 1.0
 */
public class UserDatabase extends Database {

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
			query = "SELECT * FROM "+T_STUDENT+" WHERE "+TS_USER+" LIKE ? AND "+TS_PASS+" = ?";
		} else {	//Organizer logs in
			query = "SELECT * FROM "+T_ORGANIZER+" WHERE "+TO_USER+" LIKE ? AND "+TO_PASS+" = ?";
		}
		//Try-with-resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, userName);
			statement.setString(2, userPassword);
			ResultSet result = statement.executeQuery();
			
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
		String checkQuery; //Query to check if the userName doesn't exist in the table opposite to user choice
		
		if (userChoice) {	
			//Register in student table
			query = "SELECT "+TS_USER+" FROM "+T_STUDENT+" WHERE "+TS_USER+" LIKE ?;";
			checkQuery = "SELECT "+TO_USER+" FROM "+T_ORGANIZER+" WHERE "+TO_USER+" LIKE ?;";
			insert = "INSERT INTO "+T_STUDENT+" ("+TS_USER+", "+TS_PASS+") VALUES (?, ?);";
		} else {	
			//Register in organiser table
			query = "SELECT "+TO_USER+" FROM "+T_ORGANIZER+" WHERE "+TO_USER+" LIKE ?;";
			checkQuery = "SELECT "+TS_USER+" FROM "+T_STUDENT+" WHERE "+TS_USER+" LIKE ?;";
			insert = "INSERT INTO "+T_ORGANIZER+" ("+TO_USER+", "+TO_PASS+") VALUES (?, ?);";
		}
		
		//Try-with-resources
		try {
			//Prepare statement to check the userName doesn't already exist in the complementary table
			PreparedStatement statement = connection.prepareStatement(checkQuery);
			statement.setString(1, userName);
			
			//Execute statement and check if there are no coincidences
			ResultSet result = statement.executeQuery();
			if (result.isBeforeFirst()) return false;
			
			//Prepare statement to check the userName in the target table
			statement = connection.prepareStatement(query);
			statement.setString(1, userName);
			
			//Execute statement and check if there are no coincidences
			result = statement.executeQuery();
			if (result.isBeforeFirst()) return false;
			
			//If userName doesn't exist for any table, insert
			
			//Prepare statement
			statement = connection.prepareStatement(insert);
			statement.setString(1, userName);
			statement.setString(2, userPassword);
			
			//Execute statement and check if there are no issues
			int r = statement.executeUpdate();
			
			if (r == 1) return true;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * Function that checks in the database to which table does userName belong to, checks that the
	 * provided old password is right, and on a valid condition changes the password to the new value
	 * 
	 * @param userName of the currently logged user
	 * @param oldPassword password currently associated with this user in the database
	 * @param newPassword password to be associated with this user in the database
	 * @return true when the operation has been successful, 
	 */
	public boolean changePassword(String userName, String oldPassword, String newPassword) {
		String orgInsert = "UPDATE "+T_ORGANIZER+" SET "+TO_PASS+" = ? WHERE "+TO_USER+" LIKE ? AND "+TO_PASS+" = ?;";
		String stdInsert = "UPDATE "+T_STUDENT+" SET "+TS_PASS+" = ? WHERE "+TS_USER+" LIKE ? AND "+TS_PASS+" = ?;";
		int r;
		
		try {
			// Prepare statement
			PreparedStatement statement = connection.prepareStatement(orgInsert);
			statement.setString(1, newPassword);
			statement.setString(2, userName);
			statement.setString(3, oldPassword);
			
			// Execute and check if it affected one row
			r = statement.executeUpdate();
			if (r == 1) return true;
			
			// if it didn't affect any row, combination username and old password didn't exist in organiser table
			// try student
			
			// Prepare statement
			statement = connection.prepareStatement(stdInsert);
			statement.setString(1, newPassword);
			statement.setString(2, userName);
			statement.setString(3, oldPassword);
			
			// Execute and check if it affected one row
			r = statement.executeUpdate();
			if (r == 1) return true;
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}

	/**
	 * Function deletes the row in the belonging table for the user with userName and userPassword supplied.
	 * It tries to delete first from organiser table, and if the DML statement doesn't return a successful
	 * response, it tries to delete from student table. Finally, if the new DML statement doesn't return a 
	 * successful response either, the function returns false to specify there was a problem.
	 * 
	 * @param userName userName in the database of the user to delete
	 * @param userPassword password associated to the user
	 * @return true if the operation went successfully. False otherwise.
	 */
	public boolean deleteUser(String userName, String userPassword) {
		String delStudent = "DELETE FROM "+T_STUDENT+" WHERE "+TS_USER+" = ? AND "+TS_PASS+" = ?;";
		String delOrganizer = "DELETE FROM "+T_ORGANIZER+" WHERE "+TO_USER+" = ? AND "+TO_PASS+" = ?;";
		int r;
		
		try {
			// Prepare statement
			PreparedStatement statement = connection.prepareStatement(delOrganizer);
			statement.setString(1, userName);
			statement.setString(2, userPassword);
			
			// Execute and check if it affected one row
			r = statement.executeUpdate();
			if (r == 1) return true;
			
			// if it didn't affect any row, combination username and old password didn't exist in organiser table
			// try student
			
			// Prepare statement
			statement = connection.prepareStatement(delStudent);
			statement.setString(1, userName);
			statement.setString(2, userPassword);
			
			// Execute and check if it affected one row
			r = statement.executeUpdate();
			if (r == 1) return true;
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}	
}
