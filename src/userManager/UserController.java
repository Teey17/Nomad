package userManager;

import persistence.UserDatabase;

/**
 * The UserController Class receives events coming from UserAgent.
 * Processes the information only to the extent necessary to determine the context of the current processing situation
 * 
 * @author purohit_rishi
 * @version 1.0
 */
public class UserController {
	/**
	 * instance of User class.
	 */
	private UserDatabase usrdb = new UserDatabase();

	/**
	 * This method allows User of NOMAD to login
	 * @param userName userName of User who wants to login
	 * @param userPassword password of User who wants to login
	 * @param userChoice userChoice informs NOMAD if the User who wants to login is Student or Organizer.
	 * @return boolean stating if the login was successful or not.
	 */
	public boolean login(String userName, String userPassword, boolean userChoice){
		if(userChoice == true) {
			User student = new Student(userName,userPassword );
			if(student.login(userName, userPassword, userChoice) != null){
				return true;
			}else {
				return false;
			}
		} else {
			User organizer = new Organizer(userName,userPassword);
			if(organizer.login(userName, userPassword, userChoice) != null){
				return true;
			}else {
				return false;
			}
		}
	}
	/**
	 * This method lets User register into the NOMAD.
	 * 
	 * @param userName This User's userName. It is a unique value.
	 * @param userPassword This User's Password
	 * @return boolean This User's registration status.
	 */
	public boolean register(String userName, String userPassword, boolean userChoice){
		User student = new Student(userName, userPassword);
		return student.register(userName, userPassword, userChoice);
	}
	/**
	 * This method allows User to change his/her password.
	 * @param userName userName of the User.
	 * @param oldPassword oldPassword of this User.
	 * @param newPassword newPassword of this User.
	 * @return boolean stating if the Password was changed successfully or not.
	 */
	public boolean changePassword(String userName, String oldPassword, String newPassword) {
		return usrdb.changePassword(userName, oldPassword, newPassword);
	}
}
