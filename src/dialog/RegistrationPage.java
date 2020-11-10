package dialog;

import dialogAgent.UserAgent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
/**
 * 
 * @author NOMAD
 * This class provides a registration portal for students to register
 *
 */
public class RegistrationPage {
	
	public static LoginPage loginPage = new LoginPage();
	public static MainClass mainClass = new MainClass();
	private static UserAgent userAgent = new UserAgent();
	/**
	 * A method which returns a view where students can register
	 * @param none
	 * @return Scene
	 */
	public Scene registrationScene() {
		BorderPane root = new BorderPane();
		// add a background image
		root.setStyle("-fx-background-image: url(images/ams.png)");
        root.setPadding(new Insets(10,10,10,10));
        
        // grid pane to organise controls
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setAlignment(Pos.CENTER);

        // create the children of the grid pane
        // set up the welcome text
        Text message = new Text("Please register here");
		message.setFill(Color.WHITE);
        gridPane.add(message, 1, 0, 2, 1);
        
        // create input fields and their labels
        Label username = new Label("Username");
        username.setTextFill(Color.DARKVIOLET);
        gridPane.add(username, 0, 2);
        TextField name = new TextField();
        gridPane.add(name, 1,2 );
        
        Label password = new Label("Password");
        password.setTextFill(Color.GREEN);
        gridPane.add(password, 0,3);
        PasswordField pass = new PasswordField();
        gridPane.add(pass, 1,3);
        
        Label confirmPassword = new Label("Confirm Password");
        confirmPassword.setTextFill(Color.BLUE);
        gridPane.add(confirmPassword, 0,4);
        PasswordField confirmPass = new PasswordField();
        gridPane.add(confirmPass, 1,4);
        
        // create buttons for creating events
        Button register_button = new Button("register");
        register_button.setTextFill(Color.GREEN);
        register_button.setPrefSize(90, 10);
        
        Button back_button = new Button("back to login");
        back_button.setTextFill(Color.RED);
        back_button.setPrefSize(90, 10);

        // organise the buttons in an horizontal controller and add them to the grid pane
        HBox temp = new HBox(10);
        temp.getChildren().addAll(register_button, back_button);
        gridPane.add(temp, 1, 5);
        
        // create a title and place it at the top of the border pane
        Label welcome = new Label("REGISTRATION PORTAL");
        welcome.setId("reglabel");
        welcome.setId("reglabel");
        welcome.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(welcome, Pos.CENTER);
        root.setTop(welcome);
        
        // place the grid pane at the center of the border pane
        root.setCenter(gridPane);
        // set up the scene to be returned
		Scene scene = new Scene(root, MainClass.STAGE_WIDTH, MainClass.STAGE_HEIGHT);
		
		/** event handling: make the user return the login page when they click on back button */
		back_button.setOnAction(e->{
			// return to the login screen 
			mainClass.getMainStage().setScene(loginPage.loginScene());
		});
		
		/** event handling: make an attempt to register a student when they click on the register button */
		register_button.setOnAction(e->{
			boolean decision = false;
			String the_name = name.getText();
			String the_pass = pass.getText();
			// extract data from the respective fields
			if(!the_name.contains(" ") &&  !the_pass.contains(" ") && the_name.length()>0 && the_pass.length()>0 
					&& the_pass.equals(confirmPass.getText())) {
		      	decision = userAgent.register(name.getText(), pass.getText(), true);
			}
			// take appropriate action with respect to the decision
			if(decision) {
				// inform user about the successful registration attempt
				mainClass.alerts(new Alert(Alert.AlertType.INFORMATION), "Registration Confirmation", "Your registration was successful");
				name.setText("");
				pass.setText("");
				confirmPass.setText("");
			}
			else {
				// inform user about the failed registration attempt
				mainClass.alerts(new Alert(Alert.AlertType.ERROR), "Error Message", "Your registration failed");
				name.setText("");
				pass.setText("");
				confirmPass.setText("");
			}
		});
		// return the scene which provides a view for registration
        scene.getStylesheets().add("styles/loginStyle.css");
		return scene;
	}

}
