package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides interface to the NOMAD system to establish the connection to the database and ensure all queries and access 
 * happen only through one connection, using the JDBC connector.
 * 
 * When the system starts up, the static method <i>connect()</i> of this class should be called to establish the connection,
 * otherwise all subsequent attempts to query the database will fail.
 * 
 * On system exit, a static method <i>close()</i> is provided to close the connection.
 * 
 * All queries to the database are implemented in child classes ExcursionDatabase, UserDatabase and BookingDatabase. They all use
 * this class' connection instance.
 * 
 * <b>NOTE:</b> The JDBC connector is stored in the "external_jar". Make sure to configure the project's classpath to include this 
 * .jar file.
 * 
 * @version 1.0
 */
public abstract class Database {
	/**
	 * URL of the Mysql server hosted in remotemysql.com
	 */
	private static String URL =  "jdbc:mysql://remotemysql.com:3306/UDLdhORRPs?useUnicode=true&"+
								"characterEncoding=utf8&autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
	//private static String URL = "jdbc:mysql://localhost:3306/NOMAD?useUnicode=true&"+ 
	//		"characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	/**
	 * Username to connect to the database
	 */
	private static String username="UDLdhORRPs";
	//private static String username="NOMAD";
	
	/**
	 * Password to connect to the password
	 */
	private static String password="p7b2Q4EXzq";
	//private static String password="password";
		
	/**
	 * Connection object used to create SQL statements.
	 * It follows the singleton pattern: Only one instance of this object
	 * will be used in the system to ensure only one connection.
	 */
	protected static Connection connection;
	
	/**
	 * Name of the Booking's table
	 */
	protected final String T_BOOKING = "Booking";
	/**
	 * Name of the Booking's userName column
	 */
	protected final String TB_STDID = "userName";
	/**
	 * Name of the Booking's excursionEventID column
	 */
	protected final String TB_EXCEVID = "excursionEventID";
	/**
	 * Name of the Booking's payment status
	 */
	protected final String TB_STAT = "paymentStatus";
	/**
	 * Name of the Booking's date column
	 */
	protected final String TB_DATE = "bookingDate";
	/**
	 * Name of the Excursion's table.
	 */
	protected final String T_EXCURSION = "Excursion";
	/**
	 * Name of the excursion's ID column.
	 */
	protected final String TE_ID = "excursionID";
	/**
	 * Name of the excursion's name column.
	 */
	protected final String TE_TITLE = "excursionName";
	/**
	 * Name of the excursion's description column.
	 */
	protected final String TE_DESP = "excursionDesc";
	/**
	 * Name of the excursion's status column
	 */
	protected final String TE_STAT = "status";
	//private final String TE_LOC = "location";
	//private final String TE_PIC = "pic_url";
	//private final String TE_ORGID = "orgID";
	/**
	 * Name of the ExcursionEvent's table.
	 */
	protected final String T_EXEVENT = "ExcursionEvent";
	/**
	 * Name of the ExcursionEvent's ID column.
	 */
	protected final String TEV_ID = "excursionEventID";
	/**
	 * Name of the ExcursionEvent's date column.
	 */
	protected final String TEV_DATE = "date";
	//private final String TEV_PRICE = "price";
	//private final String TEV_NATTND = "n_Attds";
	//private final String TEV_MATTND = "max_Attds";
	//private final String TEV_DUR = "duration";
	//private final String TEV_INF = "information";
	//private final String TEV_ORGID = "orgID";
	/**
	 * Name of the ExcursionEvent's excursionID column.
	 */
	protected final String TEV_EXCID = "excursionID";
	/**
	 * Name of the ExcursionEvent's maxParticipants column
	 */
	protected final String TEV_MAXP = "maxParticipants";
	/**
	 * Name of the ExcursionEvent's confirmedParticipants column
	 */
	protected final String TEV_NUMP = "confirmedParticipants";
	/**
	 * Name of the ExcursionEvent's status (isActive) column
	 */
	protected final String TEV_STATUS = "isActive";
	//private final String TEV_STA = "status";
	/**
	 * Name of the student's table
	 */
	protected final String T_STUDENT = "Student";
	/**
	 * Name of the student's username column
	 */
	protected final String TS_USER = "userName";
	/**
	 * Name of the student's password column
	 */
	protected final String TS_PASS = "userPassword";
	/**
	 * Name of the organizer's table
	 */
	protected final String T_ORGANIZER = "Organizer";
	/**
	 * Name of the organizer's username column
	 */
	protected final String TO_USER = "userName";
	/**
	 * Name of the organizer's password column
	 */
	protected final String TO_PASS = "userPassword";
	
	
	/**
	 * Method creates a new instance of the connection object if it didn't exist before.
	 * It calls the DriverManager.getConnection() function to connect with the URL, username 
	 * and password class attributes
	 * @throws SQLException if the connection to the database failed.
	 */
	public static void connect() throws SQLException {
		if (connection == null) {
			connection = DriverManager.getConnection(URL, username, password);
			connection.setAutoCommit(true);
		}
	}
	

	/**
	 * Function closes this' object's connection object to the database.
	 * This connection is the reference to the static connection database in Database class
	 * @throws SQLException if a database access error occurs
	 */
	public static void close() throws SQLException {
		connection.close();
	}
}