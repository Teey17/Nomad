package excursionManager;

/**
 * The Excursion class represents Excursions in the NOMAD application.
 * Organizers can Create, Edit, View or Cancel Excursions and ExcursionEvents.
 * Students can view ExcursionEvents.
 * @author purohit_rishi
 * @version 1.0
 */
public class Excursion {
	/**
	 * ExcursionName, ExcursionDescription and ExcursionID of this Excursion.
	 */
	private String excursionName;
	private String excursionDesc;
	private int excursionID;
	/**
	 * creates a new Excursion with Excursion Name and Excursion Description
	 * Initially every excursion has ID zero 
	 * and then every excursion gets unique ID assigned in the database
	 * @param excursionName This Excursions Name
	 * @param excursionDesc This Excursions Description
	 */
	public Excursion(String excursionName, String excursionDesc) {
		super();
		this.excursionName = excursionName;
		this.excursionDesc = excursionDesc;
		this.excursionID = 0;
	}

	/**
	 * creates a new Excursion with ExcursionName, ExcursionDescription and ExcursionID
	 * @param excursionName This Excursions Name
	 * @param excursionDesc This Excursions Description
	 * @param excursionID This Excursions ID
	 */
	public Excursion(String excursionName, String excursionDesc, int excursionID) {
		super();
		this.excursionName = excursionName;
		this.excursionDesc = excursionDesc;
		this.excursionID = excursionID;
	}
	
	/**
	 * Gets the Excursion Name of this Excursion.
	 * @return This Excursions Name
	 */
	public String getExcursionName() {
		return excursionName;
	}
	/**
	 * Changes the Excursion Name of this Excursion.
	 * @param excursionName This Excursion's new Excursion Name.
	 */
	public void setExcursionName(String excursionName) {
		this.excursionName = excursionName;
	}
	/**
	 * Gets the Excursion Description of this Excursion.
	 * @return This Excursions Description
	 */
	public String getExcursionDesc() {
		return excursionDesc;
	}
	/**
	 * Changes the Excursion Description of this Excursion.
	 * @param excursionDescription This Excursion's new Excursion Description.
	 */
	public void setExcursionDesc(String excursionDesc) {
		this.excursionDesc = excursionDesc;
	}
	/**
	 * Gets the Excursion ID of this Excursion.
	 * @return This Excursions ID
	 */
	public int getExcursionID() {
		return excursionID;
	}
	/**
	 * Changes the Excursion_ID of this Excursion.
	 * @param excursionID This Excursion's new Excursion ID.
	 */
	public void setID(int excursionID) {
		this.excursionID = excursionID;
	}
	

}
