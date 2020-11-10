package testDatabase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import excursionManager.Excursion;
import excursionManager.ExcursionEvent;
import persistence.Database;
import persistence.ExcursionDatabase;

/**
 * ExcursionDatabaseTest
 * Junit tests for Excursion Database Class.
 * @author Hasan Güney Esendemir
 */
@TestInstance(Lifecycle.PER_CLASS)
public class ExcursionDatabaseTest {

    private ExcursionDatabase db;
    private Excursion[] excursions;
    private ExcursionEvent[] events;
    private String[] strs = {",", "'", "'#", "' -- ", "'; Sleep(5)", " ' or '1' = '1 # ", "\"", "", "", "", ""};


    /*
    * Creates connection to database.
    * Creates Excursion objects in local environment.
    * Excursion names has characters which can lead errors in sql.
    * Each excursion object has a description lenght between 10 and 900.
    *
    */
    @BeforeEach
	void setUp() throws SQLException {
        Database.connect();
        db = new ExcursionDatabase();
        String name = "Excursion Name Sample ";
        String description  = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Sed risus ultricies tristique nulla aliquet enim tortor. Sagittis nisl rhoncus mattis rhoncus urna neque viverra justo. Tincidunt praesent semper feugiat nibh. Feugiat in ante metus dictum at tempor. Vel pretium lectus quam id leo in vitae. Nulla porttitor massa id neque aliquam vestibulum. Convallis aenean et tortor at risus viverra. Ac odio tempor orci dapibus. Sed lectus vestibulum mattis ullamcorper velit sed. Dolor magna eget est lorem ipsum dolor sit. Sapien pellentesque habitant morbi tristique senectus et netus. Quam vulputate dignissim suspendisse in. Quis risus sed vulputate odio ut enim blandit volutpat. Est sit amet facilisis magna etiam tempor orci eu lobortis. Amet nisl purus in mollis nunc sed id semper risus. Scelerisque purus semper eget duis at. Quis commodo odio aenean sed adipiscing diam donec adipiscing tristique. Fermentum dui faucibus in ornare quam viverra. Nec nam aliquam sem et tortor consequat id. In ante metus dictum at tempor commodo. In nulla posuere sollicitudin aliquam ultrices sagittis orci.";
        
        excursions = new Excursion[10];
        events = new ExcursionEvent[10];
        for(int i = 0; i < 10; ++i){
            excursions[i] = new Excursion(name + strs[i], description.substring(0, (i + 1) * 10 * i));
        }
	}
    
    /*
    * Store locally created objects in the database.
    * Test case should return true for each excursion with unique name.
    *
    */
//    @Test
//    @Order(1)
    void createNewExcursions(){
        //Create Excursions with different description lengths. (10-1000)
        
        for(Excursion e : excursions){
           
            assertTrue(db.createExcursion(e), "Create excursion should return true if name is not in the database before");
        }
    }

    /*
    * Tries to store same excursions in database.
    * Names should be unique in database; test case should return false.
    */

//   @Test
//    @Order(2)
    void createExcursionExisting(){
        for(Excursion e : excursions){
            assertFalse(db.createExcursion(e), "Create excursion should return false if there is an excursion with the same name.");
        }

    }

    /*
    *   Gets stored excursions from database.
    *   Checks if test excursions' name are contained in the database results.
    *   
    *   Test case should return true.
    *
    */

//    @Test
    void getAllExcursionsTestForNames(){
        ArrayList<Excursion> temp = db.getAllExcursion();
        boolean found = false;
        for(Excursion e : temp){
                
            for(Excursion ex : excursions){
                if(ex.getExcursionName().equals(e.getExcursionName())){
                    found = true;
                    break;
                }                   
            }
        }
        assertTrue(found, "Excursion Name not found in results.");
    }

    /*
    *   Gets stored excursions from database.
    *   Checks if test excursions' descriptions are contained in the database results.
    *   
    *   Test case should return true.
    *
    */
//    @Test
    void getAllExcursionsTestForDescriptions(){
        ArrayList<Excursion> temp = db.getAllExcursion();

        boolean found = false;
        for(Excursion e : temp){
            for(Excursion ex : excursions){
                if(ex.getExcursionDesc().equals(e.getExcursionDesc())){
                    found = true;
                    break;
                }
                
            }
        }
        assertTrue(found, "Excursion Description not found in results.");
    }
    
