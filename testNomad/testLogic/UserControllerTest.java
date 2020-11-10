/**
 * 
 */
package testLogic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.Database;
import userManager.UserController;

/**
 * @author rmp
 *
 */
class UserControllerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Database.connect();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link userManager.UserController#UserController()}.
	 */
	@Test
	void testUserController() {
		UserController uc = new UserController();
		assertNotNull(uc);
	}

	/**
	 * Test method for {@link userManager.UserController#login(java.lang.String, java.lang.String, boolean)}.
	 */
	@Test
	void testLogin() {
		String name = "fra";
		String password = "tra";
		boolean choice = true;
		UserController uc = new UserController();
		assertNotNull(uc);
		uc.register(name, password, choice);
		assertTrue(uc.login(name, password, choice), "Login successful");
	}

	/**
	 * Test method for {@link userManager.UserController#register(java.lang.String, java.lang.String, boolean)}.
	 */
	@Test
	public void testRegister() {
		String name = "art";
		String password = "art";
		boolean choice = false;
		UserController uc = new UserController();
		assertNotNull(uc);
		assertFalse(uc.register(name, password, choice), "Register successful");
	}

	/**
	 * Test method for {@link userManager.UserController#changePassword(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testChangePassword() {
		String name = "fra";
		String password = "fra";
		String newp = "tra";
		boolean choice = true;
		UserController uc = new UserController();
		assertNotNull(uc);
		uc.register(name, password, choice);
		assertTrue(uc.login(name, password, choice), "Login successful");
		assertTrue(uc.changePassword(name, password, newp), "Change Password successful");
	}

}
