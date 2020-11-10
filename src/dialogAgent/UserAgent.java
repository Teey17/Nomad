package dialogAgent;

import clientNomad.ClientNomad;
import userManager.UserController;

public class UserAgent {
	/**
	 * Instance of User controller
	 */
	private static ClientNomad soc = ClientNomad.getInstance();
	
	/**
	 * This method allows User of NOMAD to login
	 * @param userName userName of User who wants to login
	 * @param userPassword password of User who wants to login
	 * @param userChoice userChoice informs NOMAD if the User who wants to login is Student or Organizer.
	 * @return boolean stating if the login was successful or not.
	 */
	public boolean login(String username, String password, boolean userChoice) {
		return (boolean) soc.invokeFunction(new Object[] {"login", username, password, userChoice});
	}
	
	/**
	 * This method lets User register into the NOMAD.
	 * 
	 * @param userName This User's userName. It is a unique value.
	 * @param userPassword This User's Password
	 * @return boolean This User's registration status.
	 */
	public boolean register(String username, String password, boolean userChoice) {
		return (boolean) soc.invokeFunction(new Object[] {"register", username, password, userChoice});
	}
	
	/**
	 * This method allows User to change his/her password.
	 * @param userName userName of the User.
	 * @param oldPassword oldPassword of this User.
	 * @param newPassword newPassword of this User.
	 * @return boolean stating if the Password was changed successfully or not.
	 */
	public boolean changePassword(String userName, String oldPassword, String newPassword) {
		return (boolean) soc.invokeFunction(new Object[] {"changePassword", userName, oldPassword, newPassword});
	}
}
