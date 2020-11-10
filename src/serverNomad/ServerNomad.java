package serverNomad;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import bookingManager.BookingController;
import excursionManager.ExcursionController;
import persistence.Database;
import userManager.UserController;

/**
 * Class establishes the interface between the logic layer components and the communication
 * with the clients via TCP/IP connections implementing Java Sockets.
 * 
 * @author Eugenio with the help of Marcin from StackOverflow. 
 * 			See link: https://stackoverflow.com/a/46438407/8868327
 *
 * @version 1.0
 */
public class ServerNomad {

	/*
	 * Class variables
	 */
	private static final int SERVER_PORT = 9998;
	private static final int MAX_CLIENTS = 1;
	/**
	 * Instance variables 
	 */
	private int serverPort;
	private String serverIP;
	private int maxClients;
	private int numClients;
	private Class[] serverControllerClasses;
	private boolean serverWorking = false;
	private ServerSocket serverSocket;
	private ServerChat chatServer;

	/**
	 * Main method of this class. Run this method to start a new instance of the NOMAD server
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Starting NOMAD server");
		Class[] classes = {UserController.class, ExcursionController.class, BookingController.class};
		ServerNomad server = new ServerNomad(SERVER_PORT, MAX_CLIENTS, classes);
		// Launch server
		Thread startServer = new Thread(() -> server.start());
		startServer.start();
	}


	/**
	 * Constructor of the server object that'd be used for creating the socket for connection
	 * @param serverPort
	 * @param maxClients
	 * @param serverControllerClasses
	 */
	public ServerNomad(int serverPort, int maxClients, Class<?>[] serverControllerClasses) {
		this.serverPort = serverPort;
		this.maxClients = maxClients;
		this.serverControllerClasses = serverControllerClasses;
		this.chatServer = new ServerChat();
	}

