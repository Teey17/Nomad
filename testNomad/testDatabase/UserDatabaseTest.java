package testDatabase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;

import persistence.Database;
import persistence.UserDatabase;
import userManager.Organizer;
import userManager.Student;

@TestInstance(Lifecycle.PER_CLASS)
class UserDatabaseTest {

	/**
	 * This data is contained in the database to test the class.
	 * Date: 30/11/19
	 */
	private UserDatabase db;
	// UserNames for different types of testing
	private String studentName = "student1";
	private String invalidStudentName = "student2";
	private String organizerName = "organizer1";
	private String invalidOrganizerName = "organizer2";
	private String changeOrg = "changeme";
	private String changeStd = "changeme2";
	private String stdTestName = "Test1";	//Not existing student
	private String orgTestName = "Test2";	//Not existing organizer
	// Passwords for different types of testing
	private String pass2Chg = "pass";
	private String password = "password";
	private String wrongPassword = "wrong";
	private String newPassword = "new";
	private String userTestPassword = "54321";
	//Boolean decision variables
	private boolean student_choice = true;
	private boolean organizer_choice = false;
	
		
	
	@BeforeAll
	void setUp() throws SQLException {
		Database.connect();
		db = new UserDatabase();
	}
	
	@Test
	@Order(1)
	void registerWithUnexistingUserReturnsTrue() {
		//Assert registration of unexisting student, later attempt to register him again
		assertTrue(db.register(stdTestName, userTestPassword, true), "Student registration with unexisting user should return true.");
	}
	
	@Test
	@Order(2)
	void registerWithExistingUserReturnsFalse() {
		//Assert student registration of existing student or organizer
		assertFalse(db.register(studentName, password, true), "Student registration with existing student username returns false");
		assertFalse(db.register(organizerName, password, true), "Student registration with existing organizer username returns false");
		
		assertFalse(db.register(studentName, password, false), "Student registration with existing student username returns false");
		assertFalse(db.register(organizerName, password, false), "Student registration with existing organizer username returns false");
	}
	
	@Test
	@Order(3)
	void userLogsInWithInvalidCredentialsReturnsNull(){
		//Student tries to log in with invalid credentials
		//Invalid username
		assertNull(db.login(invalidStudentName, password, student_choice), "An unexisting Student username should return a null object");
		//Invalid password
		assertNull(db.login(studentName, wrongPassword, student_choice), "Login with the wrong password should return a null object");
		
		//Organizer tries to log in with invalid credentials
		//Invalid username
		assertNull(db.login(invalidOrganizerName, password, organizer_choice), "An unexisting organizer username should return a null object");
		//Invalid password
		assertNull(db.login(organizerName, wrongPassword, organizer_choice), "Login with the wrong password should return a null object");
	}
	
	@Test
	@Order(4)
	void userLogsInWithValidCredentialsReturnsRightUser() {			
		//Student logging in with right credentials should return a Student object
		assertNotNull(db.login(studentName, password, student_choice), "Logging in with correct credentials should not return null");
		assertTrue(db.login(studentName, password, student_choice) instanceof Student, "Logging in with correct credentials should return a Student object");
		//Test different inputs from user
		assertTrue(db.login(studentName.toUpperCase(), password, student_choice) instanceof Student, "Trying input all in upper case.");
		assertTrue(db.login(studentName.toLowerCase(), password, student_choice) instanceof Student, "Trying input all in upper case.");
		
		//Organizer logging in with right credentials should return a Student object
		assertNotNull(db.login(organizerName, password, organizer_choice), "Logging in with correct credentials should not return null");
		assertTrue(db.login(organizerName, password, organizer_choice) instanceof Organizer, "Logging in with correct credentials should return a Organizer object");
		//Test different inputs from user
		assertTrue(db.login(organizerName.toUpperCase(), password, organizer_choice) instanceof Organizer, "Trying input all in upper case.");
		assertTrue(db.login(organizerName.toLowerCase(), password, organizer_choice) instanceof Organizer, "Trying input all in upper case.");
	}
	
	@Test
	@Order(5)
	void userChangesPasswordWithInvalidOldPasswordReturnFalse() {
		// Try to change user password with wrong old password
		assertFalse(db.changePassword(changeStd, wrongPassword, newPassword), "Student should not change password if old password is wrong");
		assertFalse(db.changePassword(changeOrg, wrongPassword, newPassword), "Organizer should not change password if old password is wrong");
	}
	
	@Test
	@Order(6)
	void userChangesPasswordWithValidOldPasswordReturnTrue() {
		// Try to change user password with right old password
		assertTrue(db.changePassword(changeStd, pass2Chg, newPassword), "Student should be able to change password when old password is provided");
		assertTrue(db.changePassword(changeOrg, pass2Chg, newPassword), "Organizer should be able to change password when old password is provided");
	}
	
	@Test
	@Order(7)
	void userDeletesItsAccountWithInvalidCredentialsReturnsFalse() {
		assertFalse(db.deleteUser(stdTestName, "wrong"), "Username with wrong password should return false");
		assertFalse(db.deleteUser(orgTestName, "wrong"), "Username with wrong password should return false");
		assertFalse(db.deleteUser("Wrong", "Whatever"), "Wrong username should return false");
	}
	
	@Test
	@Order(8)
	void userDeletesItsAccountWithValidCredentialsReturnsTrue() {
		assertTrue(db.deleteUser(stdTestName, userTestPassword), "Username with right password should return true");
		assertTrue(db.deleteUser(orgTestName, userTestPassword), "Username with right password should return true");
	}
	
	@AfterAll
	void restoreDatabaseContent() throws SQLException {
		db.changePassword(changeStd, newPassword, pass2Chg); //Restore student password
		db.changePassword(changeOrg, newPassword, pass2Chg); //Restore organiser password
		Database.close();
	}
	
}
