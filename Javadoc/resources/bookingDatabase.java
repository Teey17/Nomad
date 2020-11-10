package persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class bookingDatabase extends Database {

	//Table Booking and its columns
	private final String T_BOOKING = "Booking";
	private final String TB_ID = "bookingID";
	private final String TB_STDID = "userName";    //Student ID
	private final String TB_EXCEVID = "excursionEventID"; 	//Excursion event ID
	private final String TB_STAT = "status";
	
	// Connection object
	private static Connection connection;
		
	// ResultSet object
	private static ResultSet result;
			
	// Database object
	private static bookingDatabase db;
			
	
	/** 
	 * @return 
	 * @throws SQLException
	 */
	// Singleton pattern
	private bookingDatabase() throws SQLException {
		if (connection == null) {
			connection = getConnection();
		}
	}
			
	/**
	 * Function returns the instance of this user object
	 * @return
	 * @throws SQLException 
	 */
	public bookingDatabase getInstance() throws SQLException {
		if (db == null) {
			db = new bookingDatabase();
		}
		return db;
	}

	/**
	 * Takes a booking object to store in the Database
	 * @param booking
	 * @return true if creation was successful, false otherwise
	 */
	public boolean createBooking(Booking booking) {
		String sql = "INSERT INTO "+T_BOOKING+" ("+
				TB_STDID+", "+TB_EXCEVID+", "+TB_STAT+") VALUES (" + 
				booking.getStudent().getID() + ", " + booking.getExcursionEvent().getEvID()+
				", '"+booking.getStatus()+"');";
		
		//Try-with-resources automatically closes statement and resultset resources
		try (Statement statement = connection.createStatement()){
			
			int num_rows = statement.executeUpdate(sql);
			
			if(num_rows == 1) return true; //Check DML statement
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		
		return false;
	}
	
	/**
	 * Takes a booking to update it's status in the Database
	 * @param booking
	 * @return true if update was successful, false otherwise
	 */
	public boolean updateBooking(Booking booking) {
		String sql = "UPDATE "+T_BOOKING+" SET "+TB_STAT+" = '" + booking.getStatus() + 
				"' WHERE "+TB_ID+" = "+ booking.getID()+";";
		
		//Try-with-resources
		try (Statement statement = connection.createStatement()) {
			
			int num_rows = statement.executeUpdate(sql);
			
			if(num_rows == 1) return true; //Check DML statement
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
}
