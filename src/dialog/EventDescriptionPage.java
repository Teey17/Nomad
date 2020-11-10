package dialog;

import java.sql.Timestamp;
import java.util.Optional;

import dialogAgent.BookingAgent;
import dialogAgent.ExcursionAgent;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * A class which shows the description of an event
 */
public class EventDescriptionPage {
	private static AddEditEvent addEditEvent = new AddEditEvent();
	private static ExcursionAgent excursionAgent = new ExcursionAgent();
	private static MainClass mainClass = new MainClass();
	private static AllEvents events = new AllEvents();
	private static LoginPage loginPage = new LoginPage();
	private static BookingAgent bookingAgent = new BookingAgent();
	private static ParticipantsPage participantsPage = new ParticipantsPage();
	/**
	 * A method which display an event on screen and enables the user to interact with it
	 * @param title
	 * @param description
	 * @param id
	 * @param eventID
	 * @param date
	 * @param user
	 * @param maxParticipants
	 * @param currentParticipants
	 * @return Scene
	 */
	public Scene eventDescriptionScene(String title, String description, String id, String eventID, String date, USERTYPE user, String maxParticipants, String currentParticipants) {
		BorderPane root = new BorderPane();
		/**add a background image*/
		root.setStyle("-fx-background-image: url(images/bigben.jpg)");
		/** borders */ 
		mainClass.decorator(root);
		/** set up the title */
		Text the_title = new Text(title);
		the_title.setFill(Color.WHITE);
		the_title.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));

		/** set up the date */
		Text the_date = new Text(date);
		the_date.setFill(Color.WHITE);
		the_date.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
		
		/** set up information about the participants */
		String participants = "Participants: "+currentParticipants+"/"+maxParticipants;
		Text the_participants = new Text(participants);
		the_participants.setFill(Color.WHITE);
		the_participants.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));

		/**group the title, the date and the description*/
		VBox event_details = new VBox(10);
		event_details.setPadding(new Insets(10,10,0,10));
		event_details.setAlignment(Pos.CENTER);
		event_details.getChildren().addAll(the_title, the_date, the_participants);

		/** set up the description of this event */
		Text the_description = new Text(description);
		the_description.setFill(Color.WHITE);
		the_description.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
		root.setCenter(the_description);
		BorderPane.setMargin(the_description, new Insets(10,10,10,10));
		BorderPane.setAlignment(the_description, Pos.TOP_CENTER);

		/** create labels for the menu bar */
		Label edit_event = new Label("Edit Event");
		edit_event.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));

		Label delete_event = new Label("Delete Event");
		delete_event.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));

		Label back = new Label("back");
		back.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));

		Label logout = new Label("logout");
		logout.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        /**create a label for booking events*/
		Label book_label = new Label("Book Event");
		book_label.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
		
		 /** create a label for confirmed participants for the student to view */
		Label confirmed_participants = new Label("Participants");
		confirmed_participants.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
	
		/** create a label for a student to cancel his or her bookings */
		Label cancel_my_booking = new Label("Cancel Booking");
		cancel_my_booking.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
		
		/** create a a label for the organiser to view participants in an event */
		Label participants_label = new Label("Participants");
		participants_label.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));


		/** create the menu bar */
		MenuBar menubar1 = new MenuBar();
		MenuBar menubar2 = new MenuBar();
		Menu menu_participants = new Menu("", participants_label);
		Menu menu_edit = new Menu("", edit_event);
		Menu menu_delete = new Menu("", delete_event);
		Menu menu_back = new Menu("", back);
		Menu menu_logout = new Menu("", logout);
		Menu menu_book = new Menu("", book_label);
		Menu menu_confirmed = new Menu("", confirmed_participants);
		Menu menu_cancel_my_booking = new Menu("", cancel_my_booking);
		menubar1.setStyle("-fx-background-color: transparent");
		menubar2.setStyle("-fx-background-color: transparent");
		
		/** add menus according to user type */
		switch(user) {
		case STUDENT:
			menubar1.getMenus().addAll(menu_book, menu_confirmed);
			break;
		case ORGANISER:
			menubar1.getMenus().addAll(menu_edit, menu_delete, menu_participants);
			break;
		case STUDENT_EVENTS:
			menubar1.getMenus().addAll(menu_cancel_my_booking);
		}

		menubar2.getMenus().addAll(menu_back, menu_logout);

		/**organise menus on the menu bar*/
		Region rightSpacer = new Region();
		rightSpacer.setStyle("-fx-background-color: transparent");
		HBox.setHgrow(rightSpacer, Priority.SOMETIMES);
		HBox spaced_menubar = new HBox(menubar1, rightSpacer, menubar2);

		/**add title, date, information about participants and the menu bar at the top of this border pane*/
		VBox box = new VBox(10);
		box.getChildren().addAll(spaced_menubar, event_details);
		box.setAlignment(Pos.CENTER); 
		root.setTop(box);
		
		/**event handling: to display of the confirmed participants for the student to view*/
		// event handling: view all participants in this event
	    confirmed_participants.setOnMouseClicked(e->{
			// load the scene that displays all participants in an event
			mainClass.getMainStage().setScene(participantsPage.participantsScene(bookingAgent.viewAllParticipants(Integer.parseInt(eventID)), title, description, id, eventID, date, maxParticipants, currentParticipants,
					USERTYPE.STUDENT));
		});
		
		/** event handling: to view all participants in this event */
	    participants_label.setOnMouseClicked(e->{
			/** load the scene that displays all participants in an event */
			mainClass.getMainStage().setScene(participantsPage.participantsScene(bookingAgent.viewAllParticipants(Integer.parseInt(eventID)), title, description, id, eventID, date, maxParticipants, currentParticipants,
					USERTYPE.ORGANISER));
		});

		/**event handling: make a student attempt to cancel their booking by clicking on this label*/
		cancel_my_booking.setOnMouseClicked(e->{
			// ask the user if they really want to cancel the booking
			Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to cancel this event?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                // make an attempt to cancel the booking
				boolean cancel_decision = bookingAgent.cancelBookExcursionEvent(loginPage.getUserName(), Integer.parseInt(eventID), timestamp);

				if(cancel_decision) {
					// confirm booking cancellation
					mainClass.alerts(new Alert(AlertType.INFORMATION), "Information", "You have successfully cancelled your booking");
					// display all the current events of this student
					mainClass.getMainStage().setScene(events.allEventsScene(excursionAgent.viewAllMYExcursionEvents(loginPage.getUserName()), USERTYPE.STUDENT_EVENTS));
				}
				else {
					// inform the student that the cancellation failed
					mainClass.alerts(new Alert(AlertType.ERROR), "Information", "The event could not be cancelled");
				}
			}
		});

		/** event handling: make a student attempt to book an event when they click on the book label */
		book_label.setOnMouseClicked(e->{
             /**determine whether or not booking is still possible*/
			if(Integer.parseInt(currentParticipants) >= Integer.parseInt(maxParticipants)) {
				mainClass.alerts(new Alert(AlertType.INFORMATION), "Information", "Sorry! This event is fully Booked");
				return;
			}
			Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to book this event?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				// perform booking attempt
				Timestamp time = new Timestamp(System.currentTimeMillis());
				boolean booking_decision = bookingAgent.bookExcursionEvent(Integer.parseInt(eventID), loginPage.getUserName(), time);

				if(booking_decision) {
					// inform the user that the booking attempt succeeded
					mainClass.alerts(new Alert(AlertType.INFORMATION), "Information", "You have successfully booked this event");
				}
				else {
					// inform the user that the booking attempt failed
					mainClass.alerts(new Alert(AlertType.ERROR), "Information", "The event could not be booked");
				}
			}			
		});

		/** event handling: make a user edit an excursion when they click on this label */
		edit_event.setOnMouseClicked(e->{
			// load the edit excursion page 
			addEditEvent.addEditEvent(title, description, id, eventID, date, OPERATION.EDIT, maxParticipants, currentParticipants);
		});

		/** event handling: make a user attempt to delete an excursion when they click on the delete label */
		delete_event.setOnMouseClicked(e->{
			Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this event?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				boolean delete_decision = excursionAgent.cancelExcursionEvent(Integer.parseInt(eventID));
				if(delete_decision) {
					// confirm the success of the deletion attempt
					mainClass.alerts( new Alert(Alert.AlertType.INFORMATION), "Event deletion", "This event was deleted");
					// load the events page with the up-to-date excursion events
					mainClass.getMainStage().setScene(events.allEventsScene(excursionAgent.viewAllExcursionEvents(), USERTYPE.ORGANISER));
				}
				else {
					// inform the user that the delete attempt failed
					mainClass.alerts( new Alert(Alert.AlertType.ERROR), "Failed Attempt", "The event was not deleted");	
				}
			}
		});	

		/** event handling: make the user log out when they click on the logout label */
		logout.setOnMouseClicked(e->{
			mainClass.logout();
		});

		/** event handling: make the user return back to the home page based on the type of user which is logged in*/
		back.setOnMouseClicked(MouseEvent->{
			// load the events page with the up-to-date excursion event
			if(user == USERTYPE.ORGANISER) {
				mainClass.getMainStage().setScene(events.allEventsScene(excursionAgent.viewAllExcursionEvents(), USERTYPE.ORGANISER));
			}
			// load all events for the student to view
			else if(user == USERTYPE.STUDENT) {
				mainClass.getMainStage().setScene(events.allEventsScene(excursionAgent.viewAllExcursionEvents(), USERTYPE.STUDENT));
			}
			// load all the events this student has paid for
			else if(user == USERTYPE.STUDENT_EVENTS) {
				mainClass.getMainStage().setScene(events.allEventsScene(excursionAgent.viewAllMYExcursionEvents(loginPage.getUserName()), USERTYPE.STUDENT_EVENTS));
			}
		});

		/**return a view with the description of this excursion*/
		Scene scene = new Scene(root, MainClass.STAGE_WIDTH, MainClass.STAGE_HEIGHT);
		scene.getStylesheets().add("styles/eventDescriptionStyle.css");

		return scene;
	}
}
