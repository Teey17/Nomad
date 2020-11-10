package userManager;

import persistence.UserDatabase;

/**
 * 
 * The Abstract User class represents User types for the NOMAD application.
 * User can be a Student or an Organizer.
 * 
 * @author purohit_rishi
 * @version 1.0
 *
 */
public abstract class User {
	/**
	 * The userName of this User.
	 */
	protected String userName;
	protected String userPassword;
	
	protected UserDatabase userdatabase = new UserDatabase();

	/**
	 * Method Login allows User to login into the NOMAD.
	 * @param userName This User's userName
	 * @param userPassword This User's Password
	 */
	public abstract User login(String userName, String userPassword, boolean userChoice);

	/**
	 * This method lets User register into the NOMAD.
	 * 
	 * @param userName This User's userName. It is a unique value.
	 * @param userPassword This User's Password
	 * @return boolean This User's registration status.
	 */
	public boolean register(String userName, String userPassword, boolean userChoice) {
		return userdatabase.register(userName, userPassword, userChoice);
	}
	/**
	 * This method allows User to change his/her password.
	 * @param userName userName of the User.
	 * @param oldPassword oldPassword of this User.
	 * @param newPassword newPassword of this User.
	 * @return boolean stating if the Password was changed successfully or not.
	 */
	public boolean changePassword(String userName, String oldPassword, String newPassword) {
		return userdatabase.changePassword(userName, oldPassword, newPassword);
	}
	

	/**
	 * Gets the userName of this User.
	 * @return This User's userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Changes the userName of this User.
	 * @param userName This User's new userName.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


}
