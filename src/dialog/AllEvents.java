package dialog;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import dialogAgent.BookingAgent;
import dialogAgent.ExcursionAgent;
import excursionManager.MultiMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.*;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * A class which displays most of the events currently available in the system
 */
public class AllEvents {
	private static HomePage homepage = new HomePage();
	private static ChatPage chatPage = new ChatPage();
	private static MainClass mainClass = new MainClass();
	private static EventDescriptionPage eventDescriptionPage = new EventDescriptionPage();
	private static ExcursionAgent excursionAgent = new ExcursionAgent();
	private static LoginPage loginPage = new LoginPage();
	/**
	 * A method which returns a scene which displays most of the available events
	 * @param excursionEvents
	 * @param user
	 * @return Scene 
	 */
	public Scene allEventsScene(MultiMap<String, String> excursionEvents, USERTYPE user) {
		BorderPane root = new BorderPane();
		/** add a background image */
		root.setStyle("-fx-background-image: url(images/top.jpg)");
		/** set up the title of the page */
		Text the_title = new Text("Available Excursion Events");
		the_title.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		the_title.setFill(Color.WHITE);
		/** set the page title according the to user type */
		switch(user) {
		case ORGANISER:
			the_title.setText("Available Excursion Events");
			break;
		case STUDENT_EVENTS:
			the_title.setText("Your Booked Excursion Events");
			break;
		default:
			break;
		}
		
		/** create labels for the menu bar */
		Label back = new Label("back");
		Label logout = new Label("logout");

		/** the label for the students events */
		Label my_events = new Label("My Events");
		
		/** create a label for a student to join the chat room */
		Label joinChat = new Label("join chat");

		/** create a menu bar */
		MenuBar menubar1 = new MenuBar();
		MenuBar menubar2 = new MenuBar();
		Menu menu_back = new Menu("", back);
		Menu menu_joinChat = new Menu("", joinChat);
		Menu menu_logout = new Menu("", logout);
		Menu menu_my_events = new Menu("", my_events);
		menubar1.setStyle("-fx-background-color: transparent");
		menubar2.setStyle("-fx-background-color: transparent");
		
		/** visibility of controls: add menus according to the user type */
		switch(user) {
		case STUDENT:
			menubar1.getMenus().addAll(menu_my_events, menu_joinChat);
			menubar2.getMenus().addAll(menu_logout);
			break;
		case ORGANISER:
			menubar2.getMenus().addAll(menu_back, menu_logout);
			break;
		case STUDENT_EVENTS:
			menubar2.getMenus().addAll(menu_back, menu_logout);
			break;
		}
		
		/**organise the menus on the menu bar*/
		Region rightSpacer = new Region();
		rightSpacer.getStyleClass().add("menu-bar");
		rightSpacer.setStyle("-fx-background-color: transparent");
		HBox.setHgrow(rightSpacer, Priority.SOMETIMES);
		HBox spaced_menubar = new HBox(menubar1, rightSpacer, menubar2);

		/** create a vertical box for the title and the menus */
		VBox box = new VBox(10);
		box.getChildren().addAll(spaced_menubar, the_title);
		box.setAlignment(Pos.CENTER); 
		/**add menu bar and the title at the top part of this border pane */
		root.setTop(box); 
		/** set up margins for the menu bar and title */
		BorderPane.setMargin(box, new Insets(0,0,10,0));
		BorderPane.setAlignment(box, Pos.CENTER);

		/** event handling: make a user join the chat room when they press on joinChat label*/
		joinChat.setOnMouseClicked(e->{
			/** display the chat window */
			mainClass.getMainStage().setScene(chatPage.chatScene(USERTYPE.STUDENT));
		});
		/** event handling: make a student see all their events when they click on my_events label */
		my_events.setOnMouseClicked(e->{
			/** display all the events of this student */
			mainClass.getMainStage().setScene(allEventsScene(excursionAgent.viewAllMYExcursionEvents(loginPage.getUserName()), USERTYPE.STUDENT_EVENTS));
		});

		/** event handling: make the user return to previous page depending on the type of user which was viewing the page */
		back.setOnMouseClicked(e->{
			
			switch(user) {
			case ORGANISER:
				/** load the home page if it is an organiser */
				mainClass.getMainStage().setScene(homepage.homeScene(excursionAgent.viewAllExcursions()));
				break;
				
			case STUDENT_EVENTS:
				/** load most of the available events for this student to view */
				mainClass.getMainStage().setScene(allEventsScene(excursionAgent.viewAllExcursionEvents(), USERTYPE.STUDENT));
				break;
			default:
				break;
			}
		});

		/**event handling: log out the user*/
		logout.setOnMouseClicked(e->{
			mainClass.logout();
		});

		/** create a grid pane for showing all events in an organised manner */
		GridPane grid = new GridPane();
		grid.setHgap(25);
		grid.setVgap(10);

		/** store all the events IDs in a list */
		ArrayList<String> eventIDs = new ArrayList<String>();
		for(String id: excursionEvents.keySet()) {
			eventIDs.add(id);
		}
		/** create columns and rows variables for organising data on the grid pane */
		int column = 0;
		int row = 4;

		/** show titles of excursion events on the screen */
		for(String eventID : eventIDs) {
			String name = ((ArrayList<String>)excursionEvents.get(eventID)).get(0);
			String date = ((ArrayList<String>)excursionEvents.get(eventID)).get(3);
			String description = ((ArrayList<String>)excursionEvents.get(eventID)).get(1);
			/** create a click-able link for each excursion event */
			Hyperlink excursionEvent = new Hyperlink(name+"\n"+date);
			excursionEvent.setStyle("-fx-underline: false");
			excursionEvent.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
			GridPane childGrid = new GridPane();
			childGrid.setPrefSize(200,75);
			childGrid.setAlignment(Pos.CENTER);
			childGrid.setStyle("-fx-border-width:2px;-fx-border-color:white");
			// add the excursion event to the child grid pane
			childGrid.add(excursionEvent,0,0,4,4);
			// add the child grid pane to the parent grid pane
			grid.add(childGrid, column, row, 4, 1);
			/** handle events on displayed excursion events: load the corresponding description page for each event */
			excursionEvent.setOnAction(e->{	
				/** first gather data corresponding to this specific excursion event */			
				String id = ((ArrayList<String>)excursionEvents.get(eventID)).get(2);
				String maxParticipants = ((ArrayList<String>)excursionEvents.get(eventID)).get(4);
				String currentParticipants = ((ArrayList<String>)excursionEvents.get(eventID)).get(5);
				/** load the description page of this particular excursion */
				mainClass.getMainStage().setScene(eventDescriptionPage.eventDescriptionScene(name, description, id, eventID, date, user, maxParticipants, currentParticipants));
			});
			
			/**make use of a tooltip object to show the description of the excursion when it gets hovered*/
			Tooltip hover= new Tooltip();
			hover.setText(description);
			excursionEvent.setTooltip(hover);

			/** update the columns and rows in the grid pane */
			column+=4;
			if(column > 11) {
				column = 0;
				row += 1;
			}
		}
		/** place the grid pane at the centre of this border pane */
		root.setCenter(grid);
		grid.setAlignment(Pos.TOP_CENTER);
		BorderPane.setAlignment(grid, Pos.CENTER);
		/**return a scene which displays most of the currently available events*/
		Scene scene = new Scene(root, MainClass.STAGE_WIDTH, MainClass.STAGE_HEIGHT);
		scene.getStylesheets().add("styles/eventDescriptionStyle.css");
		return scene;
	}
}
