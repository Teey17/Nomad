package excursionManager;

import java.sql.Timestamp;
import java.util.Comparator;


/**
 * The Excursion Event class represents Excursion Events in the NOMAD application.
 * One Excursion can have one or more Excursion Events.
 * Organisers can Create, Edit, View or Cancel Excursions and ExcursionEvents.
 * Students can view ExcursionEvents.
 * @author purohit_rishi
 * @version 1.0
 */
public class ExcursionEvent extends Excursion{
	/**
	 * Excursion Event Date and Excursion Event ID of this Excursion Event.
	 */
	private Timestamp date;
	private int maxParticipants;
	private int excursionEventID;
	private int confirmedParticipants;
	private boolean isActive;			// Status
	/**
	 * creates a new Excursion Event with Excursion Name, Excursion Description, Excursion ID and Date
	 * 
	 * @param excursionName This Excursions Name
	 * @param excursionDesc This Excursions Description
	 * @param excursionID This ExcursionEvent's ID
	 * @param date This Excursion Events Date
	 */
	public ExcursionEvent(String excursionName, String excursionDesc, int excursionID, Timestamp date, int maxParticipants) {
		super(excursionName, excursionDesc, excursionID);
		this.maxParticipants = maxParticipants;
		this.date = date;
		this.excursionEventID = 0;
		this.confirmedParticipants = 0;
		this.isActive = true;
	}
	/**
	 * creates a new Excursion Event with Excursion Name, Excursion Description, Excursion ID, Excursion Event ID and date.
	 * @param excursionName This Excursion's name
	 * @param excursionDesc This Excursion's Description
	 * @param excursionID This Excursion's ID
	 * @param excursionEventID This ExcursionEvent's ID
	 * @param date This Excursion Events Date
	 */
	public ExcursionEvent(String excursionName, String excursionDesc, int excursionID, int excursionEventID, Timestamp date, int maxParticipants, int confirmedParticipants, boolean isActive) {
		super(excursionName, excursionDesc, excursionID);
		this.maxParticipants = maxParticipants;
		this.date = date;
		this.excursionEventID = excursionEventID;
		this.confirmedParticipants = confirmedParticipants;
		this.isActive = isActive;
	}
	/**
	 * Gets the date of Excursion Event
	 * @return This Excursion Event's Date 
	 */
	public Timestamp getDate() {
		return date;
	}
	/**
	 * Changes the date of this Excursion's Event
	 * @param date This Excursion Event's new Date
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}
	/**
	 * Gets the ID of Excursion Event.
	 * @return This Excursion Event's ID
	 */
	public int getExcursionEventID() {
		return excursionEventID;
	}
	/**
	 * Changes the ID of this Excursion Event
	 * @param excursionEventID This Excursion Event's new ID.
	 */
	public void setExcursionEventID(int excursionEventID) {
		this.excursionEventID = excursionEventID;
	}
	/**
	 * Gets the maximum number of participants that are allowed in this excursion event
	 * @return this excursion events maximum number of allowed participants
	 */
	public int getMaxParticipants() {
		return maxParticipants;
	}
	/**
	 * Changes the maximum number of participants an excursion can have
	 * @param maxParticipants
	 */
	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}
	/**
	 * Gets number of participants who have confirmed their payment
	 * @return this events number of confirmed pariticipants
	 */
	public int getConfirmedParticipants() {
		return confirmedParticipants;
	}
	/**
	 * Changes number of participants for an excursion event
	 * @param confirmedParticipants This excursion event's new number of confirmed participants.
	 */
	public void setConfirmedParticipants(int confirmedParticipants) {
		this.confirmedParticipants = confirmedParticipants;
	}

	/**
	 * Sets the ExcursionEvent status to active or inactive
	 * @param isActive true to set the Event active. False to set it inactive.
	 */
	public void setStatus(boolean isActive) {
		this.isActive = isActive;
	}
	
	/**
	 * Returns the status of the excursionEvent
	 * @return true if the ExcursionEvent is active. False otherwise.
	 */
	public boolean isActive() {
		return this.isActive;
	}
	
	/**
	 * Nested Class ByDate compares two ExcursionEvent Object's.
	 * It also helps to sort ExcursionEvents ByDate (i.e)
	 * @author purohit_rishi
	 */
	public static class ByDate implements Comparator<ExcursionEvent>{

		@Override
		/**
		 * This method simply compares two ExcursionEvent Objects based on the Dates.
		 */
		public int compare(ExcursionEvent o1, ExcursionEvent o2) {
			return o1.getDate().compareTo(o2.getDate());
		}
	}	

}
