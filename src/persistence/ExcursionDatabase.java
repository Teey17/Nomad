package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import excursionManager.*;


/**
 * Implements all the interfaces needed from the logic layer to access database functionality to
 * create, edit, cancel and get Excursion and ExcursionEvent objects.
 * 
 * Uses the connection object of its superclass to create and execute SQL statements. 
 * 
 * @version 1.0
 */
public class ExcursionDatabase extends Database {

		
	/**
	 * Function inserts a new entry in the Excursion table, and returns the result of the DML
	 * statement as a boolean result.
	 * 
	 * If an SQLException occurs during it's execution, the error is sent to the console and 
	 * false is returned.
	 * 
	 * @param excursion the object to be stored in the database.
	 * @return true if the query's execution went ok. False otherwise.
	 */
	public boolean createExcursion(Excursion excursion) {
		String query = "INSERT INTO "+T_EXCURSION+ 
				" ("+TE_TITLE+", "+TE_DESP+") VALUES (?, ?);";
		
		//Try-with-resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			
			//Set parameters
			statement.setString(1, excursion.getExcursionName());
			statement.setString(2, excursion.getExcursionDesc());
			
			//Execute query
			int result = statement.executeUpdate();
			
			//Query should only modify one row
			if (result == 1) return true; //Check DML statement
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	/**
	 * Function inserts a new entry in the ExcursionEvent table, and returns the result of the DML
	 * statement as a boolean result. 
	 * 
	 * The following ExcursionEvent attributes are set by default upon creation such as:
	 * <ul>
	 * 		<li>ExcursionEventID = Autoincrement value in the MySQL DB</li>
	 * 		<li>confirmedParticipants = 0</li>
	 * 		<li>isActive = true</li>
	 * </ul>
	 * 
	 * If an SQLException occurs during it's execution, the error is sent to the console and 
	 * false is returned.
	 * 
	 * @param excursionEvent the object to be stored in the database.
	 * @return true if the query's execution went ok. False otherwise.
	 */
	public boolean createExcursionEvent(ExcursionEvent excursionEvent) {
		String query = "INSERT INTO "+T_EXEVENT+ 
				" ("+TEV_DATE+", "+TEV_EXCID+", "+TEV_MAXP+") VALUES (?, ?, ?);";
		
		//Try-with-resources 
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			
			//Set parameters
			statement.setTimestamp(1, excursionEvent.getDate());
			statement.setInt(2, excursionEvent.getExcursionID());
			statement.setInt(3, excursionEvent.getMaxParticipants());
			
			//Execute query
			int result = statement.executeUpdate();
			
			if (result == 1) return true; //Check DML statement
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	/**
	 * Function updates an entry in the excursion table, and returns the result of the DML
	 * statement as a boolean result. 
	 * 
	 * If an SQLException occurs during it's execution, the error is sent to the console and 
	 * false is returned.
	 * 
	 * @param excursion the object to be updated in the database.
	 * @return true if the query's execution went ok. False otherwise.
	 */
	public boolean editExcursion(Excursion excursion) {
		String query = "UPDATE "+T_EXCURSION+" SET "+
				TE_TITLE+" = ?, "+ TE_DESP+" = ? WHERE "+TE_ID+" = ?;";
		
		//Try-with-resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, excursion.getExcursionName());
			statement.setString(2, excursion.getExcursionDesc());
			statement.setInt(3, excursion.getExcursionID());
			int result = statement.executeUpdate();
			
			if (result == 1) return true; //Check DML statement
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}


	/**
	 * Function updates the date and the maximum amount of participants of an ExcursionEvent 
	 * and returns the result of the DML statement as a boolean result. 
	 * 
	 * If an SQLException occurs during it's execution, the error is sent to the console and 
	 * false is returned.
	 * 
	 * @param excursionNew the object to be updated in the database.
	 * @return true if the query's execution went ok. False otherwise.
	 */
	public boolean editExcursionEvent(ExcursionEvent excursionNew) {
		String query = "UPDATE "+T_EXEVENT+" SET "+
				TEV_DATE+" = ?, "+TEV_MAXP+" = ? WHERE "+TEV_ID+" = ?";
		
		//Try-with-resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			
			//Set parameters
			statement.setTimestamp(1, excursionNew.getDate());
			statement.setInt(2, excursionNew.getMaxParticipants());
			statement.setInt(3, excursionNew.getExcursionEventID());
			
			//Execute DML statement
			int result = statement.executeUpdate();
			
			//DML should only affect 1 row
			if (result == 1) return true;  //Check DML statement
			 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
		}
		return false;
	}
	
	/**
	 * Function deletes an entry in the excursion table, and returns the result of the DML
	 * statement as a boolean result. 
	 * 
	 * If an SQLException occurs during it's execution, the error is sent to the console and 
	 * false is returned.
	 * 
	 * @param excursion the object to be deleted from the database.
	 * @return true if the query's execution went ok. False otherwise.
	 */
	public boolean cancelExcursion(Excursion excursion) {
		String query = "DELETE FROM "+T_EXCURSION+" WHERE "+TE_ID+" = ?;";
		
		//Try-with-resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			
			//Set parameters
			statement.setInt(1, excursion.getExcursionID());
			
			//Execute
			int result = statement.executeUpdate();
			
			// Only 1 row should be affected
			if (result == 1) return true; //Check DML statement
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	/**
	 * Function deletes an entry in the ExcursionEvent table, and returns the result of the DML
	 * statement as a boolean result. 
	 * 
	 * If an SQLException occurs during it's execution, the error is sent to the console and 
	 * false is returned.
	 * 
	 * @param excursionEvent the object to be deleted from the database.
	 * @return true if the query's execution went ok. False otherwise.
	 */
	public boolean cancelExcursionEvent(ExcursionEvent excursionEvent) {
		String query = "DELETE FROM "+T_EXEVENT+" WHERE "+TEV_ID+" = ?;";
		
		//Try-with-resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			
			//Set parameters
			statement.setInt(1, excursionEvent.getExcursionEventID());
			
			//Execute
			int result = statement.executeUpdate();
			
			//Should only affect one row
			if (result == 1) return true; //Check DML statement
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	
	/**
	 * Function queries the database to obtain all rows from the ExcursionEvent table.
	 * 
	 * If an SQLException occurs during it's execution, the error is sent to the console and 
	 * null is returned.
	 * 
	 * @return and ArrayList of ExcursionEvents if the query's execution went ok. Null otherwise.
	 */
	public ArrayList<ExcursionEvent> getAllExcursionEvents() {
		String query = "SELECT Ex."+TE_ID+", Ex."+TE_TITLE+", Ex."+TE_DESP+", Ev."+TEV_ID+", Ev."+TEV_DATE+
				", Ev."+TEV_MAXP+", Ev."+TEV_NUMP+", Ev."+TEV_STATUS+" FROM "+
					T_EXCURSION+" Ex JOIN "+T_EXEVENT+" Ev ON "
							+ "Ex."+TE_ID+" = Ev."+TEV_EXCID+";";
		ArrayList<ExcursionEvent> exEvList = new ArrayList<>();
		
		//Try-with-resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			
			//Execute
			ResultSet result = statement.executeQuery();
			
			//Check if empty result set
			if (!result.isBeforeFirst()) return exEvList;
			
			while (result.next()) {
				//Add all excursions
				exEvList.add(new ExcursionEvent(result.getString(TE_TITLE),
						result.getString(TE_DESP),
						result.getInt(TE_ID),
						result.getInt(TEV_ID),
						result.getTimestamp(TEV_DATE),
						result.getInt(TEV_MAXP),
						result.getInt(TEV_NUMP),
						result.getBoolean(TEV_STATUS)
						));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		return exEvList;
	}

	/**
	 * Function queries the database to obtain all rows from the Excursion table.
	 * 
	 * If an SQLException occurs during it's execution, the error is sent to the console and 
	 * null is returned.
	 * 
	 * @return and ArrayList of Excursions if the query's execution went ok. Null otherwise.
	 */
	public ArrayList<Excursion> getAllExcursion(){
		String query = "SELECT "+TE_ID+", "+TE_TITLE+", "+TE_DESP+" FROM "+T_EXCURSION+";";
		ArrayList<Excursion> list = new ArrayList<>();
		//Get excursions
		try(PreparedStatement statement = connection.prepareStatement(query)) {			
			//Execute
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				list.add(new Excursion(result.getString(TE_TITLE),
								result.getString(TE_DESP),
								result.getInt(TE_ID)));	
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		return list;
	}
	
	/**
	 * Function retrieves an ArrayList of all entries in the excursion table whose 
	 * <i>status</i> value corresponds to active.
	 * 
	 * If an SQLException occurs during it's execution, the error is sent to the console and 
	 * null is returned.
	 * 
	 * @return ArrayList with the excursions found. Null if there was an SQLException.
	 */
	public ArrayList<Excursion> getActiveExcursions() {
		int active = 1;		//Temporary
		String query = "SELECT "+TE_ID+", "+TE_TITLE+", "+TE_DESP+" FROM "+T_EXCURSION+
						" WHERE "+TE_STAT+" = ?;";
		ArrayList<Excursion> result = new ArrayList<>();
		
		//Try-catch with resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			
			//Set parameters
			statement.setInt(1, active);
			
			//Execute
			ResultSet rset = statement.executeQuery();
			
			//Do checks
			
			if (!rset.isBeforeFirst()) return result;	//Empty ResultSet, empty ArrayList
			
			while (rset.next()) {
				result.add(new Excursion(rset.getString(TE_TITLE),
								rset.getString(TE_DESP),
								rset.getInt(TE_ID))
						);
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		}		
		return result;
	}

	/**
	 * Function modifies the status of an entry in the excursion table if exists.
	 * If the DML statement doesn't affect any row, the function returns false.
	 * 
	 * If an SQLException occurs during it's execution, the error is sent to the console and 
	 * false is returned.
	 * 
	 * @param e excursion whose status is to be modified
	 * @param status the status to be set
	 * @return true if the execution went as expected. False otherwise
	 */
//	public boolean changeExcursionStatus(Excursion e, boolean status) {
//		String query = "UPDATE "+T_EXCURSION+" SET "+TE_STAT+" = ? WHERE "+
//						TE_ID+" = "+e.getExcursionID()+";";
//		
//		try (PreparedStatement statement = connection.prepareStatement(query)) {
//			
//			//Set parameters
//			statement.setBoolean(1, status);
//			
//			//Execute
//			int result = statement.executeUpdate();
//			
//			if (result == 1) return true;
//			
//		} catch (SQLException ex) {
//			System.out.println(ex.getMessage());
//		}
//		return false;
//	}

	/**
	 * Function queries the database to return all the excursionEvents that have been
	 * associated to <i>userName</i> by bookings. If no booking is found that links 
	 * <i>userName</i> with any excursionEvent, an empty list is returned.
	 * 
	 * If an SQLException occurs during execution, a message is displayed in the console and
	 * null is returned.
	 * 
	 * @param userName
	 * @return
	 */
	public ArrayList<ExcursionEvent> getAllMyExcursionEvents(String userName) {
		String query = "SELECT Ex."+TE_ID+", Ex."+TE_TITLE+", Ex."+TE_DESP+", Ev."+TEV_ID+", Ev."+TEV_DATE+
				", Ev."+TEV_MAXP+", Ev."+TEV_NUMP+", Ev."+TEV_STATUS+
				" FROM "+T_EXCURSION+" Ex JOIN "+T_EXEVENT+" Ev ON "+
				"Ex."+TE_ID+" = Ev."+TEV_EXCID+" JOIN "+T_BOOKING+" B ON Ev."+TEV_ID+" = B."+TB_EXCEVID+
				" WHERE "+TB_STDID+" = ?;";
		
		ArrayList<ExcursionEvent> result = new ArrayList<>();
		
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			//Set parameters
			statement.setString(1, userName);
			
			//Execute
			ResultSet rset = statement.executeQuery();
			
			//Check for empty result set
			if (!rset.isBeforeFirst()) return result;
			
			//Fill up arrayList
			while (rset.next()) {
				//Add all excursions
				result.add(new ExcursionEvent(rset.getString(TE_TITLE),
						rset.getString(TE_DESP),
						rset.getInt(TE_ID),
						rset.getInt(TEV_ID),
						rset.getTimestamp(TEV_DATE),
						rset.getInt(TEV_MAXP),
						rset.getInt(TEV_NUMP),
						rset.getBoolean(TEV_STATUS)
						));
			}
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
		
		return result;
	}

	/**
	 * Function deletes an entry in the ExcursionEvent table, and returns the result of the DML
	 * statement as a boolean result. 
	 * 
	 * If an SQLException occurs during it's execution, the error is sent to the console and 
	 * false is returned.
	 * 
	 * @param excursionEventID the ID of the object to be deleted from the database.
	 * @return true if the query's execution went ok. False otherwise.
	 */
	public boolean cancelExcursionEvent(int excursionEventID) {
		String query = "DELETE FROM "+T_EXEVENT+" WHERE "+TEV_ID+" = ?;";
		
		//Try-with-resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			
			//Set parameters
			statement.setInt(1, excursionEventID);
			
			//Execute
			int result = statement.executeUpdate();
			
			//Should only affect one row
			if (result == 1) return true; //Check DML statement
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * Function changes the status of an excursion event, in case we need to delete it but
	 * there are bookings with paymentStatus == true.
	 * 
	 * If an SQLException occurs during it's execution, the error is sent to the console and 
	 * false is returned.
	 * 
	 * @param e the excursionEvent whose status will be modified
	 * @param status true if the Event will be changed to active. False to change it to inactive
	 * @return
	 */
	public boolean changeExcursionEventStatus(ExcursionEvent e, boolean status) {
		String query = "UPDATE "+T_EXEVENT+" SET "+TEV_STATUS+" = ? WHERE "+TEV_ID+" = ?;";
		
		//Try-with-resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			
			//Set parameters
			statement.setBoolean(1, status);
			statement.setInt(2, e.getExcursionEventID());
			
			//Execute
			int result = statement.executeUpdate();
			
			//Should only modify one row
			if (result == 1) return true;
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}

	/**
	 * Function finds the Excursion data stored in database for the <i>excursionName</i> String
	 * and returns the object corresponding to it.
	 * 
	 * If an SQLException occurs during it's execution, the error is sent to the console and 
	 * null is returned.
	 * 
	 * @param excursionName
	 * @return the Excursion object found or null if it wasn't found or an error occurred
	 */
	public Excursion getExcursion(String excursionName) {
		String query = "SELECT "+TE_ID+", "+TE_TITLE+", "+TE_DESP+" FROM "+
				T_EXCURSION+" WHERE "+TE_TITLE+" = ?;";
		
		//Try-with-resources
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			
			//Set paramenters
			statement.setString(1, excursionName);
			
			//Execute
			ResultSet rset = statement.executeQuery();
			
			if (rset.next()) {
				return new Excursion(
						rset.getString(TE_TITLE),
						rset.getString(TE_DESP),
						rset.getInt(TE_ID)
						);
			}
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}

	/**
	 * Function finds all ExcursionEvents that refer to the Excursion named
	 * <i>excursionName</i> and returns an ArrayList of it.
	 * 
	 * If an SQLException occurs during it's execution, the error is sent to the console and 
	 * null is returned.
	 * 
	 * @param excursionName the excursion name of the events to find.
	 * @return an ArrayList of all excursions find, or null if there was an error.
	 */
	public ArrayList<ExcursionEvent> getExcursionEvents(String excursionName) {
		String query = "SELECT Ex."+TE_ID+", Ex."+TE_TITLE+", Ex."+TE_DESP+", Ev."+TEV_ID+", Ev."+TEV_DATE+
				", Ev."+TEV_MAXP+", Ev."+TEV_NUMP+", Ev."+TEV_STATUS+" FROM "+
					T_EXCURSION+" Ex JOIN "+T_EXEVENT+" Ev ON "
					+ "Ex."+TE_ID+" = Ev."+TEV_EXCID+" WHERE Ex."+TE_TITLE+" = ?;";
		ArrayList<ExcursionEvent> exEvList = new ArrayList<>();
		
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			//Set parameters
			statement.setString(1, excursionName);
			
			//Execute
			ResultSet result = statement.executeQuery();
			
			//Check if empty result set
			if (!result.isBeforeFirst()) return exEvList;
			
			while (result.next()) {
				//Add all excursions
				exEvList.add(new ExcursionEvent(result.getString(TE_TITLE),
						result.getString(TE_DESP),
						result.getInt(TE_ID),
						result.getInt(TEV_ID),
						result.getTimestamp(TEV_DATE),
						result.getInt(TEV_MAXP),
						result.getInt(TEV_NUMP),
						result.getBoolean(TEV_STATUS)
						));
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}
}
