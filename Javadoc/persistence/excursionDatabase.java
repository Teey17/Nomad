package persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import excursionManager.*;

public class excursionDatabase extends Database {
	
	//Table Excursion and its columns
	private final String T_EXCURSION = "Excursion";
	private final String TE_ID = "excursionID";
	private final String TE_TITLE = "excursionName";
	private final String TE_DESP = "excursionDesc";
	//private final String TE_LOC = "location";
	//private final String TE_PIC = "pic_url";
	//private final String TE_STA = "status";
	//private final String TE_ORGID = "orgID";
	
	//Table ExcursionEvent and its columns
	private final String T_EXEVENT = "ExcursionEvent";
	private final String TEV_ID = "excursionEventID";
	private final String TEV_DATE = "date";
	//private final String TEV_PRICE = "price";
	//private final String TEV_NATTND = "n_Attds";
	//private final String TEV_MATTND = "max_Attds";
	//private final String TEV_DUR = "duration";
	//private final String TEV_INF = "information";
	//private final String TEV_ORGID = "orgID";
	private final String TEV_EXCID = "excursionID";
	//private final String TEV_STA = "status";
	
	// Connection object
	private static Connection connection;
		
	// ResultSet object
	private static ResultSet result;
		
	// Database object
	private static excursionDatabase db;
		
	
	/** 
	 * @return 
	 * @throws SQLException
	 */
	// Singleton pattern
	private excursionDatabase() throws SQLException {
		if (connection == null) {
			connection = getConnection();
		}
	}
		
	/**
	 * Function returns the instance of this user object
	 * @return
	 * @throws SQLException 
	 */
	public excursionDatabase getInstance() throws SQLException {
		if (db == null) {
			db = new excursionDatabase();
		}
		return db;
	}
	
	/**
	 * Function adds a new Excursion to the system's database
	 * @param excursion
	 * @return true if the query's execution went ok. False otherwise.
	 */
	public boolean createExcursion(Excursion excursion) {
		String query = "INSERT INTO "+T_EXCURSION+ 
				" ("+TE_TITLE+", "+TE_DESP+") VALUES ('"+
				excursion.getExcursionName()+"', '"+excursion.getExcursionDesc()+"';";
		
		//Try-with-resources
		try (Statement statement = connection.createStatement()) {
			
			int result = statement.executeUpdate(query);
			
			if (result == 1) return true; //Check DML statement
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	/**
	 * Function adds a new ExcursionEvent to the system's database
	 * @param ex
	 * @return true if the query's execution went ok. False otherwise.
	 */
	public boolean createExcursionEvent(ExcursionEvent excursionEvent) {
		String query = "INSERT INTO "+T_EXEVENT+ 
				" ("+TEV_DATE+", "+TEV_EXCID+") VALUES ('"+
				excursionEvent.getDate()+"', '"+excursionEvent.getexcursionID()+"';";
		
		//Try-with-resources
		try (Statement statement = connection.createStatement()) {
			
			int result = statement.executeUpdate(query);
			
			if (result == 1) return true; //Check DML statement
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	/**
	 * Function updates the modifications realised over <i>ex</i> in the database 
	 * @param ex
	 * @return true if the execution of the query went ok. False otherwise
	 */
	public boolean editExcursion(Excursion excursion) {
		String query = "UPDATE "+T_EXCURSION+" SET "+
				TE_TITLE+" = '"+excursion.getExcursionName()+"', "+
				TE_DESP+" = '"+excursion.getExcursionDesc()+
				"' WHERE "+TE_ID+" = "+excursion.getexcursionID()+";";
		
		//Try-with-resources
		try (Statement statement = connection.createStatement()) {
			
			int result = statement.executeUpdate(query);
			
			if (result == 1) return true; //Check DML statement
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}


	/**
	 * Function updates the modifications realised over <i>ex</i> in the database 
	 * @param ex
	 * @return true if the execution of the query went ok. False otherwise
	 */
	public boolean editExcursionEvent(ExcursionEvent excursionNew) {
		String query = "UPDATE "+T_EXEVENT+" SET "+
				TEV_DATE+" = "+excursionNew.getDate()+", "+
				TEV_EXCID+" = "+excursionNew.getexcursionID()+
				" WHERE "+TEV_ID+" = "+excursionNew.getExcursionEventID()+";";
		
		//Try-with-resources
		try (Statement statement = connection.createStatement()) {
			
			int result = statement.executeUpdate(query);
			
			if (result == 1) return true;  //Check DML statement
			 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
		}
		return false;
	}
	
	/**
	 * Function updates the modifications realised over <i>ex</i> in the database 
	 * @param ex
	 * @return true if the execution of the query went ok. False otherwise
	 */
	public boolean cancelExcursion(Excursion excursion) {
		String query = "DELETE FROM "+T_EXCURSION+" WHERE "+TE_ID+" = "+excursion.getexcursionID()+";";
		
		//Try-with-resources
		try (Statement statement = connection.createStatement()) {
			
			int result = statement.executeUpdate(query);
			
			if (result == 1) return true; //Check DML statement
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	/**
	 * Function cancels an excursion event
	 * @param excursionEvent
	 * @return
	 */
	public boolean cancelExcursionEvent(ExcursionEvent excursionEvent) {
		String query = "DELETE FROM "+T_EXEVENT+" WHERE "+TEV_ID+" = "+excursionEvent.getExcursionEventID()+";";
		
		//Try-with-resources
		try (Statement statement = connection.createStatement()) {
			
			int result = statement.executeUpdate(query);
			
			if (result == 1) return true; //Check DML statement
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	/**
	 * Function returns a list of all ExcursionEvents registered in the System.
	 * @return
	 */
	public ArrayList<ExcursionEvent> getAllExcursionEvents() {
		String query = "SELECT "+TE_ID+", "+TE_TITLE+", "+TE_DESP+", "+TEV_ID+", "+TEV_DATE+" FROM "+
					T_EXCURSION+" Ex JOIN "+T_EXEVENT+" Ev ON "
							+ "Ex."+TE_ID+" = Ev."+TEV_EXCID+";";
		ArrayList<ExcursionEvent> exEvList = new ArrayList<>();
		
		//Try-with-resources
		try (Statement statement = connection.createStatement()) {
			result = statement.executeQuery(query);
			while (result.next()) {
				//Add all excursions
				exEvList.add(new ExcursionEvent(result.getString(TE_TITLE),
						result.getString(TE_DESP),
						result.getInt(TE_ID),
						result.getInt(TEV_ID),
						result.getString(TEV_DATE)
						));
			}
			return exEvList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Function returns a list of all Excursions registered in the System.
	 * @return
	 */
	public ArrayList<Excursion> getAllExcursion() {
		String query = "SELECT "+TE_ID+", "+TE_TITLE+", "+TE_DESP+" FROM "+T_EXCURSION+";";
		ArrayList<Excursion> list = new ArrayList<>();
		//Get excursions
		try (Statement statement = connection.createStatement()) {
			result = statement.executeQuery(query);
			while (result.next()) {
				list.add(new Excursion(result.getString(TE_TITLE),
								result.getString(TE_DESP),
								result.getInt(TE_ID)));	
			}
			return list;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}

}
