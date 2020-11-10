package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bookingManager.Booking;

/**
 * Implements all the interfaces needed from the logic layer to access booking functionality to
 * create, edit, cancel, retrieve and retrieve specific information on booking objects.
 * 
 * Uses the connection object of its superclass to create and execute SQL statements. 
 * 
 * @version 1.0
 */
public class BookingDatabase extends Database {
	
	/**
	 * Function changes the payment status of an entry in the Booking's database. When attempting
	 * to update an unexisting booking entry, the function will return false.
	 * 
	 * When the paymentStatus of a booking is changed to true, the confirmedParticipants counter of
	 * the referred booking is incremented by 1. Likewise, when the paymentStatus of a booking is changed to false,
	 * the confirmedParticipants counter is decremented by 1.
	 * 
	 * On bad usage of function (Trying to set the the status to the same as it was before) the function will return
	 * false
	 * 
	 * If an SQLException occurs during execution of the SQL statement, a message of the error is shown
	 * by console and false returned.
	 * 
	 * @param userName the userName linked to the booking
	 * @param currentStatus the new payment status
	 * @param excursionEventID the excursionEventID linked to the booking
	 * @return true if the execution of the function went as expected. False if attempt to change status to the current status or 
	 * 		  if an error was produced.
	 */
	public boolean changePaymentStatus(String userName, boolean currentStatus, int excursionEventID) {
		String query = "UPDATE "+T_BOOKING+" SET "+TB_STAT+" = ? WHERE "+TB_STDID+" = ? AND "
						+TB_EXCEVID+" = ? AND "+TB_STAT+" = ?;";	
		/* We check that the previous status  ( ^^^^^^^^^^^^^) is different than the new status
		 * to avoid decrementing or incrementing the confirmedParticipants counter on ExcursionEvent 
		 * on false or erroneous calls to this function
		 */
		// Try-catch with resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			//Let's prepare for beginning transaction
			connection.setAutoCommit(false);
			
			//Set parameters
			statement.setBoolean(1, currentStatus);  //If current status == true, store as 1. Otherwise store 0
			statement.setString(2, userName);
			statement.setInt(3, excursionEventID);
			statement.setBoolean(4, !currentStatus);
			
			//Execute DML statement. Should return 1 if booking exists
			int result = statement.executeUpdate();
			
			//Check correct statement
			if (result == 1) {
				/*If result == 1, means that exactly one row has been modified as expected.
				 * Now we need to take care of increasing or decreasing accordingly the counter
				 * in the related ExcursionEvent.
				 * If by any chance the update in mention counter could not be done, then we need
				 * to reset the information of the booking to the previous status so we have consistent
				 * information: ExcursionEvent.confirmedParticipants = COUNT(Booking.paymentStatus==true)
				 * then we return false to inform the calling function about the error in execution*/
				String update;
				if (currentStatus) {
					update = "UPDATE "+T_EXEVENT+" SET "+TEV_NUMP+" = "+TEV_NUMP+" + 1 WHERE "+TEV_ID+" = ?;"; 
				} else {
					update = "UPDATE "+T_EXEVENT+" SET "+TEV_NUMP+" = "+TEV_NUMP+" - 1 WHERE "+TEV_ID+" = ?;"; 
				}
				
				try (PreparedStatement sttmnt = connection.prepareStatement(update)) {
					//Set parameters
					sttmnt.setInt(1, excursionEventID);
					
					//Execute
					result = sttmnt.executeUpdate();
					//Should update one row
					if (result == 1) {
						//Commit changes
						connection.commit();
						connection.setAutoCommit(true);	//Restore
						return true;
					}
				} catch (SQLException ex) {
					ex.getMessage();
					//If error occurred during execution of the confirmedParticipants count, roll back changes
					try {
						connection.rollback();
						connection.setAutoCommit(true);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			
		} catch (SQLException ex) {
			System.out.println("Error trying to change the payment status of booking.");
			//If error occurred during execution of the confirmedParticipants count, roll back changes
			try {
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		return false;
	}


	/**
	 * Function retrieves from the database all the entries in the Booking table that contain
	 * <i>excursionEventID</i> as their related excursionEvent counterpart. 
	 * 
	 * If an SQLException occurs during execution of the SQL statement, a message of the error is shown
	 * by console and false returned.
	 * 
	 * @param excursionEventID the ID to which the Bookings refer to
	 * @return ArrayList of Booking related to <i>excursionEventID</i>
	 */
	public ArrayList<Booking> getParticipants(int excursionEventID) {
		String query = "SELECT "+TB_STDID+", "+TB_STAT+", "+TB_DATE+" "+
						"FROM "+T_BOOKING+" WHERE "+TB_EXCEVID+" = ?;";
		ArrayList<Booking> result = new ArrayList<>();
		
		// Try-catch with resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			//Set parameters
			statement.setInt(1, excursionEventID);

			//Execute query
			ResultSet rset = statement.executeQuery();
			
			//If rset is empty, return empty ArrayList
			if (!rset.isBeforeFirst()) return result;
			
			//Fill up ArrayList with obtained bookings
			while (rset.next()) {
				result.add(new Booking(excursionEventID,
							rset.getString(TB_STDID),
							rset.getTimestamp(TB_DATE),
							rset.getBoolean(TB_STAT))
						);
			}
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage()+"\nError trying to retrieve Bookings for excursionEventID: "+excursionEventID);
			return null;
		}
		return result;
	}

	/**
	 * Function counts from the database all the entries in the Booking table that contain
	 * <i>excursionEventID</i> as their related excursionEvent counterpart. 
	 * 
	 * If an SQLException occurs during execution of the SQL statement, a message of the error is shown
	 * by console and -1 returned.
	 * 
	 * If for any reason the result set obtained after the execution of the SQL statement is empty,
	 * -2 is returned.
	 * 
	 * @param excursionEventID the ID to which the Bookings refer to
	 * @return count of all entries relate to excursionEventID. -1 In case of SQLException.
	 * 		   -2 in case the obtained ResultSet was empty
	 */
	public int getParticipantCount(int excursionEventID) {
		String countName = "result";
		String query = "SELECT COUNT("+TB_STDID+") as "+countName+" "+
				"FROM "+T_BOOKING+" WHERE "+TB_EXCEVID+" = ?;";

		// Try-catch with resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			//Set parameters
			statement.setInt(1, excursionEventID);
	
			//Execute query
			ResultSet rset = statement.executeQuery();
	
			//Move the cursor to the first row and return the result of count
			if (rset.next()) return rset.getInt(countName);
	
		} catch (SQLException ex) {
			System.out.println("Error trying to retrieve Bookings for excursionEventID: "+excursionEventID);
			return -1;
		}
		
		//next() didn't return true, hence resultSet was empty.
		return -2;
	}

	/**
	 * Function inserts a newly generated booking component as an entry in the booking table,
	 * if it didn't exist before.
	 * 
	 * If an SQLException occurs during execution of the SQL statement, a message of the error is shown
	 * by console and false returned.
	 * 
	 * @param booking the booking to be persisted.
	 * @return true if the execution of the function went as expected. False otherwise.
	 */
	public boolean bookExcursionEvent(Booking booking) {
		String query = "INSERT IGNORE INTO "+T_BOOKING+
				" ("+TB_EXCEVID+", "+TB_STDID+", "+TB_STAT+", "+TB_DATE+
				") VALUES (?, ?, ?, ?);";
		
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			//Set parameters
			statement.setInt(1,booking.getExcursionEventID());
			statement.setString(2, booking.getUserName());
			statement.setBoolean(3, booking.isPaymentStatus());
			statement.setTimestamp(4, booking.getBookingDate());
			
			//Execute
			int result = statement.executeUpdate();
			
			if (result != 1) return false;
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		
		return true;
	}

	/**
	 * Function deletes the entry in the Booking table corresponding to the <i>booking</i> object
	 * passed as parameter. If the object doesn't correspond to any entry in the table, the function
	 * returns false.
	 * 
	 * If an SQLException occurs during execution of the SQL statement, a message of the error is shown
	 * by console and false returned.
	 * 
	 * @param booking the booking entry to be
	 * @return
	 */
	public boolean cancelBookExcursionEvent(Booking booking) {
		String query = "DELETE FROM "+T_BOOKING+" WHERE "+TB_STDID+" = ? AND "+TB_EXCEVID+" = ? AND "+TB_STAT+" = ?;";
		
		// Try with statement
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			//Set parameters
			statement.setString(1, booking.getUserName());
			statement.setInt(2, booking.getExcursionEventID());
			statement.setBoolean(3, false);
			
			//Execute
			int result = statement.executeUpdate();
					
			//DML statement should modify only one row
			if (result == 1) return true;
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return false; 
	}

	
	public ArrayList<Booking> getAllBookings() {
		String query = "SELECT "+TB_STDID+", "+TB_EXCEVID+", "+TB_STAT+" FROM "+T_BOOKING+";";
		ArrayList<Booking> result = new ArrayList<>();
		
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			//Execute
			ResultSet rset = statement.executeQuery();
			
			//Check empty result set
			if (!rset.isBeforeFirst()) return result;
			
			//Fill up
			while (rset.next()) {
				result.add(new Booking(
						rset.getInt(TB_EXCEVID),
						rset.getString(TB_STDID),
						rset.getTimestamp(TB_DATE)
						));
			}
			return result;
		} catch (SQLException ex) {
			ex.getMessage();
		}
		return null;
	}
	
	
}
