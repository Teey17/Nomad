package dialogAgent;

import userManager.UserController;

public class UserAgent {
	private static UserController userController = new UserController();
	public boolean login(String username, String password, boolean choice) {

		return userController.login(username, password, choice);
	}
	
	public boolean register(String username, String password) {
		return userController.register(username, password);
	}
	
	public boolean changePassword(String userName, String oldPassword, String newPassword) {
		return userController.changePassword(userName, oldPassword, newPassword);
	}

}
