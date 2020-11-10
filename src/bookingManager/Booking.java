package bookingManager;

import java.sql.Timestamp;
import java.util.Comparator;
/**
 * Booking class represents Booking made by User.
 * Booking class allows Student to book/cancel Excursion Event.
 * And it allows Organizer to manage Booking done by Students.
 * @author purohit_rishi
 *
 */
public class Booking {
	
	/**
	 * ID of Excursion Event
	 * User's userName
	 * Payment status of User
	 * Date of Booking an Excursion Event
	 */
	private int excursionEventID;
	private String userName;
	private boolean paymentStatus;
	private Timestamp bookingDate;
	
	/**
	 * Creates a new Booking with excursionEventID, userName(User's), bookingDate.
	 * @param excursionEventID excursionEvent's ID for which User wants to do Booking.
	 * @param userName userName of User doing the Booking.
	 * @param bookingDate Date on which Booking was made.
	 */
	public Booking(int excursionEventID, String userName, Timestamp bookingDate) {
		this.excursionEventID = excursionEventID;
		this.userName = userName;
		this.paymentStatus = false;
		this.bookingDate = bookingDate;
	}
	
	/**
	 * Creates a new Booking for Database with excursionEventID, userName(User's), paymentStatus, bookingDate.
	 * @param excursionEventID excursionEvent's ID for which User wants to do Booking.
	 * @param userName userName of User doing the Booking.
	 * @param paymentStatus paymentStatus of user, which can either be "Paid" or "Unpaid"
	 * @param bookingDate Date on which Booking was made.
	 */
	public Booking(int excursionEventID, String userName, Timestamp bookingDate, boolean paymentStatus) {
		this.excursionEventID = excursionEventID;
		this.userName = userName;
		this.bookingDate = bookingDate;
		this.paymentStatus = paymentStatus;
	}
	
	/**
	 * Gets the Excursion Event's ID
	 * @return excursionEventID that the User has booked
	 */
	public int getExcursionEventID() {
		return excursionEventID;
	}
	
	/**
	 * Changes the ExcursionEvent's ID
	 * @param excursionEventID This Booking's new ExcursionEventID
	 */
	public void setExcursionID(int excursionEventID) {
		this.excursionEventID = excursionEventID;
	}
	
	/**
	 * Gets the User's userName
	 * @return userName of User who has made the Booking.
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Changes the User's userName
	 * @param userName This Booking's new User Name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Gets the Payment Status of User's Booking
	 * @return Payment Status, which can either be "Paid" or "Unpaid"
	 */
	public boolean isPaymentStatus() {
		return paymentStatus;
	}
	
	/**
	 * Changes the Payment Status of User's Booking
	 * @param paymentStatus This Booking's new Payment Status.
	 */
	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	/**
	 * Gets the Date of Booking
	 * @return BookingDate of user's Booking
	 */
	public Timestamp getBookingDate() {
		return bookingDate;
	}
	
	/**
	 * Changes BookingDate of Booking
	 * @param bookingDate This Booking's new Booking Date
	 */
	public void setBookingDate(Timestamp bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * Nested Class ByPayment compares two Booking Object's.
	 * It also helps to sort Booking Objects ByPayments (i.e) either "Paid" or "Unpaid"
	 * @author purohit_rishi
	 */
	public static class ByPayment implements Comparator<Booking>{
		@Override
		/**
		 * This method simply compares two Booking Objects based on their PaymentStatus.
		 */
		public int compare(Booking o1, Booking o2) {
			int temp = 0;
			if(o1.isPaymentStatus() == o2.isPaymentStatus()) {
				temp = 0;
			}else {
				if(o1.isPaymentStatus()) {
					temp = 1;
				}else {
					temp = -1;
				}	
			}
			return temp;
		}

	}

}