	/**
	 * Method initiates de ServerSocket to allow clients to connect to the server.
	 * Once the connection is done, it connects the server to the database server too.
	 */
	public void start() {
		if (serverWorking) return;
		try {
			if (serverIP != null) {
				serverSocket = new ServerSocket(serverPort, 50, InetAddress.getByName(serverIP));
			} else {
				serverSocket = new ServerSocket(serverPort);
			}

			//Confirm message
			System.out.println("Nomad Server now listening for new connections on port "+serverPort+".");
			// establish database connection
			//Database.connect();
			System.out.println("Server now connected to the Database.");
			//Launch chat server
			Thread startServer = new Thread(() -> chatServer.start());
			startServer.start();
			
			while (true) {
				//Open client socket
				Socket clientSocket = serverSocket.accept();

				if (clientSocket != null && getClientCount() < getMaxClients()) {
					//Synchronized retrieval of attributes
					System.out.println("New client connected to the server: IP: "+clientSocket.getInetAddress().toString());
					new Thread(()->new Client(clientSocket)).start();
				} else {
					System.out.println(String.format("Exceeded maximum amount of clients connected: %d/%d.", getClientCount(), getMaxClients()));
				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();}
		/*} catch (SQLException sql) {
			sql.printStackTrace();
		}*/
		return;

	}

	/**
	 * Method closes the ServerSocket so no more clients will connect to the server.
	 */
	public void stop() {
		try {
			chatServer.stop();
			serverSocket.close();
			serverWorking = false;
			Database.close();
		} catch (IOException io) {
			io.printStackTrace();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}

	/**
	 * Returns the amount of clients (threads launched) connected to the system
	 * @return number of clients connected
	 */
	public synchronized int getClientCount() {
		return numClients;
	}

	/**
	 * Returns the maximum amount of clients allowed simultaneously in the system 
	 * @return
	 */
	public synchronized int getMaxClients() {
		return maxClients;
	}

	/**
	 * Class to control the actions of clients through socket connections
	 * @author see first comment in file
	 */
	public class Client {
		String clientSentence = null, clientIP = null;
		Object[] args;
		ArrayList argsList;
		public ObjectOutputStream toClient;
		public ObjectInputStream fromClient;

		public Client(Socket clientSocket) {
			if (!clientSocket.isConnected() || clientSocket.isClosed()) return;

			synchronized(int.class) {
				numClients++;
			}

			//Set client's IP
			clientIP = clientSocket.getInetAddress().toString();
			
			try {
				fromClient = new ObjectInputStream(clientSocket.getInputStream());
				toClient = new ObjectOutputStream(clientSocket.getOutputStream());
			} catch (IOException io) {
				io.printStackTrace();
				synchronized(int.class) {numClients--;}		
				return;
			}

			while (true) {

				try {
					// Process information from client
					args = (Object[]) fromClient.readObject();

					// Obtain client sentence
					clientSentence = (String) args[0];
					// Separate function call from arguments
					argsList = new ArrayList(Arrays.asList(args));
					Collection<?> tmp = argsList;
					tmp.remove(clientSentence);
					args = tmp.toArray();

					// Print the client sentence
					System.out.println("Function to be invoked: "+clientSentence+"()");

					// arguments
					for (Object o : args) {
						System.out.println("Parameter: '"+ o +"' type '"+o.getClass().getTypeName()+"'");
					}

					toClient.writeObject(processServerFunction(clientSentence, args));
				} catch (IOException io) {
					//io.printStackTrace();
					System.out.println("Connection with client: "+clientIP+" has terminated");
					//Close sockets
					try {
						if (clientSocket != null) clientSocket.close();
						if (fromClient != null) fromClient.close();
						if (toClient != null) toClient.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					synchronized(int.class) { numClients--; }
					break;
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		}

		/**
		 * Function processes the function call received by the client and returns it's result
		 * to be sent by the client controller in the serverNomad
		 * @param function name of the function to be found in any of the local classes
		 * @param args arguments of the function
		 * @return the result of the function call
		 * @throws Exception if function doesn't match to any function in any of the classes
		 */
		public Object processServerFunction(String function, Object[] args) throws Exception {
			Method fun = null;
			Object clientEcho;
			int i;
			if (args.length > 0) {
				//Execute function with parameters
				for (i = 0; i < serverControllerClasses.length && fun == null;i++) {
					try {
						fun = serverControllerClasses[i].getDeclaredMethod(function, 
								getParameterTypes(serverControllerClasses[i], function));
						if (fun != null) break;

					} catch (NoSuchMethodException e) {
						System.out.println("No method "+function+" found in class "+serverControllerClasses[i].getSimpleName());
					} catch (SecurityException e) {
						e.printStackTrace();
					}
				}	
				if (fun == null) throw new Exception("No method found with name "+function+" and given parameters");

				clientEcho = fun.invoke(serverControllerClasses[i].newInstance(), args);
			} else {
				//Execute function without parameters
				for (i = 0; i < serverControllerClasses.length && fun == null;i++) {
					try {

						fun = serverControllerClasses[i].getDeclaredMethod(function);
						if (fun != null) break;

					} catch (NoSuchMethodException e) {
						System.out.println("No method "+function+" found in class "+serverControllerClasses[i].getSimpleName());
					} catch (SecurityException e) {
						e.printStackTrace();
					}
				}	
				if (fun == null) throw new Exception("No method found with name "+function);
				clientEcho = fun.invoke(serverControllerClasses[i].newInstance());
			}

			return clientEcho;
		}

		/**
		 * Get all the function parameter types dynamically
		 *
		 * @param aClass     class in which the function is located
		 * @param methodName function name
		 * @return Class[] of parameter types
		 */
		private Class<?>[] getParameterTypes(Class<? extends Object> aClass, String methodName) {
			try {
				for (Method m : aClass.getDeclaredMethods()) {
					if (m.getName().equals(methodName)) {
						return m.getParameterTypes();
					}
				}

				// If nothing have been found return null value
				return null;

			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}
}
