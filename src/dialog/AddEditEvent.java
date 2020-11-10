package dialog;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.sql.Date;
import java.sql.Timestamp;

import dialogAgent.ExcursionAgent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class provides a user interface for adding an event and editing an event
 */
public class AddEditEvent {
	private static MainClass mainClass = new MainClass();
	private static ExcursionAgent excursionAgent = new ExcursionAgent();
	private static EventDescriptionPage eventDescriptionPage = new EventDescriptionPage();
	/**
	 * A method simply which enables the user to create an event or to edit an event, then notifies the user graphically
	 * about whether or not it was successful
	 * @return void 
	 * @param title
	 * @param description
	 * @param id
	 * @param eventID
	 * @param the_date
	 * @param operation
	 * @param maxParticipants
	 * @param currentParticipants
	 */
	public void addEditEvent(String title, String description, String id, String eventID, String the_date, OPERATION operation, String max_Participants, String currentParticipants) {	
		Stage stage = new Stage();

		// page title
		switch(operation) {
		case ADD:
			stage.setTitle("EVENT CREATION");
			break;
		case EDIT:
			stage.setTitle("EVENT EDITING");
			break;
		}

		/** create a date picker for picking dates*/
		DatePicker datepicker = new DatePicker();

		/** make it impossible to enter a date manually*/
		datepicker.getEditor().setDisable(true);

		/** disable past dates*/
		datepicker.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				setDisable(empty || date.compareTo(today) < 0 );
			}
		});

		/** set up the smaller window for creating or editing an event. it pops up when the user selects add event or 
		 * edit event.*/
		VBox root = new VBox(15);
		root.setStyle("-fx-background-image: url(images/bigben.jpg)");
		root.setPadding(new Insets(10,10,10,10));
		root.setAlignment(Pos.CENTER);
		Label event_date = new Label("Select Event Date");
		TextField participantsField = new TextField();
		participantsField.setPromptText("Max Number Of Participants");
		participantsField.setMaxSize(292, 10);
		Button acceptDetails = new Button("Validate");
		root.getChildren().addAll(event_date, datepicker, participantsField, acceptDetails);

		/** ensure that only a valid whole number can be added for number of participants*/
		participantsField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (!newValue.matches("[0-9]*")) {
					participantsField.setText(oldValue);
				}
			}
		});

		/** validate or discard event creation or modification*/
		acceptDetails.setOnAction(new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent e) 
			{ 
				/** get the date a store it as a time stamp*/
				LocalDate localdate = datepicker.getValue();
				/**remind the user to select a date, in case he/she forgets*/
				if (localdate == null) {
					mainClass.alerts(new Alert(Alert.AlertType.INFORMATION), "Information", "Please Select a Date");
					return;
				}
				/** get the time stamp*/
				Timestamp germanTimestamp = Timestamp.valueOf(localdate.atStartOfDay());
				int temp;
				int maxParticipants;
				/** extract the text field for the number of participants. remind the user if they forget to enter a number
				 *of participants*/
				if(!participantsField.getText().equals("")&&(temp = Integer.parseInt(participantsField.getText()))>0) {
					maxParticipants = temp;
				}
				else {
					mainClass.alerts(new Alert(Alert.AlertType.INFORMATION), "Information", "Please Enter a valid Maximum Number of Participants");
					return;
				}

				/** close the smaller window after validating or discarding the event creation or modification*/
				stage.close();
				boolean decision=false;
				String alertHeader = "";
				String alertMessage = "";
				String alertFailHeader = "Failure";
				String alertFailMessage = "";
				/** attempt to create an event and then inform the user of the outcome */
				if(germanTimestamp.toString().length()>0 && operation == OPERATION.ADD) {
					decision = excursionAgent.createExcursionEvent(title, description, Integer.parseInt(id), germanTimestamp, maxParticipants);
					alertHeader = "Event Creation";
					alertMessage = "The event was created";
					alertFailHeader = "Failed Creation Attempt";
					alertFailMessage = "The event was not created. it may already exist";
				}
				/** attempt to edit an event and then inform the user of the outcome*/
				if(germanTimestamp.toString().length()>0 && operation == OPERATION.EDIT) {
					decision = excursionAgent.editExcursionEvent(title, description, Integer.parseInt(id), Integer.parseInt(eventID), germanTimestamp, maxParticipants);
					alertHeader = "Event Modification";
					alertMessage = "The Event was successfully edited";
					alertFailHeader = "Failed Editing Attempt";
					alertFailMessage = "The event was not edited";
				}
				if(decision && operation == OPERATION.ADD) {
					/** inform the organiser of the successful creation of the event	*/
					mainClass.alerts(new Alert(Alert.AlertType.INFORMATION), alertHeader, alertMessage);
				}
				/** after successfully editing an event, reload its description page*/
				else if(decision && operation == OPERATION.EDIT){
					mainClass.alerts(new Alert(Alert.AlertType.INFORMATION), alertHeader, alertMessage);
					mainClass.getMainStage().setScene(eventDescriptionPage.eventDescriptionScene(title, description, id, eventID, germanTimestamp.toString().substring(0,10), USERTYPE.ORGANISER, ""+maxParticipants, currentParticipants));
				}
				else {
					/**inform the user here if creating or editing an event failed*/
					mainClass.alerts(new Alert(Alert.AlertType.ERROR), alertFailHeader, alertFailMessage);
				}		
			} 
		});

		/**this is the scene that is returned by the function. it displays the GUI for creating or editing events*/
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add("styles/loginStyle.css");
		stage.setScene(scene);
		stage.show();
	}	
}
