package clientNomad;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dialog.ChatPage;
import javafx.scene.text.TextFlow;
/**
 *A class which connects the user to the chat server, thus enabling the user to participate in the 
 *multi-user chat room
 */
public class ChatClient {
    private static ChatPage chatPage = new ChatPage();
    private static TextFlow textflow = chatPage.getTextFlow();
    // ip address of the computer which runs the server
    private static String server_ipAddress = "127.0.0.1";
    private Socket socket;
    // this port must be same as that of the chat server
    private int port = 50404;
    /**
     * a method which creates two threads for each new user of the chat room:
     * one thread which broadcasts the user messages to other users, and another which 
     * ensures that this user can see other users' messages
     */
    public void running() {
		// create the socket object
		try {
			// create a socket object which gets connected to the server
			socket = new Socket(server_ipAddress, port);
			// create threads 
			ExecutorService service = Executors.newFixedThreadPool(2);
			// two threads are created to send and receive to and from the server
			service.execute(new ChatReceiver(socket, textflow));
			service.execute(new ChatSender(socket,textflow));
			
		} catch (Exception e) {
			// handle exception
			e.printStackTrace();
		} 
		finally {
		}
    }
}
