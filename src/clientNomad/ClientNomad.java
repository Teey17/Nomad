package clientNomad;

import java.io.*;
import java.net.*;

/**
 * Class establishes the interface between the application layer components and the communication
 * to the server via TCP/IP connections implementing Java Sockets.
 * 
 * @author Eugenio with the help of Marcin from StackOverflow. 
 * 			See link: https://stackoverflow.com/a/46438407/8868327
 *
 * @version 1.0
 */
public class ClientNomad {
	
	//Class constant
	private static final int SERVER_PORT = 50055;
	private static final String SERVER_IP = "127.0.0.1";
	//private static final String SERVER_IP = "10.32.18.0";
	//Instance variables
	private int serverPort;
	private String serverIP;
	private static Socket soc;
	private boolean connected = false;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	
	private static ClientNomad cn;
	
	/**
	 * Constructor of the client socket object
	 * @param serverIP
	 * @param serverPort
	 */
	private ClientNomad(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}
	
	public static ClientNomad getInstance() {
		if (cn == null) {
			cn = new ClientNomad(SERVER_IP, SERVER_PORT);
		}
		return cn;
	}
	/**
	 * Method establishes the connection with the server
	 */
	public void connect() {
		if (connected)
			return;

		System.out.println("Establishing connection to " + serverIP + " on port " + serverPort);
		try {
			// Init components to establish communication
			soc = new Socket(serverIP, serverPort);
			toServer = new ObjectOutputStream(soc.getOutputStream());
			fromServer = new ObjectInputStream(soc.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		System.out.println("Connection successful.");
		connected = true;
	}
	
	/**
	 * This method finishes the connection with the server
	 */
	public void disconnect() {
		if (!connected) return;
		
		try {
			soc.close();
			connected = false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Function executes the given function on the server side and returns its output
	 * whether this last one is null or not. 
	 * 
	 * If an exception occurs during execution of this function, error message will be sent through
	 * the console and null is returned.
	 * 
	 * @param serverSideFunction
	 * @return the resulting object of the serverSideFunction. Can be null.
	 */
	public Object invokeFunction(Object[] serverSideFunction) {
		if (soc == null || !connected) return null;
		
		try {
			toServer.writeObject(serverSideFunction);
			Object modifiedSentence = fromServer.readObject();
			
			return modifiedSentence;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Function returns the IP of the server the client is connecting to
	 * @return
	 */
	public String getServerIP() {
		return serverIP;
	}
	
	/**
	 * Function returns the port through which the client is connecting
	 * to the server
	 * @return
	 */
	public int getServerPort() {
		return serverPort;
	}

}