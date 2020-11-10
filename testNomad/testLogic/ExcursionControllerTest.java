  /**
 * 
 */
package testLogic;

import static org.junit.jupiter.api.Assertions.*;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excursionManager.Excursion;
import excursionManager.ExcursionController;
import excursionManager.ExcursionEvent;
import excursionManager.MultiMap;
import persistence.Database;


/**
 * @author rmp
 *
 */
class ExcursionControllerTest {
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
	 * Test method for {@link excursionManager.ExcursionController#ExcursionController()}.
	 */
	@Test
	void testExcursionController() {
		ExcursionController ec = new ExcursionController();
		assertNotNull(ec);
	}

	/**
	 * Test method for {@link excursionManager.ExcursionController#viewAllExcursion()}.
	 */
	@Test
	void testViewAllExcursion() {
		ExcursionController ec = new ExcursionController();
		assertNotNull(ec);
		MultiMap<String, String> test1 = ec.viewAllExcursions();
		assertNotNull(test1);
	}

	/**
	 * Test method for {@link excursionManager.ExcursionController#createExcursion(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCreateExcursion() {
		String name = "From";
		String desc = "Zrom";
		ExcursionController ec = new ExcursionController();
		assertNotNull(ec);
		assertTrue(ec.createExcursion(name, desc), "Excursion creation successful");
	}

	/**
	 * Test method for {@link excursionManager.ExcursionController#editExcursion(java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public void testEditExcursion() {
		ExcursionController ec = new ExcursionController();
		assertNotNull(ec);
		Excursion test = ec.getExcursion("From");
			if(test.getExcursionName().equals("BY")) {
				int x = test.getExcursionID();
				assertTrue(ec.editExcursion("BROM", "MUM", x), "GOT IT");
			}
	}

	/**
	 * Test method for {@link excursionManager.ExcursionController#cancelExcursion(int)}.
	 */
	@Test
	void testCancelExcursion() {
		ExcursionController ec = new ExcursionController();
		assertNotNull(ec);
		Excursion test = ec.getExcursion("From");
			if(test.getExcursionName().equals("BROM")) {
				int x = test.getExcursionID();
				assertTrue(ec.cancelExcursion(x), "GOT IT");
			}
	}

//	/**
//	 * Test method for {@link excursionManager.ExcursionController#changeExcursionStatus(int, int)}.
//	 */
//	@Test
//	void testChangeExcursionStatus() {
//		ExcursionController ec = new ExcursionController();
//		assertNotNull(ec);
//		ArrayList<Excursion> el  =  db.getAllExcursion();
//		for(Excursion e : el) {
//			System.out.println(e.getExcursionName());
//			if(e.getExcursionName().equals("BROM")) {
//				int x = e.getExcursionID();
//				assertTrue(ec.cancelExcursion(x), "GOT IT");
//			}
//		}
//	}

	//	/**
	//	 * Test method for {@link excursionManager.ExcursionController#changeExcursionEventStatus(int, int)}.
	//	 */
	//	@Test
	//	void testChangeExcursionEventStatus() {
	//
	//	}

	/**
	 * Test method for {@link excursionManager.ExcursionController#viewAllExcursionEvents()}.
	 */
	@Test
	void testViewAllExcursionEvents() {
		ExcursionController ec = new ExcursionController();
		assertNotNull(ec);
		MultiMap<String, String> test1 = ec.viewAllExcursionEvents();
		assertNotNull(test1);
	}

	/**
	 * Test method for {@link excursionManager.ExcursionController#createExcursionEvent(java.lang.String, java.lang.String, int, java.sql.Timestamp)}.
	 */
	@Test
	public void testCreateExcursionEvent() {
		ExcursionController ec = new ExcursionController();
		assertNotNull(ec);
		Excursion test = ec.getExcursion("Marco");
			if(test.getExcursionName().equals("Marco")) {
				Timestamp d = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.now()));
				System.out.println("testec");
				assertTrue(ec.createExcursionEvent(test.getExcursionName(), test.getExcursionDesc(), test.getExcursionID(), d, 40), "GOT IT");
			}
	}

	/**
	 * Test method for {@link excursionManager.ExcursionController#editExcursionEventDate(java.lang.String, java.lang.String, int, int, java.sql.Timestamp)}.
	 */
	@Test
	void testEditExcursionEvent() {
		ExcursionController ec = new ExcursionController();
		assertNotNull(ec);
		ArrayList<ExcursionEvent> test = ec.getExcursionEvents("Fourth Tour");
		ExcursionEvent damn = test.get(0); 
		Timestamp d = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.now()));
		Excursion test1 = ec.getExcursion("Fourth Tour");
		damn.setDate(d);
		Timestamp y = damn.getDate();
		if(test1.getExcursionName().equals("Fourth Tour")) {
			int x = test1.getExcursionID();
			assertTrue(ec.editExcursionEvent(test1.getExcursionName(), test1.getExcursionDesc(), x, damn.getExcursionEventID(), y  , 40));
		}
	}

	/**
	 * Test method for {@link excursionManager.ExcursionController#cancelExcursionEvent(int)}.
	 */
	@Test
	void testCancelExcursionEvent() {
			ExcursionController ec = new ExcursionController();
			assertNotNull(ec);
			ArrayList<ExcursionEvent> test = ec.getExcursionEvents("Fourth Tour");
			ExcursionEvent damn = test.get(0);
			assertTrue(ec.cancelExcursionEvent(damn.getExcursionEventID()));
	}

}
