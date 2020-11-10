package dialog;

import dialogAgent.ExcursionAgent;
import dialogAgent.UserAgent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * A class which provides the user with the screen where they can login
 */
public class LoginPage {
	// static references to other classes so as to delegate their class-specific operation to them
	private static UserAgent userAgent = new UserAgent();
	private static RegistrationPage registrationPage = new RegistrationPage();
	private static MainClass mainClass = new MainClass();
	public static RenewPassword renewPass = new RenewPassword();
	private static ExcursionAgent excursionAgent = new ExcursionAgent();
	private static HomePage homepage = new HomePage();
	private static AllEvents allEvents = new AllEvents();
	private static USERTYPE choice = USERTYPE.STUDENT;
	public static String user_name;

	/**
	 *  A method which displays the login screen in the main window
	 * @param none
	 * @return Scene
	 */
	public Scene loginScene(){
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(100,100,100,100));
		// add a background image
		root.setStyle("-fx-background-image: url(images/ams.png)");
		// create the grid pane for arranging the elements on the page
		GridPane gridPane = new GridPane();
		gridPane.setId("grid-pane");
		gridPane.setHgap(15);
		gridPane.setVgap(15);
		gridPane.setPadding(new Insets(70,10,10,10));
		gridPane.setAlignment(Pos.CENTER);


		// create a label for the user name and its text field
		Label username = new Label("Username");
		username.setTextFill(Color.DARKRED);
		gridPane.add(username, 0, 2);
		TextField name = new TextField();
		gridPane.add(name, 1,2 );

		// create a label for the password and its text field
		Label password = new Label("Password");
		password.setTextFill(Color.DARKVIOLET);
		gridPane.add(password, 0,3);
		PasswordField pass = new PasswordField();
		gridPane.add(pass, 1,3);

		// create buttons for registering and logging in
		Button login_button = new Button("login");
		login_button.setTextFill(Color.GREEN);
		login_button.setPrefSize(90, 10);
		Button register_button = new Button("register");
		register_button.setTextFill(Color.RED);
		register_button.setPrefSize(90, 10);

		// add these buttons in the grid pane
		HBox temp = new HBox(10);
		temp.setAlignment(Pos.CENTER);
		temp.getChildren().addAll(login_button, register_button);
		gridPane.add(temp, 1, 4);

		// create radio buttons for determining the type of user that is logging in
		final ToggleGroup group = new ToggleGroup();        
		RadioButton radio_student = new RadioButton("Student");
		radio_student.setToggleGroup(group);
		radio_student.setSelected(true);
		choice = USERTYPE.STUDENT;
		RadioButton radio_organiser = new RadioButton("Organiser");
		radio_organiser.setToggleGroup(group);
		HBox temp2 = new HBox(10);
		temp2.getChildren().addAll(radio_student, radio_organiser);
		gridPane.add(temp2, 1,5);

		// set up the title of the application
		Label welcome = new Label("NOMAD");
		welcome.setId("nomadlabel");
		welcome.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(welcome, Pos.CENTER);
		root.setTop(welcome);
		// set up the border pane with this grip pane in the centre of it
		root.setCenter(gridPane);

		// create a hyperlink for changing passwords
		Hyperlink renewPassword = new Hyperlink("change password?");
		renewPassword.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(renewPassword, Pos.CENTER);
		// add to this hyperlink the grid pane
		gridPane.add(renewPassword, 1, 7);

		/** event handling: enable to user to renew their password when they click on this link */
		renewPassword.setOnAction(e->{
			mainClass.getMainStage().setScene(renewPass.renewPasswordScene());

		});
 
		/** event handling: store the type of user that is logging in so the appropriate home page gets 
		 * loaded in the main window */
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
					Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() == radio_student) {
					choice = USERTYPE.STUDENT;
				}
				else if(group.getSelectedToggle() == radio_organiser) {
					choice = USERTYPE.ORGANISER;
				}                
			}
		});

		/** event handling: attempt to log in and inform the user of the outcome  */    
		login_button.setOnAction(e->{
			// extract the user name and password fields and make sure they are valid
			String userName = name.getText();
			String userPass = pass.getText(); 
			if(userName.contains(" ") || userPass.contains(" ")) {
				// inform the user that the log in attempt failed
				mainClass.alerts( new Alert(Alert.AlertType.ERROR), "Login Failure", "Invalid user name or password");
				return;
			}
			boolean decision = false; 
			// send extracted data to the respective use case controller
			if(userName.length()>0 && userPass.length()>0  && choice == USERTYPE.ORGANISER) {
				decision = userAgent.login(userName, userPass, false);
			}
			if(userName.length()>0 && userPass.length()>0  && choice == USERTYPE.STUDENT) {
				decision = userAgent.login(userName, userPass, true);
			}
			// organiser's view
			if(decision && choice == USERTYPE.ORGANISER) {
				// store user name 
				user_name = userName;
				// display excursions for the organiser to view
				mainClass.getMainStage().setScene(homepage.homeScene(excursionAgent.viewAllExcursions()));	
			}
			// student's view
			else if(decision && choice == USERTYPE.STUDENT) {
				// store user name
				user_name = userName;
				// display most of the events for the student to view
				mainClass.getMainStage().setScene(allEvents.allEventsScene(excursionAgent.viewAllExcursionEvents(), USERTYPE.STUDENT));
			}
			else {
				// inform the user that the log in attempt failed
				mainClass.alerts( new Alert(Alert.AlertType.ERROR), "Login Failure", "Your login was not successful");
			}
		});

		/** event handling: load the registration portal for students to register */
		register_button.setOnAction(e->{
			mainClass.getMainStage().setScene(registrationPage.registrationScene());
		});

		/** return the scene which can be used to log into the system */
		Scene scene = new Scene(root,700,700);
		scene.getStylesheets().add("styles/loginStyle.css");
		return scene;
	}
	
	/**
	 * A method which returns the user name of the logging in user
	 * @return
	 */
	public String getUserName() {
		return user_name;
	}
}
