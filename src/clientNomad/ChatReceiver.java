package clientNomad;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import dialog.ChatPage;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * A class which simply receives messages from the server and displays them to this user
 */
public class ChatReceiver implements Runnable{
	private BufferedReader reader;
	private static ArrayList<String> names = new ArrayList<>();
	private Socket socket;
	private TextFlow textflow;
	private ChatPage chatPage = new ChatPage();
	private String server_data = "";

	public ChatReceiver(Socket s, TextFlow t) {
		socket = s;
		textflow = t;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(Exception e) {
			e.getMessage();
		}
	}
	
	/**
	 * a method which defines the task of this thread: to receive data from the server and show it to this client
	 */
	public void run() {

		while(true) {
			//read data from the server and display in the text area
			try {
				// wait for data from the server: this thread gets blocked until new data has arrived
				server_data = reader.readLine();
				// clear the list of names
				if(server_data.startsWith("+++clearthearray+++")) {
					names.clear();
					names.add("All Users");
				}
				// get the list of names
				else if(server_data.startsWith("+++usernames+++")) {
					names.add(server_data.substring(15));
				}
				// private message
				else if(server_data.startsWith("private_message")) {
					String[] data = server_data.split("[*]");
					String chat_partner = data[1];
					String my_message = chat_partner+": "+data[2];	

					// get the text flow object and append text to it
					Platform.runLater(()->{	
						// open the private chat window between this client and the private sender
						chatPage.getStage(chat_partner).show();
						TextFlow text_flow = chatPage.getPrivateTextflow(chat_partner);
						Text text = new Text(my_message+"\n");
						text.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));
						text.setFill(Color.BLACK);
						text_flow.getChildren().add(text);
					});
				}
				else {
					// show messages of other users to this client
					Platform.runLater(()->{
						Text text = new Text(server_data+"\n");
						text.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));
						text.setFill(Color.BLACK);
						textflow.getChildren().add(text);
					});
				}
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
	/**
	 *A method which returns the current users of the chat room to this client 
	 * @return
	 */
	public static ArrayList<String> sendNames(){
		return names;
	}
}