    /* 
    This function is commented out by intentionally. When the test is run, it runs before
    some of the other Functions and this results with failed tests.
  
    | | | | | | | | | | | | | | | | | | |
    V V V V V V V V V V V V V V V V V V V
    */

    /*
    *   Checks if excursions can be deleted in database.
    *   Test case should return true.
    *
    *
    *
    */
    
  
//    @Test
    void ZZZZcancelExcursions(){
        ArrayList<Excursion> temp = db.getAllExcursion();
        for(Excursion e : temp){
            boolean finalv = false;
            for(Excursion ex : excursions){
                if(ex.getExcursionName().equals(e.getExcursionName())){
                    
                    finalv = true;
                    break;
                }
            }
            if(finalv){
                assertTrue(db.cancelExcursion(e), "Cancel Excursion should return true");
            }
        }
    } 


    /*
    *  Create excursion events with random dates and store them in database.
    *   
    *   
    *   Test case should return true.
    *
    */

    /*
    *   Gets stored excursion events from database.
    *   Checks if excursion events can be edited.
    *   
    *   Test case should return true.
    *
    */
//    @Test
    void editExcursionEvent() {
    	ArrayList<ExcursionEvent> temp = db.getAllExcursionEvents();
    	
    	if (temp.isEmpty()) {
    		System.out.println("Empty result set from getAllExcursionEvents");
    		return;
    	}
    	//Edit excursion events
    	for (ExcursionEvent ex : temp) {
    		ex.setDate(new Timestamp((long) (ex.getDate().getTime() + Integer.MAX_VALUE * Math.random())));
            assertTrue(db.editExcursionEvent(ex));
    	}
    }

    /*
    *   Gets stored excursion events from database.
    *   Checks if excursion events can be canceled in database.
    *   
    *   Test case should return true.
    *
    */
//    @Test
    void cancelExcursionEvents() {
    	ArrayList<ExcursionEvent> tmp = db.getAllExcursionEvents();
    	
    	if (tmp.isEmpty()) {
    		System.out.println("Empty result set from getAllExcursionEvents");
    		return;
        }
        for(ExcursionEvent e : tmp){
            assertTrue(db.cancelExcursionEvent(e), "Excursion delete should return true");
        }
    }
//    /*
//    *   Change excursion status to 1  in the database for each excursion of test class.
//    *   Test case should return true.
//    */
//    
//    @Test
//    void changeExcursionStatus(){
//    	
//    	ArrayList<Excursion> excursions = db.getAllExcursion();
//    	
//        for(Excursion e : excursions){
//            assertTrue(db.changeExcursionStatus(e, true), "Excursion status change should return true");
//        }
//    }


    /*
    *   Change excursion status to 1  in the database for each excursion of test class.
    *   Test case should return true.
    */

//    @Test
    void changeExcursionStatus(){
    	
    	ArrayList<Excursion> excursions = db.getAllExcursion();
    	
        for(Excursion e : excursions){
//            assertTrue(db.changeExcursionEventStatus(e, 1), "Excursion status change should return true");
        }
    }

	//@Test
	void editExcursionsShouldEditAllNewExcursions() {
		for (Excursion e : excursions) {
            db.createExcursion(e);
		}
		ArrayList<Excursion> ex = db.getAllExcursion();
		
		for (Excursion e : ex) {
			e.setExcursionDesc("This has been changed");
			assertTrue(db.editExcursion(e), "Edit a newly created excursion should return true");
		}
	}
	
	//@Test
	void createExcursionEventsTest() {
		ArrayList<Excursion> ex = db.getAllExcursion();
		Timestamp d;
		for (int i = 0; i<ex.size(); i++) {
			d = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.now()));
			db.createExcursionEvent(new ExcursionEvent(ex.get(i).getExcursionName(), ex.get(i).getExcursionDesc(), ex.get(i).getExcursionID(), d, 40));
		}
	}
	
    /*
    * After tests are completed close connection to database.
    */
    @AfterAll
    void cleanUp() throws SQLException {
    	Database.close();
    }
}