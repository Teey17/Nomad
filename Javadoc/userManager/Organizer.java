package userManager;

public class Organizer extends User{

	private Database db = new Database();

	
	/** Organizer constructor
	 * @param userName
	 * @param userPassword
	 * @return 
	 */
	public Organizer(String userName, String userPassword) {
		super();
		// TODO Auto-generated constructor stub
		super.userName = userName;
		super.userPassowrd = userPassword;
	}
	
	/** 
	 * @param userName
	 * @param userPassword
	 * @return User
	 */
	@Override
	public User login(String userName, String userPassword) {
		return db.login(userName, userPassword);
	}
}
