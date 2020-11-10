package testDatabase;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import bookingManager.Booking;
import excursionManager.ExcursionEvent;
import persistence.BookingDatabase;
import persistence.Database;
import persistence.ExcursionDatabase;
import userManager.Student;
import userManager.User;

class BookingDatabaseTest {

	ExcursionDatabase edb = new ExcursionDatabase();
	BookingDatabase bdb = new BookingDatabase();
	
	@BeforeAll
	static void setUp() throws SQLException {
		Database.connect();
	}
	
	@Test
	void test() {
		ArrayList<ExcursionEvent> ex = edb.getAllExcursionEvents();
		User u = new Student("student1", "password");
		
		assertTrue(bdb.bookExcursionEvent(new Booking(ex.get(0).getExcursionEventID(), u.getUserName(), new Timestamp(0))));
	}
	
	@Test
	void changingBookingWithPaymentFalseToFalseShouldReturnFalse() {
		ArrayList<ExcursionEvent> ex = edb.getAllExcursionEvents();
		
		bdb.bookExcursionEvent(new Booking(ex.get(0).getExcursionEventID(), "student2", new Timestamp(1)));

		assertFalse(bdb.changePaymentStatus("student2", false, ex.get(0).getExcursionEventID()));
	}

}
