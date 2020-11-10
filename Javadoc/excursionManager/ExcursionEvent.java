package excursionManager;



public class ExcursionEvent extends Excursion{
	private String date;
	private int excursionEventID;
	
	
	/** 
	 * Excursion Event constructor.
	 * @param excursionName Excursion Name.
	 * @param excursionDesc Excursion Description
	 * @param excursionID	Excursion ID
	 * @param date Date
	 * @return 
	 */
	public ExcursionEvent(String excursionName, String excursionDesc, int excursionID, String date) {
		super(excursionName, excursionDesc, excursionID);
		this.date = date;
		this.excursionEventID = 0;
	}

	
	/** 
	 * Excursion event constructor. Used by database component. Additionaly has event id.
	 * 
	 * 
	* @param excursionName Excursion Name.
	 * @param excursionDesc Excursion Description
	 * @param excursionID	Excursion ID
	 * @param excursionEventID Excursion Event ID
	 * @param date Date
	 * @return 
	 */
	public ExcursionEvent(String excursionName, String excursionDesc, int excursionID, int excursionEventID, String date) {
		super(excursionName, excursionDesc, excursionID);
		this.date = date;
		this.excursionEventID = excursionEventID;
	}
	
	
	/** 
	 * Getter for date.
	 * @return String
	 */
	public String getDate() {
		return date;
	}
	
	/** 
	 * Setter for date.
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	
	/** Getter for ExcursionEventID
	 * @return int
	 */
	public int getExcursionEventID() {
		return excursionEventID;
	}

}
