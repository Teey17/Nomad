package dialog;

import javafx.geometry.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

import clientNomad.ChatClient;
import clientNomad.ChatReceiver;
import dialogAgent.ExcursionAgent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
/**
 * 
 * A class which provides a multi- user chat window where users of the system can interact with each other
 *
 */
public class ChatPage {

	private static TextFlow textflow = new TextFlow();
	private static MainClass mainClass = new MainClass();
	private static HomePage homepage  = new HomePage();
	private static AllEvents events = new AllEvents();
	private static ExcursionAgent excursionAgent = new ExcursionAgent();
	private static String comment = "";
	private ArrayList<String> usernames = new ArrayList<>();
	private Label leave;
	private TextField message;
	private Scene scene;
	private Button sendButton;
	private Label refresh;
	private Label logout;
	private BorderPane root;
	private static TextFlow private_textflow;
	private static Stage privateStage;
	private static HashMap<String, TextFlow> textflows = new HashMap<>();
	private static HashMap<String, Stage> stages = new HashMap<>();
	public static String leave_message = "qwertzuiop";
	/**
	 * A method to return a user interface of a chat room where students and organisers can chat communicate
	 * @param user
	 * @return Scene
	 */
	public Scene chatScene(USERTYPE user) {
		root = new BorderPane();
		/** add a back ground image */
		root.setStyle("-fx-background-image: url(images/paris.jfif)");
		
		/** start the chat client: sender and receiver threads */
		ChatClient chatclient = new ChatClient();
		chatclient.running();

		/**set up the root node with all its controls*/
		/** set up the title of this page */
		Text the_title = new Text("NOMAD Chat Room");
		the_title.setFill(Color.WHITE);
		the_title.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));

		/**create labels: leave label to leave the chat or logout label to log out of the system*/
		leave = new Label("leave chat");
		leave.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));

		refresh = new Label("refresh");
		leave.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));

		logout = new Label("logout");
		logout.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));

		/** create a menu bar using the above labels */
		MenuBar menubar1 = new MenuBar();
		MenuBar menubar2 = new MenuBar();
		Menu menu_logout = new Menu("", logout);
		Menu menu_leave = new Menu("", leave);
		Menu menu_refresh = new Menu("", refresh);
		menubar1.setStyle("-fx-background-color: transparent;");
		menubar1.getMenus().addAll(menu_leave, menu_logout);
		menubar2.setStyle("-fx-background-color: transparent;");
		menubar2.getMenus().addAll(menu_refresh);

		/**organise the menus on the menu bar*/
		Region rightSpacer = new Region();
		rightSpacer.setStyle("-fx-background-color: transparent;");
		HBox.setHgrow(rightSpacer, Priority.SOMETIMES);
		HBox spaced_menubar = new HBox(menubar2, rightSpacer, menubar1);

		/** add the title and the menu bar at the top of the border pane */
		VBox box = new VBox(10);
		box.getChildren().addAll(spaced_menubar, the_title);
		box.setAlignment(Pos.CENTER); 
		root.setTop(box);

		/** create a text field for the chat */		
		message = new TextField();
		message.setPrefSize(400, 10);
		/**set up the text area where text will be displayed*/
		textflow.setDisable(true);
		textflow.setLineSpacing(10);

		/** make the text area scroll-able */
		ScrollPane scrollpane1 = new ScrollPane();
		scrollpane1.setContent(textflow);
		scrollpane1.setPrefSize(440, 520);

		try {
			/** wait for names to arrive from the server*/
			Thread.sleep(5);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		/**store the names of the current users of the system for display*/
		usernames = ChatReceiver.sendNames();
		/**set up a list view with these names for display*/
		ObservableList<String> observablelist = FXCollections.observableArrayList(usernames);
		ListView<String> listnames = new ListView<String>(observablelist);
		listnames.setPrefSize(120, 520);
		/**create an optional button for sending messages*/
		sendButton = new Button("send");

		/** add the UI for the chat room in the centre of this border pane */
		VBox display = new VBox(5, scrollpane1, message);
		VBox side = new VBox(5, listnames, sendButton);
		HBox chatView = new HBox(5, display, side);
		chatView.setAlignment(Pos.CENTER);
		chatView.setPadding(new Insets(15,2,2,2));
		root.setCenter(chatView); 

		/** prepare scene to be returned */
		scene = new Scene(root, MainClass.STAGE_HEIGHT, MainClass.STAGE_WIDTH);	
		scene.getStylesheets().add("styles/eventDescriptionStyle.css");

		/** event handling: make the user log out when they press the log out label */
		logout.setOnMouseClicked(e->{
			/** specify the leave message */
			comment = leave_message;
			mainClass.logout();
		});
		/** event handling: make the user leave the chat and return the previous page */
		leave.setOnMouseClicked(e->{
			/** ask the user if they really want to leave the chat room */
			Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to leave the chat room?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				/** empty the view */
				comment = leave_message;
				/**return to the previous page based on the type of user logged in*/
				switch(user) {
				case STUDENT:
					mainClass.getMainStage().setScene(events.allEventsScene(excursionAgent.viewAllExcursionEvents(), USERTYPE.STUDENT));
					break;
				case ORGANISER:
					mainClass.getMainStage().setScene(homepage.homeScene(excursionAgent.viewAllExcursions()));

					break;
				default:
					break;
				}
			}
		});

		/** event handling: make the user send their message by pressing enter key */
		message.setOnAction(e->{
			comment = message.getText();
			message.setText("");
		});	

		/** event handling: make a user send a message by pressing this time the send button */
		sendButton.setOnAction(e->{
			comment = message.getText();
			message.setText("");
		});	
		/** event handling: refresh page */
		refresh.setOnMouseClicked(e->{	
		});

		/** event handling: private messaging: a click on the list view */
		// Get the list view selection model. 
		MultipleSelectionModel<String> selection_model = listnames.getSelectionModel();
		selection_model.selectedItemProperty().addListener(
				new ChangeListener<String>() {
					public void changed(ObservableValue<? extends String> changed,
							String oldValue, String newName) {
						if(!newName.equals("")) {
							// show the private window
							privateWindow(newName).show();
						}
					}
				});

		return scene;
	}

	/**
	 * A method to return a private chat window for two chatters
	 * @param selectedName
	 * @return
	 */
	public Stage privateWindow(String selectedName) {
		privateStage = new Stage();	
		stages.put(selectedName, privateStage);
		VBox vbox = new VBox(5);
		vbox.setPadding(new Insets(5,5,5,5));
		vbox.setAlignment(Pos.CENTER);
		private_textflow = new TextFlow();
		textflows.put(selectedName, private_textflow);
		/**set up the text area where text will be displayed*/
		private_textflow.setDisable(true);
		private_textflow.setLineSpacing(10);

		/** make the text area scroll-able */
		ScrollPane scroll_pane = new ScrollPane();
		scroll_pane.setContent(private_textflow);
		scroll_pane.setPrefSize(290, 325);
		scroll_pane.setPadding(new Insets(0,0,5,0));
		TextField field = new TextField();
		field.setPrefSize(305, 20);
		Button sender = new Button("send");
		sender.setPrefSize(80,10);
		HBox box = new HBox(5, field, sender);
		box.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(scroll_pane, box);
		Scene the_scene = new Scene(vbox, 400, 400); 

		privateStage.setTitle("Chat with "+selectedName);
		privateStage.setScene(the_scene);
		privateStage.setResizable(false);

		sender.setOnAction(e->{
			comment = "private_message"+"*"+selectedName + "*" + field.getText();
			field.setText("");
		});	
		return privateStage;
	}

	/**
	 * A method to return a given text flow
	 * 
	 */
	public TextFlow getPrivateTextflow(String str) {
		return textflows.get(str);
	}


    /**
     * A method to return a textflow object
     * @return Textflow
     */
	public TextFlow getTextFlow() {
		return textflow;
	}
	/** 
	 * return the last comment entered by the chatter 
	 * @return String
	 * */
	public String getComment() {
		String temp = comment;
		comment = "";
		return temp;	
	}

	/**
	 * A method to set the comment for the chat room
	 * @param c
	 */
	public void setComment(String c) {
		comment = c;
	}

	/**
	 * a method which returns the private chat window between two users
	 * @param name
	 * @return
	 */
	public Stage getStage(String name) {
		if(stages.get(name) != null) {
             return stages.get(name);
		}
		return privateWindow(name);
	}

}
