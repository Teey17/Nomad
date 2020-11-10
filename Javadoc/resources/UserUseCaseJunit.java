package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import comps.*;

class UserUseCaseJunit {

	@org.junit.jupiter.api.Test
	void studentRegistersWithAppropiateCredentials() {
		try {
			Database db = Database.getDatabaseInstance();
			
			//Actual data in the database as for 23/11/19
			String username = "Test1";	//Existing user
			String password = "54321";
			
			//Assert registration of existing student should be false
			assertTrue(db.register(username, password), "Student registration should return true.");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "Error instantiating the database driver.");
		}
	}
	
	@org.junit.jupiter.api.Test
	void studentCannotRegisterWithStudentsUsername(){
		try {
			Database db = Database.getDatabaseInstance();
			
			//Actual data in the database as for 23/11/19
			String username = "Rishi";	//Existing user
			String password = "54321";
			
			//Assert registration of existing student should be false
			assertFalse(db.register(username, password), "Trying same content as in database.");
			
			assertFalse(db.register(username.toLowerCase(), password), "Trying input all in lower case.");
			
			assertFalse(db.register(username.toUpperCase(), password), "Trying input all in upper case.");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "Error instantiating the database driver.");
		}
	}
	
	@org.junit.jupiter.api.Test
	void studentCannotRegisterWithOrganizersUsername() {
		try {
			Database db = Database.getDatabaseInstance();
			
			//Actual data in the database as for 23/11/19
			String username = "Guney";	//existing organiser
			String password = "kekeke";
			
			//Assert registration of existing user should be false
			assertFalse(db.register(username, password), "Trying same content as in database.");
			
			assertFalse(db.register(username.toLowerCase(), password), "Trying input all in lower case.");
			
			assertFalse(db.register(username.toUpperCase(), password), "Trying input all in upper case.");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "Error instantiating the database driver.");
		}
	}
	
	@org.junit.jupiter.api.Test
	void organizerLoginReturnsOnlyOrganizerObject() {
		try {
			Database db = Database.getDatabaseInstance();
			
			//Actual data in the database as for 23/11/19
			String username = "Guney";	//existing organiser
			String password = "kekeke";
			
			User user = db.loginCheck(username, password);
			//Assert registration of existing user should be false
			assertNotNull(user, "User is registered, function should not return null.");
			assertTrue(user instanceof Organizer, "The returning object should be organizer.");
			assertNull(db.loginCheck(username, "wrongpassword"), "Should return null if password is wrong");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "Error instantiating the database driver.");
		}
	}

	@org.junit.jupiter.api.Test
	void studentLoginReturnsOnlyStudentObject() {
		try {
			Database db = Database.getDatabaseInstance();
			
			//Actual data in the database as for 23/11/19
			String username = "Nelson";	//existing organiser
			String password = "asdfg";
			
			User user = db.loginCheck(username, password);
			//Assert registration of existing user should be false
			assertNotNull(user, "User is registered, function should not return null.");
			assertTrue(user instanceof Student, "The returning object should be student.");
			assertNull(db.loginCheck(username, "wrongpassword"), "Should return null if password is wrong");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "Error instantiating the database driver.");
		}
	}
}
