package excursionManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import persistence.ExcursionDatabase;

/**
 * The ExcursionController Class receives events coming from ExcursionAgent.
 * Processes the information only to the extent necessary to determine the context of the current processing situation
 * 
 * @author purohit_rishi
 * @version 1.0
 */
public class ExcursionController {
	/**
	 * Instance variable for ExcursionDatabase
	 * ArrayList of Excursion's.
	 */

	private static ExcursionDatabase db = new ExcursionDatabase();
	private ArrayList<Excursion> excursionList = db.getAllExcursion();
	
	public ExcursionController() {}
	
	/**
	 * This method allows the User to view all the Excursion's
	 * @return All the Excursions with their Description's and ID's.
	 */
	public MultiMap<String, String> viewAllExcursions(){
		updateExcursionList();
		MultiMap<String, String> multiMap = new MultiMap<>();
		for (Excursion excursion : excursionList) {
			multiMap.put(""+excursion.getExcursionID(), excursion.getExcursionName());
			multiMap.put(""+excursion.getExcursionID(), excursion.getExcursionDesc());
		}		
		return multiMap;
	}
	/**
	 * This method allows the User to view all the upcoming Excursion's
	 * @return All the Active Excursion's
	 */
	public MultiMap<String, String> viewAllActiveExcursion(){
		this.excursionList = db.getActiveExcursions();
		MultiMap<String, String> multiMap = new MultiMap<>();
		for (Excursion excursion : excursionList) {
			multiMap.put(excursion.getExcursionName(), excursion.getExcursionDesc());
			multiMap.put(excursion.getExcursionName(), ""+excursion.getExcursionID());
		}		
		return multiMap;
	}
	/**
	 * This method simply updates Active Excursion List.
	 */
	public void updateActiveExcursionList(){
		this.excursionList = db.getActiveExcursions();
	}
	/**
	 * This method simply updates Excursion List.
	 */
	public void updateExcursionList(){
		this.excursionList = db.getAllExcursion();
	}

	/**
	 * This method allows an Organizer to create an Excursion
	 * @param excursionName name, with which an Excursion should be created
	 * @param excursionDesc description, with which an Excursion should be created
	 * @return boolean value stating if the Excursion Event was created successfully or not.
	 */
	public boolean createExcursion(String excursionName, String excursionDesc) {
		Excursion excursion = new Excursion(excursionName, excursionDesc);
		return db.createExcursion(excursion);
	}
	/**
	 * This method allows an Organizer to edit the details of an Excursion.
	 * @param excursionName name of an Excursion to be edited
	 * @param excursionDesc description of an Excursion to be edited
	 * @param excursionID ID of an Excursion to be edited
	 * @return boolean value stating if the editExcursion was successful or not.
	 */
	public boolean editExcursion(String excursionName, String excursionDesc, int excursionID) {
		updateExcursionList();
		for(Excursion e : this.excursionList){
			System.out.println("e.getExcursionID "+e.getExcursionID()+ " excursionID "+excursionID);
			if(e.getExcursionID() == excursionID){
				e.setExcursionName(excursionName);
				e.setExcursionDesc(excursionDesc);
				boolean result = db.editExcursion(e);
				return result;
			}
		}
		return false;
	}
	/**
	 * This method allows Organizer to cancel excursion
	 * @param excursionID ID of the excursion Organizer wants to cancel.
	 * @return
	 */
	public boolean cancelExcursion(int excursionID ) {
		for(Excursion e : excursionList){
			if(e.getExcursionID() == excursionID){
				return db.cancelExcursion(e);
			}
		}
		return false;
	}
	
	/**
	 * This method allows Organizer to activate/ deactivate an Excursion Event.
	 * @param excursionEventID excursionEventID Organizer wants to change the status of.
	 * @param status Status of an ExcursionEvent(active/inactive).
	 * @return boolean value stating if the Excursion Event is active or inactive.
	 */
	public boolean changeExcursionEventStatus(int excursionEventID, boolean status){
		ArrayList<ExcursionEvent> excursionEvents = db.getAllExcursionEvents();
		for(ExcursionEvent e : excursionEvents){
			if(e.getExcursionEventID() == excursionEventID){
				return db.changeExcursionEventStatus(e, status);
			}
		}
		return false;
	}
	
