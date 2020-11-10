package userManager;

/**
 * The Organizer class represents User:Organizer at THU Ulm.
 * An Organizer can register or login into NOMAD application and manage(create, edit, delete) excursions.
 * @author purohit_rishi
 * @version 1.0
 */
public class Organizer extends User{
	/**
	 *
	 * Creates a new Organizer with the given userName and Password.
	 *
	 * @param userName This Organizer's userName 
	 * @param userPassword This Organizer's Password
	 */
	public Organizer(String userName, String userPassword) {
		super();
		// TODO Auto-generated constructor stub
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
