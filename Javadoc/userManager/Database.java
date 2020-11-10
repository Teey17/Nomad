package userManager;

public class Database {
	public User login(String userName, String userPassword) {
		User student = new Student("rmp", "rmp");
		User organizer = new Organizer("amp","amp");
		return student;
	}
	public boolean register(String userName, String userPassword) {
		return true;
	}
	
	public boolean changePassword(String userName, String oldPassword, String newPassword) {
		return true;
	}
}