	/**
	 * This method allows Users to view all the upcoming excursion events.
	 * Excursion Events are sorted ByDate
	 * @return Upcoming Excursion Events sorted ByDate.
	 */
	public MultiMap<String, String> viewAllExcursionEvents(){
		ArrayList<ExcursionEvent> excursionEvents = db.getAllExcursionEvents();
		
		excursionEvents.sort(new ExcursionEvent.ByDate());
		MultiMap<String, String> multiMap = new MultiMap<>();
		for (ExcursionEvent excursionEvent : excursionEvents) {
			multiMap.put(""+excursionEvent.getExcursionEventID(), excursionEvent.getExcursionName());
			multiMap.put(""+excursionEvent.getExcursionEventID(), excursionEvent.getExcursionDesc());
			multiMap.put(""+excursionEvent.getExcursionEventID(), ""+excursionEvent.getExcursionID());
			multiMap.put(""+excursionEvent.getExcursionEventID(), excursionEvent.getDate().toString().substring(0,10));
			multiMap.put(""+excursionEvent.getExcursionEventID(), ""+excursionEvent.getMaxParticipants());
			// get the current number of parcipants
			multiMap.put(""+excursionEvent.getExcursionEventID(), ""+excursionEvent.getConfirmedParticipants());
		}
		return multiMap;
	}
	/**
	 * This method allows Users to view all the excursion events that they have booked.
	 * @param userName of User who wants to see all his/her booked excursion events.
	 * @return Excursion Events that a user has booked.
	 */
	public MultiMap<String, String> viewAllMYExcursionEvents(String userName){
		ArrayList<ExcursionEvent> excursionEvents = db.getAllMyExcursionEvents(userName);	
		
		excursionEvents.sort(new ExcursionEvent.ByDate());
		MultiMap<String, String> multiMap = new MultiMap<>();
		for (ExcursionEvent excursionEvent : excursionEvents) {
			multiMap.put(""+excursionEvent.getExcursionEventID(), excursionEvent.getExcursionName());
			multiMap.put(""+excursionEvent.getExcursionEventID(), excursionEvent.getExcursionDesc());
			multiMap.put(""+excursionEvent.getExcursionEventID(), ""+excursionEvent.getExcursionID());
			multiMap.put(""+excursionEvent.getExcursionEventID(), excursionEvent.getDate().toString().substring(0,10));
			multiMap.put(""+excursionEvent.getExcursionEventID(), ""+excursionEvent.getMaxParticipants());
			multiMap.put(""+excursionEvent.getExcursionEventID(), ""+excursionEvent.getConfirmedParticipants());
		}
		return multiMap;
	}
	
	/**
	 * This method allows an Organizer to create an ExcursionEvent 
	 * @param excursionName Name of Excursion for which Organizer wants to create an event.
	 * @param excursionDesc Description of Excursion for which Organizer wants to create an event.
	 * @param excursionID ID of Excursion for which Organizer wants to create an event.
	 * @param date Date at which Organizer wants to create an excursion event.
	 * @return boolean value stating if the ExcursionEvent creation was successful or not.
	 */
	public boolean createExcursionEvent(String excursionName, String excursionDesc, int excursionID, Timestamp date, int maxParticipants) {
		ExcursionEvent excursionEvent = new ExcursionEvent(excursionName, excursionDesc, excursionID, date, maxParticipants);
		return db.createExcursionEvent(excursionEvent);
	}
	
	/**
	 * This method allows an Organizer to change the Date of an Excursion Event Date.
	 * @param excursionName Name of the Excursion to which the excursionEvent belongs
	 * @param excursionDesc Description of Excursion to which the excursionEvent belongs
	 * @param excursionEventID ID of the Excursion Event that the Organizer wants to modify the Date.
	 * @param excursionID ID of the Excursion to which the excursionEvent belongs
	 * @param date Date of the Excursion to which the excursionEvent belongs
	 * @return boolean value stating if the Date modification was successful or not.
	 */
	public boolean editExcursionEvent(String excursionName, String excursionDesc, int excursionID, int excursionEventID, Timestamp date, int maxParticipants) {
		/*
		 * Eugenio: in the gui organizer should not be able to modify the number of confirmed participants or -MAYBE THIS CHANGES BUT- change the status of the event
		 * 			 with this function and in order to keep things simple both in gui and logic, we force confirmedParticpants = 0 and isActive = false in this case.
		 * 			As specified in the database interface, editExcursionEvent will only modify the Date and maxParticipants of
		 * 			an ExcursionEvent
		 * 
		 * 			It is not important the values we pass for those two attributes. They're ignored by editExcursionEvent()
		 */
		ExcursionEvent excursionEventDate = new ExcursionEvent(excursionName, excursionDesc, excursionID, excursionEventID, date, maxParticipants, 0, false);
		return db.editExcursionEvent(excursionEventDate);
	}

	/**
	 * This method allows an Organizer to cancel an Excursion Event.
	 * @param excursionEventID ID of the Excursion Event that the Organizer wants to cancel.
	 * @return boolean value stating if the excurison ws deleted successfully
	 */
	public boolean cancelExcursionEvent(int excursionEventID) {
		return db.cancelExcursionEvent(excursionEventID);
	}
	/**
	 * This method is used to retrieve an Excursion 
	 * @param excursionName name of teh Excursion we wish to retrieve
	 * @return an Excursion
	 */
	public Excursion getExcursion(String excursionName) {
		return db.getExcursion(excursionName);
	}
	/**
	 * This method retrieves list of excursion events belonging to a particular excursion
	 * @param excursionName name of an Excursion for which the user wants to retrieve excursion events
	 * @return events belonging to an excursion
	 */
	public ArrayList<ExcursionEvent> getExcursionEvents(String excursionName){
		return db.getExcursionEvents(excursionName);
	}
	
}