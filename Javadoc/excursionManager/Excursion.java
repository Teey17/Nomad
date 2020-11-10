package excursionManager;

public class Excursion {
	private String excursionName;
	private String excursionDesc;
	private int excursionID;
	
	
	/** 
	 * Excursion object constructor
	 * @param excursionName
	 * @param excursionDesc
	 * @return 
	 */
	public Excursion(String excursionName, String excursionDesc) {
		super();
		this.excursionName = excursionName;
		this.excursionDesc = excursionDesc;
		this.excursionID = 0;
	}

	
	/** 
	 * An excursion object constructor used by database with id attribute.
	 * @param excursionName
	 * @param excursionDesc
	 * @param excursionID
	 * @return 
	 */
	public Excursion(String excursionName, String excursionDesc, int excursionID) {
		super();
		this.excursionName = excursionName;
		this.excursionDesc = excursionDesc;
		this.excursionID = excursionID;
	}
	
	
	/** 
	 * @return String
	 */
	public String getExcursionName() {
		return excursionName;
	}

	
	/** 
	 * @param excursionName
	 */
	public void setExcursionName(String excursionName) {
		this.excursionName = excursionName;
	}

	
	/** 
	 * @return String
	 */
	public String getExcursionDesc() {
		return excursionDesc;
	}

	
	/** 
	 * @param excursionDesc
	 */
	public void setExcursionDesc(String excursionDesc) {
		this.excursionDesc = excursionDesc;
	}
	
	
	/** 
	 * @return int
	 */
	public int getExcursionID() {
		return excursionID;
	}

	
	/** 
	 * @param excursionID
	 */
	public void setID(int excursionID) {
		this.excursionID = excursionID;
	}

}
