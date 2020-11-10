package clientNomad;

import java.io.PrintWriter;
import java.net.Socket;
import dialog.ChatPage;
import dialog.LoginPage;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
/**
 * A class which sends this user messages to the server so that it can broadcast it to other clients
 */
public class ChatSender implements Runnable{
	private TextFlow textflow;
	private Socket socket;
	private static LoginPage loginPage = new LoginPage();
	private static ChatPage chatPage = new ChatPage();
	private PrintWriter writer;

	public ChatSender(Socket s, TextFlow t) {
		socket = s;
		textflow = t;
		try {
			writer = new PrintWriter(socket.getOutputStream(), true);
		}catch(Exception e) {

		}
	}

	/**
	 * a method which simply sends messages of this user to the server for broadcast
	 */
	public void run() {
		System.out.println("chat sender begins");
		// create a variable for the client's user name 
		String username="";
		while(true) {
			// introduce some delay for the CPU so it can react a the correct moment
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// get the name from the GUI
			username = loginPage.getUserName();
			if(username != "") {
				break;
			}
		}
		// send the user name to the sever
		writer.println(username);

		String message = "";
		// send messages of the user
		do {
			// introduce some minor delay
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// get data from the GUI to send to the server
			message = chatPage.getComment();
			
			// send a private message to the server to be send to a private client
			if(message.startsWith("private_message")) {
				String[] data = message.split("[*]");
				String chat_partner = data[1];
				String my_message = username+": "+data[2];				
				Platform.runLater(()->{		
					TextFlow text_flow = chatPage.getPrivateTextflow(chat_partner);
					Text text = new Text(my_message+"\n");
					text.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));
					text.setFill(Color.BLUE);
					text_flow.getChildren().add(text);
				});
				writer.println(message);
				continue;
			}
			// create the server message
			if(!message.equals("")) {
				String full_message = username+": "+message;
				if(!message.contains(ChatPage.leave_message)) {
					Platform.runLater(()->{
						Text text = new Text(full_message+"\n");
						text.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));
						text.setFill(Color.BLUE);
						textflow.getChildren().add(text);
					});
				}
				// send message to the server
				writer.println(full_message);
			}
			// stop sending when the you receive leave message
		}while(!message.equals(ChatPage.leave_message));
	}
}
