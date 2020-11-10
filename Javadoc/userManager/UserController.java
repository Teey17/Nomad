package userManager;

public class UserController {
	
	private Database db = new Database();
	
	/** 
	 * Login proccess for users.
	 * 
	 * @param userName	Username
	 * @param userPassword UserPassword
	 * @param userChoice Organizer/Student login.
	 * @return boolean
	 */
	public boolean login(String userName, String userPassword, boolean userChoice) {
		if(userChoice == true) {
			User student = new Student(userName,userPassword );
			if(student.login(userName, userPassword) != null){
				return true;
			}else {
				return false;
			}
		} else {
			User organizer = new Organizer(userName,userPassword);
			if(organizer.login(userName, userPassword) != null){
				return true;
			}else {
				return false;
			}
		}

	}
	
	/** 
	 * Registration for new Student users.
	 * @param userName HS username
	 * @param userPassword password
	 * @return boolean True if registeration is sucessful.
	 */
	public boolean register(String userName, String userPassword) {
		User student = new Student(userName,userPassword);
		return student.register(userName, userPassword);
	}
	
	
	/** 
	 * Password Change function for user.
	 * @param userName Username
	 * @param oldPassword Old password of the user
	 * @param newPassword	New desired password.
	 * @return boolean True if change is sucessful.
	 */
	public boolean changePassword(String userName, String oldPassword, String newPassword) {
		return db.changePassword(userName, oldPassword, newPassword);
	}
}
