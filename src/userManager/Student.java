package userManager;

/**
 * The Student class represents User:Student at THU Ulm.
 * A Student can register or login into NOMAD application and can register or unregister from excursions.
 * @author purohit_rishi
 * @version 1.0
 */
public class Student extends User{
	/**
	 *
	 * Creates a new Student with the given userName and Password.
	 * @param userName This Student's userName 
	 * @param userPassword This Student's Password
	 */
	public Student(String userName, String userPassword){
		super();
		super.userName = userName;
		super.userPassword = userPassword;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User login(String userName, String userPassword, boolean userChoice) {
		return userdatabase.login(userName, userPassword, userChoice);
	}

}