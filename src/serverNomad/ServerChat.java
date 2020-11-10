package serverNomad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *A class which serves as clients. it receive messages from from a given client and broadcasts it to 
 *other clients.
 */
public class ServerChat{
	private ServerChat chatserver;
	private ArrayList<String> usernames = new ArrayList<>();
	private static Set<User> users = new HashSet<>();
	private static int port = 50404;
	private String leave_message = "qwertzuiop";
	private ExecutorService service;
	ServerSocket serversocket;

	/**
	 * the main method: this method creates a server socket object and waits for the client to 
	 * connect to the server so that it can create a new thread of execution for this client
	 * in the thread of this client, the client's messages are received by the server and broadcasted to other clients
	 * @param args
	 */ 
	public void start() {
		// create a server socket object
		try {
			serversocket = new ServerSocket(port);
			System.out.println("Chat server started...");
			// create a pool of 25 threads
			service = Executors.newFixedThreadPool(25);
			chatserver = new ServerChat();
			System.out.println("Chat Server now listening for new connections on port "+port+".");
			while(true) {				
				// create a client user once this client joins the chat room
				User user = chatserver.new User(serversocket.accept());
				// add the client to the pool of clients logged it
				users.add(user);
				// execute the task of this new user of the server: the task is in the run method
				service.execute(user);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that when called, stops all running user instances before closing the server
	 */
	public void stop() {
		if (service != null) {
			Iterator value = users.iterator();
			
			while (value.hasNext()) {
				User u = (User) value.next();
				u.stop();
			}
		}
		try {
			if (!serversocket.isClosed()) serversocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * a method to add the user name to the list of user names
	 * @param username
	 */
	public void addUser(String username) {
		if(!usernames.contains(username)) {
			usernames.add(username);
		}
	}
	/**
	 *  a method to broadcast a message to all but this client: he/she is excluded
	 * @param message
	 * @param excluded
	 */
	public void broadcast(String message, User excluded) {
		for(User user: users) {
			if(user != excluded) {
				user.sendMessage(message);
			}
		}
	}
	/**
	 * a method to remove a user from the pool of users
	 * @param user
	 */
	public void removeUser(User user) {
		if(users.contains(user)) {
			users.remove(user);
		}
	}
	/**
	 * a method to remove the name of a user which has left the chat
	 * @param name
	 */
	public void removeName(String name) {
		if(usernames.contains(name)) {
			usernames.remove(name);
		}
	}

	/**
	 * A class which simply contains the task associated which each client of the chat room
	 */
	private class User implements Runnable{
		private Socket socket;
		private PrintWriter writer;
		private BufferedReader reader;
		private String username;
		private int size;
		private volatile boolean finish; 
		public User(Socket s) {
			socket = s;
		}
		
		/**
		 * Method runs the user's interface with the chat server
		 */
		public void run() {
			try {
				writer = new PrintWriter(socket.getOutputStream(), true);
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				/**wait for the user name of the new client*/
				username = reader.readLine();
				String broadcast_message = username+ " has joined.";

				// store it
				addUser(username);
				// send names of current users
				sendNames();
				// broadcast the user name of the new client. inform other clients of his/her arrival in the room
				broadcast(broadcast_message, this);
				String comment;
				
				finish = false;

				// store the size of the usernames list
				size = usernames.size();
				do {
					// wait for a message from the user
					comment = reader.readLine();
					
					// send private message to another client
					if(comment.startsWith("private_message")) {
						String[] data = comment.split("[*]");
						String name = data[1];
						String message = data[2];
						for(User user : users) {
							if(user.getUserName().equals(name)) {
								user.sendMessage("private_message"+"*"+username+"*"+message);
								
							}
						}	
					}			
					// broadcast the message of this user to other users
					else if(!comment.contains(leave_message)) {
						broadcast(comment, this);
					}
					// stop when the message is the good bye message
				}while(!comment.contains(leave_message) && !finish);

				// inform others that this client left the chat
				broadcast(username + " has left the chat", this);
				removeUser(this);
				removeName(username);
			}catch(Exception e) {

			}finally {
			}
		}
		
		/**
		 * Method stops the running loop of the User
		 */
		public void stop() {
			finish = true;
		}
		
		/**
		 * A method to return the user name of the user
		 * @return String
		 */
		public String getUserName() {
			return username;
		}
		/**
		 * a method used to send a message to a connected client
		 * @param message
		 */
		public void sendMessage(String message) {
			writer.println(message);		
		}
		/**
		 * a method used to send names of currently logged in users to this client
		 */
		public void sendNames() {
			writer.println("+++clearthearray+++");
			for(int i = 0; i < usernames.size() && !usernames.get(i).equals(username); ++i) {
				writer.println("+++usernames+++"+usernames.get(i));
			}
		}
	}
}

