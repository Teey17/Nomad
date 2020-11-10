package dialogAgent;

import java.sql.Timestamp;

import clientNomad.ClientNomad;
import excursionManager.ExcursionController;
import excursionManager.MultiMap;

public class ExcursionAgent {
	
	/**
	 * Instance of Excursion controller
	 */
	private static ClientNomad soc = ClientNomad.getInstance();
	
	/**
	 * This method allows the User to view all the Excursion's
	 * @return All the Excursions with their Description's and ID's.
	 */
	public MultiMap<String, String> viewAllExcursions(){
		return (MultiMap<String, String>) soc.invokeFunction(new Object[] {"viewAllExcursions"});
    }
	
	/**
	 * This method allows the User to view all the upcoming Excursion's
	 * @return All the Active Excursion's
	 */
	public MultiMap<String, String> viewAllActiveExcursions(){
		return (MultiMap<String, String>) soc.invokeFunction(new Object[] {"viewAllActiveExcursion"});
    }
	
	/**
	 * This method allows an Organizer to create an Excursion
	 * @param excursionName name, with which an Excursion should be created
	 * @param excursionDesc description, with which an Excursion should be created
	 * @return boolean value stating if the Excursion Event was created successfully or not.
	 */
	public boolean createExcursion(String excursionName, String excursionDesc) {
		return (boolean) soc.invokeFunction(new Object[] {"createExcursion", excursionName, excursionDesc});
	}

	/**
	 * This method allows Organizer to cancel excursions
	 * @param excursionName name of an Excursion to be cancelled
	 * @param excursionDesc description of an Excursion to be cancelled
	 * @param excursionID ID of an Excursion to be cancelled
	 * @return boolean value stating if the Excursion Event was successfully cancelled or not.
	 */
	public boolean cancelExcursion(int excursionID) {
		return (boolean) soc.invokeFunction(new Object[] {"cancelExcursion", excursionID});
	}
	
	/**
	 * This method allows an Organizer to edit the details of an Excursion.
	 * @param excursionName name of an Excursion to be edited
	 * @param excursionDesc description of an Excursion to be edited
	 * @param excursionID ID of an Excursion to be edited
	 * @return boolean value stating if the editExcursion was successful or not.
	 */
	public boolean editExcursion(String excursionName, String excursionDesc, int ID) {
		return (boolean) soc.invokeFunction(new Object[] {"editExcursion", excursionName, excursionDesc, ID});
	}
	
	/**
	 * This method allows Users to view all the upcoming excursion events.
	 * Excursion Events are sorted ByDate
	 * @return Upcoming Excursion Events sorted ByDate.
	 */
	public MultiMap<String, String> viewAllExcursionEvents(){
		MultiMap<String, String> temp;
		temp = (MultiMap<String, String>) soc.invokeFunction(new Object[] {"viewAllExcursionEvents"});
    	return temp;
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
		return (boolean) soc.invokeFunction(new Object[] {"createExcursionEvent", excursionName, excursionDesc,
							excursionID, date, maxParticipants});
	}
	
	/**
	 * his method allows an Organizer to cancel an Excursion Event.
	 * @param excursionEventID ID of the Excursion Event that the Organizer wants to cancel.
	 * @return
	 */
	public boolean cancelExcursionEvent(int excursionEventID) {
		return (boolean) soc.invokeFunction(new Object[] {"cancelExcursionEvent", excursionEventID});
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
		return (boolean) soc.invokeFunction(new Object[] {"editExcursionEvent", excursionName, excursionDesc,
						excursionID, excursionEventID, date, maxParticipants});
	}
	
	/**
	 * This method allows Users to view all the excursion events that they have booked.
	 * @param userName of User who wants to see all his/her booked excursion events.
	 * @return Excursion Events that a user has booked.
	 */
	public MultiMap<String, String> viewAllMYExcursionEvents(String userName){
		MultiMap<String, String> temp;// = excursionController.viewAllMYExcursionEvents(userName);
		temp = (MultiMap<String, String>) soc.invokeFunction(new Object[] {"viewAllMYExcursionEvents", userName});
    	return temp;
    }

	/**
	 * This method allows the User to view all the upcoming Excursion's
	 * @return All the Active Excursion's
	 */
	public MultiMap<String, String> viewAllActiveExcursion(){
		MultiMap<String, String> temp;// = excursionController.viewAllActiveExcursion();
		temp = (MultiMap<String, String>) soc.invokeFunction(new Object[] {"viewAllActiveExcursion"});
    	return temp;
	}
	
	/**
	 * This method allows Organizer to activate/ deactivate an Excursion Event.
	 * @param excursionEventID excursionEventID Organizer wants to change the status of.
	 * @param status Status of an ExcursionEvent(active/inactive).
	 * @return boolean value stating if the Excursion Event is active or inactive.
	 */
	public boolean changeExcursionEventStatus(int excursionEventID, boolean status){
		return (boolean) soc.invokeFunction(new Object[] {"changeExcursionEventStatus", excursionEventID, status});
	}
	
//	/**
//	 * This method allows Organizer to activate/ deactivate an Excursion.
//	 * @param excursionID excursionID Organizer wants to change the status of.
//	 * @param status Status of an Excursion(active/inactive).
//	 * @return boolean value stating if the Excursion is active or inactive.
//	 */
//	public boolean changeExcursionStatus(int excursionID, int status){
//		return excursionController.changeExcursionStatus(excursionID, status);
//	}
}