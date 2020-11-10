package dialog;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

import dialogAgent.BookingAgent;
import excursionManager.MultiMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * A class which displays all participants in a given event. 
 * first those that have paid, then those that have not paid yet
 */
public class ParticipantsPage {
	private static MainClass mainClass = new MainClass();
	private static BookingAgent bookingAgent = new BookingAgent();
	private static EventDescriptionPage eventDescriptionPage = new EventDescriptionPage();
	private int changedNumberOfParticipants;

	/**
	 * A method which displays all participants of an event on the screen
	 * @param participants
	 * @param title
	 * @param description
	 * @param id
	 * @param eventID
	 * @param date
	 * @param maxParticipants
	 * @param currentParticipants
	 * @param user
	 * @return Scene
	 */
	public Scene participantsScene(MultiMap<String, String> participants, String title, String description, String id, String eventID, String date, 
			String maxParticipants, String currentParticipants, USERTYPE user) {
		// store the current number of participants
		changedNumberOfParticipants = (Integer.parseInt(currentParticipants));
		BorderPane root = new BorderPane();
		// add a background image
		root.setStyle("-fx-background-image: url(images/paris.jfif)");
		// set up the title
		Text the_title = new Text("List of Participants in "+title);		
		// borders
		mainClass.decorator(root);

		// set date of the event
		the_title.setFill(Color.WHITE);
		the_title.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
		Text the_date = new Text("Event Date: "+date);
		the_date.setFill(Color.WHITE);
		the_date.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));

		// set up information about the participants
		String event_participants = "Confirmed Participants: "+currentParticipants+"/"+maxParticipants;
		Text the_participants = new Text(event_participants);
		the_participants.setFill(Color.WHITE);
		the_participants.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));

		// store title, date and participants information in a vertical container
		VBox container = new VBox(10);
		container.setAlignment(Pos.CENTER);
		container.getChildren().addAll(the_title, the_date, the_participants);
		container.setPadding(new Insets(5, 0, 10, 0));

		// get all the names of participants and store them in orderly manner in a linked list
		LinkedList<String> list_names = new LinkedList<String>();
		for(String name : participants.keySet()) {
			String temp = ((ArrayList<String>)participants.get(name)).get(0).equals("true")?"paid":"unpaid";
			if(user == USERTYPE.ORGANISER) {
				if(temp.equals("paid")) {
					list_names.addFirst(name +" ("+temp+")");
				}
				else {
					list_names.addLast(name +" ("+temp+")");
				}
			}
			else if(user == USERTYPE.STUDENT) {
				// make the students only see only confirmed participants
				if(temp.equals("paid")) {
					list_names.addLast(name);
				}
			}
		}

		// create an observable list
		ObservableList<String> observableParticipants = FXCollections.observableArrayList(list_names);
		// create a list view with the participants
		ListView<String> participantsList = new ListView<>(observableParticipants);
		participantsList.setStyle("-fx-background-image: url(images/paris.jfif)");
		// Set up the list view with its size and default content
		participantsList.setPrefSize(200, 300);
		participantsList.setPlaceholder(new Label("No one has booked a place in this event"));


		// create labels
		Label back = new Label("back");
		back.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
		back.setTextFill(Color.BLUE);

		Label logout = new Label("logout");
		logout.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
		logout.setTextFill(Color.BLUE);

		Menu menu_back = new Menu("", back);
		Menu menu_logout = new Menu("", logout);
		MenuBar menubar = new MenuBar();
		menubar.getMenus().addAll(menu_back, menu_logout);
		menubar.setStyle("-fx-background-color: transparent");


		// organise menus on the menu bar
		Region rightSpacer = new Region();
		rightSpacer.setStyle("-fx-background-color: transparent");
		HBox.setHgrow(rightSpacer, Priority.SOMETIMES);
		HBox spaced_menubar = new HBox(rightSpacer, menubar);

		// store the title and the menu bar in a vertical container and place the container
		// at the center of the borderpane
		VBox box = new VBox(10);
		box.getChildren().addAll(spaced_menubar, container);
		box.setAlignment(Pos.CENTER); 		
		BorderPane.setAlignment(box, Pos.CENTER);
		root.setCenter(participantsList);
		root.setTop(box);

		/** event handling: make a user log out by loading the login page when they click on the logout label */
		logout.setOnMouseClicked(e->{
			mainClass.logout();
		});

		/** make a user return to description page when they click on the back label */
		back.setOnMouseClicked(e->{
			switch(user) {
			case ORGANISER:
		        	mainClass.getMainStage().setScene(eventDescriptionPage.eventDescriptionScene(title, description, id, eventID, date, USERTYPE.ORGANISER,
					       maxParticipants, ""+changedNumberOfParticipants));
		        	break;
			case STUDENT:
				mainClass.getMainStage().setScene(eventDescriptionPage.eventDescriptionScene(title, description, id, eventID, date, USERTYPE.STUDENT,
					       maxParticipants, ""+changedNumberOfParticipants));
				break;
			default:
				break;
			}
		});

		/** event handling: handle events on the list view: the user may attempt to change a payment status or to
		 * cancel a booking */

		// Get the list view selection model. 
		MultipleSelectionModel<String> lvSelModel= participantsList.getSelectionModel();

		// Use a change listener to listen to actions on list view
		lvSelModel.selectedItemProperty().addListener(
				new ChangeListener<String>() {
					public void changed(ObservableValue<? extends String> changed,
							String oldValue, String newValue) {
						// create a new smaller stage to display two functionalities to the user: change payment status
						// and cancel booking
						Stage stage = new Stage();
						Button change_status = new Button("change payment status");
						change_status.setPrefSize(160, 10);
						Button cancel_booking = new Button("cancel booking");
						cancel_booking.setPrefSize(160, 10);
						HBox root = new HBox(10, change_status, cancel_booking);
						root.setStyle("-fx-background-color: #c2d6d6;");
						root.setAlignment(Pos.CENTER);
						stage.setScene(new Scene(root, 400, 200));
						stage.show();
						String userName = newValue.split("[ ]")[0];

						/**event handling in the small window: make an attempt to change the payment status when the user 
						 * clicks on this button*/
						change_status.setOnAction(e->{
							Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to change \n the payment status of "+userName+ "?");
							Optional<ButtonType> result = alert.showAndWait();

							if (result.isPresent() && result.get() == ButtonType.OK) {
								boolean status = !newValue.contains("(paid)")? true : false;
								// attempt to make the change of status
								boolean change_decision = bookingAgent.changePaymentStatus(userName, status, Integer.parseInt(eventID));
								int update = status ? 1 : -1;
								if(change_decision) {
									// confirm the successful operation
									mainClass.alerts(new Alert(AlertType.INFORMATION), "Information", "You have successfully changed \n the payment status of "+userName);
									stage.close();
									// reload the list view of potential participants
									mainClass.getMainStage().setScene(participantsScene(bookingAgent.viewAllParticipants(Integer.parseInt(eventID)),title, description,id, eventID, date,
											maxParticipants, ""+(changedNumberOfParticipants+update), user));
								}
								else {
									// inform user that the change status attempt failed
									mainClass.alerts(new Alert(AlertType.ERROR), "Information", "The payment status of " + userName+ "\n could not be modified");
									stage.close();
								}
							}
						});
						/**event handling in the new smaller window: make an attempt to cancel a booking when the user clicks on this booking*/
						cancel_booking.setOnAction(e->{							
							Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to cancel the booking of "+userName +" ?");
							Optional<ButtonType> result = alert.showAndWait();
							if (result.isPresent() && result.get() == ButtonType.OK) {
                                // set up data required for cancelling a booking, date and time
								String booking_date = ((ArrayList<String>)participants.get(userName)).get(1);
								DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
								Date the_date = null;
								try {
									the_date = formatter.parse(booking_date);
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
								// get the time stamp
								Timestamp timestamp = new Timestamp(the_date.getTime());
								// attempt to cancel the booking
								boolean change_decision = bookingAgent.cancelBookExcursionEvent(userName, Integer.parseInt(eventID),timestamp);
								// confirm or discard booking
								if(change_decision) {
									// confirm the successful cancellation attempt
									mainClass.alerts(new Alert(AlertType.INFORMATION), "Information", "You have successfully canceled the booking of "+userName);
									stage.close();
									// reload the list of participants
									mainClass.getMainStage().setScene(participantsScene(bookingAgent.viewAllParticipants(Integer.parseInt(eventID)),title, description,id, eventID, date,
											maxParticipants, ""+changedNumberOfParticipants, user));
								}
								else {
									// inform the user that the cancellation attempt failed
									mainClass.alerts(new Alert(AlertType.ERROR), "Information", "The booking of "+userName+" could not be cancelled.");
									stage.close();
								}
							}

						});
					}
				});

		/**return a scene which displays the current number of participants in this event in the main window*/
		Scene scene = new Scene(root, MainClass.STAGE_WIDTH, MainClass.STAGE_HEIGHT);
		scene.getStylesheets().add("styles/eventDescriptionStyle.css");
		return scene;

	}
}
