package userManager;

public class Student extends User{

	private Database db = new Database();

	
	/** Student Constructor.
	 * @param userName
	 * @param userPassword
	 * @return 
	 */
	public Student(String userName, String userPassword) {
		super();
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