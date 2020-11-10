package excursionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class ExcursionController {
	private Database db = new Database();
	
	
	/** 
	 * Fetches all excursion registers from database; creates a new multimap object with excursion name as keys and excursion description and
	 * excursion ID's as values.
	 * @return MultiMap<String, String> a MultiMap with Excursion Name as key: Excursion Description and Excursion ID as values.
	 */
	//viewALLExcursion
	public MultiMap<String, String> viewAllExcursion(){
		// get excursion from the database
		 ArrayList<Excursion> excursions = db.getAllExcursion();	
				
		
		// create a multi map to map all excursion 
		MultiMap<String, String> multiMap = new MultiMap<>();
		for (Excursion excursion : excursions) {
			multiMap.put(excursion.getExcursionName(), excursion.getExcursionDesc());
			multiMap.put(excursion.getExcursionName(), ""+excursion.getExcursionID());
		}		
		return multiMap;
	}
	
	/** 
	 * Creates a new Excursion Object by given values("Excursion Name", "Excursion Description").
	 * Stores the object in the database.
	 * 
	 * @param excursionName Excursion name (as String)
	 * @param excursionDesc Excursion Description (as String)
	 * @return boolean (If successfully stores the excursion in database, returns true.)
	 */
	//createExcursion
	public boolean createExcursion(String excursionName, String excursionDesc) {
		Excursion excursion = new Excursion(excursionName, excursionDesc);
		return db.createExcursion(excursion);
	}
	
	/** 
	 * Creates a new Excursion object in the client side and updates the corresponding key in the database.
	 * @param excursionName New Excursion name
	 * @param excursionDesc New Excursion Description.
	 * @param ID Excursion ID of the edited excursion.
	 * @return boolean Returns true if the change in database is succesful.
	 */
	//editExcursion
	public boolean editExcursion(String excursionName, String excursionDesc, int ID) {
		Excursion excursionNew = new Excursion(excursionName, excursionDesc, ID);
		return db.editExcursion(excursionNew);
	}
	
	/** 
	 * New Excursion is created by the values from GUI; corresponding row to this object is deleted from database.
	 * 
	 * @param excursionName Excursion name.
	 * @param excursionDesc Excursion Description
	 * @param ID Excursion ID
	 * @return boolean Returns true if deletion is successful.
	 */
	//cancelExcursion
	public boolean cancelExcursion(String excursionName, String excursionDesc, int ID ) {
		Excursion excursion = new Excursion(excursionName, excursionDesc, ID);
		return db.cancelExcursion(excursion);
	}
	
	
	/** 
	 * Creates a multimap object that contains Excursion names as key; Excursion Description, Excursion ID, Excursion Event ID and date as the value.
	 * 
	 * @return MultiMap<String, String> Returns the multimap with all excursion events.
	 */
	//ExcursionEvent
	//viewAllExcursionEvents
	public MultiMap<String, String> viewAllExcursionEvents(){
		
	    ArrayList<ExcursionEvent> excursionEvents = db.getAllExcursionEvents();
		 
	  	// create a multi map to store all the event details
		MultiMap<String, String> multiMap = new MultiMap<>();
		// associate each name, with the 4 other attributes of the excursion event
		for (ExcursionEvent excursionEvent : excursionEvents) {
			multiMap.put(excursionEvent.getExcursionName(), excursionEvent.getExcursionDesc());
			multiMap.put(excursionEvent.getExcursionName(), ""+excursionEvent.getExcursionID());
			multiMap.put(excursionEvent.getExcursionName(), ""+excursionEvent.getExcursionEventID());
			multiMap.put(excursionEvent.getExcursionName(), excursionEvent.getDate());
		}
		// return the filled multimap
		return multiMap;
	}
	
	/** 
	 * Creates a new excursion event and stores in database.
	 * 
	 * @param excursionName Excursion name
	 * @param excursionDesc	Excursion Description
	 * @param excursionID	Excursion ID
	 * @param date			Date of the excursion event.
	 * @return boolean		Returns true if creation of event is successful in database.
	 */
	//createExcursionEvent
	public boolean createExcursionEvent(String excursionName, String excursionDesc, int excursionID, String date) {
		ExcursionEvent excursionEvent = new ExcursionEvent(excursionName, excursionDesc, excursionID, date);
		return db.createExcursionEvent(excursionEvent);
	}
	
	/** 
	 * Creates a new Excursion event object in the client side and updates the corresponding key in the database
	 * 
	 * @param excursionName Excursion name.
	 * @param excursionDesc Excursion description.
	 * @param excursionEventID Excursion event ID
	 * @param excursionID Excursion ID.
	 * @param date	new Date
	 * @return boolean True if update successful.
	 */
	//editExcursionEventDate
	public boolean editExcursionEventDate(String excursionName, String excursionDesc, int excursionEventID, int excursionID, String date) {
		ExcursionEvent excursionEventDate = new ExcursionEvent(excursionName, excursionDesc, excursionID, excursionEventID, date);
		return db.editExcursionEvent(excursionEventDate);
	}
	
	/** 
	 * Creates a new Excursion event object from the values from GUI; deletes the corresponding row from database.
	 * @param excursionName Excursion name
	 * @param excursionDesc Excursion Description
	 * @param excursionID	Excursion ID
	 * @param excursionEventID 	Event ID
	 * @param date	Date
	 * @return boolean True if deletion is successful.
	 */
	//cancelExcursionEvent
	public boolean cancelExcursionEvent(String excursionName, String excursionDesc, int excursionID, int excursionEventID, String date ) {
		ExcursionEvent excursionEvent = new ExcursionEvent(excursionName, excursionDesc, excursionID, excursionEventID, date);
		return db.cancelExcursionEvent(excursionEvent);
	}

	public ArrayList<Integer> getExcursionIDs() {
		return new ArrayList<Integer>();
	}

	
}
