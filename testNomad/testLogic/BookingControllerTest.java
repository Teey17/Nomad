/**
 * 
 */
package testLogic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bookingManager.Booking;
import bookingManager.BookingController;
import excursionManager.ExcursionController;
import excursionManager.ExcursionEvent;
import persistence.Database;

/**
 * @author rmp
 *
 */
class BookingControllerTest {
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Database.connect();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link bookingManager.BookingController#BookingController()}.
	 */
	@Test
	void testBookingController() {
		BookingController bc = new BookingController();
		assertNotNull(bc);
		
	}

	/**
	 * Test method for {@link bookingManager.BookingController#changePaymentStatus(java.lang.String, boolean, int)}.
	 */
	@Test
	void testChangePaymentStatus() {
		ExcursionController ec = new ExcursionController();
		assertNotNull(ec);
		BookingController bc = new BookingController();
		assertNotNull(bc);
		ArrayList<Booking> test = bc.getAllBookings();
		Booking x = test.get(0);
		assertTrue(bc.changePaymentStatus(x.getUserName(), x.isPaymentStatus(), x.getExcursionEventID()));
		
	}

	/**
	 * Test method for {@link bookingManager.BookingController#viewAllParticipants(int)}.
	 */
	@Test
	void testViewAllParticipants() {
		
	}

	/**
	 * Test method for {@link bookingManager.BookingController#numberOfParticipants(int)}.
	 */
	@Test
	void testNumberOfParticipants() {
		
	}

	/**
	 * Test method for {@link bookingManager.BookingController#bookExcursionEvent(java.lang.String, int, java.sql.Timestamp)}.
	 */
	@Test
	void testBookExcursionEvent() {
		
	}

	/**
	 * Test method for {@link bookingManager.BookingController#cancelBookExcursionEvent(java.lang.String, int, java.sql.Timestamp)}.
	 */
	@Test
	void testCancelBookExcursionEvent() {
		
	}

}
