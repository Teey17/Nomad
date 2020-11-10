package dialogAgent;

import java.sql.Timestamp;
import bookingManager.BookingController;
import clientNomad.ClientNomad;
import excursionManager.MultiMap;

public class BookingAgent {
	
	/**
	 * Instance of Booking controller
	 */
	private static BookingController bookingController = new BookingController();
	
	/**
	 * Instance of ClientNomad socket interface
	 */
	private static ClientNomad soc = ClientNomad.getInstance();
	
	/**
	 * Changes the PaymentStatus of Booking made by user.
	 * Only the Organizer is allowed to make the Change to Payment Status.
	 * Status can either be "Paid" or "Unpaid".
	 * @param userName User Name of User making the Booking.
	 * @param currentStatus Current Status of Booking.
	 * @param excursionEventID ExcursionEventID for which Booking was made.
	 * @return boolean value stating the new Status("Paid" or "Unpaid") of Payment.
	 */
	public boolean changePaymentStatus(String userName, boolean currentStatus, int excursionEventID) {
		return (boolean) soc.invokeFunction(new Object[] {"changePaymentStatus", userName, currentStatus, excursionEventID});
	}
	/**
	 * This method represents the ArrayList of Participants for given excursionEventID.
	 * List is Sorted as per PaymentStatus of Bookings in the List.
	 * @param excursionEventID ExcursionEventID of all the participants in the list.
	 * @return sorted ArrayList of all the participants for given excursionEventID.
	 */
	public MultiMap<String, String> viewAllParticipants(int excursionEventID){
		MultiMap<String, String> temp;// = bookingController.viewAllParticipants(excursionEventID);
		temp = (MultiMap<String, String>) soc.invokeFunction(new Object[] {"viewAllParticipants", excursionEventID});		
		return temp;
	}
	/**
	 * This method represents the number of participant for given ExcursionEventID.
	 * @param excursionEventID for the Organizer wants to check the total number of participants.
	 * @return number of participants for given excursionEventID.
	 */
	public int numberOfParticipants(int excursionEventID) {
		return (int) soc.invokeFunction(new Object[] {"numberOfParticipants", excursionEventID});
	}
	/**
	 * This method allows the User to Book an excursion event.
	 * @param userName userName of user who wants to make the Booking.
	 * @param excursionEventID excursionEventID for which the User wants to make the Booking.
	 * @param bookingDate Date on which the User Book's an excursion event.
	 * @return
	 */
	public boolean bookExcursionEvent(int excursionEventID, String userName,  Timestamp bookingDate) {
		return (boolean) soc.invokeFunction(new Object[] {"bookExcursionEvent", excursionEventID, userName, bookingDate});
	}
	/**
	 * This method allows the Student to cancel their Booking and 
	 * It also allows the Organizer to cancel the Booking of those whose participants who failed to make their payments within deadline.
	 * @param userName userName of the User who made the booking.
	 * @param excursionEventID excursionEventID for which the User made booking.
	 * @param bookingDate Date on which the User made Booking.
	 * @param b 
	 * @return
	 */
	public boolean cancelBookExcursionEvent(String userName, int excursionEventID, Timestamp bookingDate) {
		return (boolean) soc.invokeFunction(new Object[] {"cancelBookExcursionEvent", userName, excursionEventID, bookingDate});
	}

}
