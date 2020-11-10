package bookingManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import excursionManager.MultiMap;
import persistence.BookingDatabase;
/**
 * The BookingController Class receives events coming from BookingAgent.
 * Processes the information only to the extent necessary to determine the context of the current processing situation
 * 
 * @author purohit_rishi
 * @version 1.0
 */
public class BookingController {
	/**
	 * Instance of BookingDatabase to store the Booking into Database.
	 */
	private BookingDatabase bookdb;
	/**
	 * Creates a new BookingController.
	 * Instantiates BookingDatabase.                                                            
	 */
	public BookingController() {
		this.bookdb = new BookingDatabase();
	}
	/**
	 * Changes the PaymentStatus of Booking made by user.
	 * Only the Organizer is allowed to make the Change to Payment Status.
	 * Status can either be "Paid" or "Unpaid".
	 * @param userName User Name of User making the Booking.
	 * @param currentStatus Current Status of Booking.
	 * @param excursionEventID ExcursionEventID for which Booking was made.
	 * @return boolean value stating the new Status("Paid" or "Unpaid") of Payment.
	 */
	public boolean changePaymentStatus(String userName, boolean paymentStatus, int excursionEventID) {
		return bookdb.changePaymentStatus(userName, paymentStatus, excursionEventID);
	}
	/**
	 * This method represents the ArrayList of Participants for given excursionEventID.
	 * List is Sorted as per PaymentStatus of Bookings in the List.
	 * @param excursionEventID ExcursionEventID of all the participants in the list.
	 * @return sorted ArrayList of all the participants for given excursionEventID.
	 */
	public MultiMap<String, String> viewAllParticipants(int excursionEventID){
		ArrayList<Booking> participants = bookdb.getParticipants(excursionEventID);
		participants.sort(new Booking.ByPayment());
		MultiMap<String, String> multiMap = new MultiMap<>();
		for (Booking book : participants) {
			multiMap.put(book.getUserName(), String.valueOf(book.isPaymentStatus()));
			multiMap.put(book.getUserName(), book.getBookingDate().toString());
		}		
		return multiMap;
	}

	/**
	 * This method represents the number of participant for given ExcursionEventID.
	 * @param excursionEventID for the Organiser wants to check the total number of participants.
	 * @return number of participants for given excursionEventID.
	 */
	public int numberOfParticipants(int excursionEventID) {
		return bookdb.getParticipantCount(excursionEventID);
	}
	/**
	 * This method allows the User to Book an excursion event.
	 * @param userName userName of user who wants to make the Booking.
	 * @param excursionEventID excursionEventID for which the User wants to make the Booking.
	 * @param bookingDate Date on which the User Book's an excursion event.
	 * @return
	 */
	public boolean bookExcursionEvent(int excursionEventID, String userName, Timestamp bookingDate) {
		return bookdb.bookExcursionEvent(new Booking(excursionEventID, userName, bookingDate));
	}
	/**
	 * This method allows the Student to cancel their Booking and 
	 * It also allows the Organizer to cancel the Booking of those participants who failed to make their payments within deadline.
	 * @param userName userName of the User who made the booking.
	 * @param excursionEventID excursionEventID for which the User made booking.
	 * @param bookingDate Date on which the User made Booking.
	 * @param b 
	 * @return
	 */
	public boolean cancelBookExcursionEvent(String userName, int excursionEventID, Timestamp bookingDate) {
		return bookdb.cancelBookExcursionEvent(new Booking(excursionEventID, userName, bookingDate));
	}
	/**
	 * This method retrieves array-list of all the bookings in the system
	 * @return array-list of all the bookings
	 */
	public ArrayList<Booking> getAllBookings(){
		return bookdb.getAllBookings();
	}
	
}
