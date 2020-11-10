package userManager;

public abstract class User {
	protected String userName;
	protected String userPassowrd;

	private Database db = new Database();

	
	/** 
	 * Abstract login function.
	 * @param userName
	 * @param userName
	 * @param userPassword
	 * @return User
	 */
	public abstract User login(String userName, String userPassword);

	
	/** 
	 * User register function.
	 * @param userName
	 * @param userPassword
	 * @return boolean True if registration is successful.
	 */
	public boolean register(String userName, String userPassword) {
		return db.register(userName, userPassword);
	}

	
	/** 
	 * @return String
	 */
	public String getUserName() {
		return userName;
	}

	
	/** 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	/** 
	 * @return String
	 */
	public String getUserPassowrd() {
		return userPassowrd;
	}

	
	/** 
	 * @param userPassowrd
	 */
	public void setUserPassowrd(String userPassowrd) {
		this.userPassowrd = userPassowrd;
	} 


}
