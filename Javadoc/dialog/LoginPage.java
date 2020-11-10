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
import javafx.scene.text.*;


public class LoginPage {
	private static UserAgent userAgent = new UserAgent();
	private static RegistrationPage registrationPage = new RegistrationPage();
	private static MainClass mainClass = new MainClass();
	public static RenewPassword renewPass = new RenewPassword();
	private static ExcursionAgent excursionAgent = new ExcursionAgent();
	private static HomePage homepage = new HomePage();
    private boolean choice = true;
    private Alert alert;
    
    /** 
     * A scene generated for login page.
     * @return Scene
     */
    // return the login scene
    public Scene loginScene(){
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10,10,10,10));
        // the grid pane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setAlignment(Pos.CENTER);

        // the children of the grid pane
        // the welcome text
        Text message = new Text("Please log in here");
        message.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        message.setFill(Color.DARKGREEN);
        gridPane.add(message, 1, 0, 2, 1);
        // input fields and their labels
        Label username = new Label("Username");
        username.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        username.setTextFill(Color.DARKRED);
        gridPane.add(username, 0, 2);
        TextField name = new TextField();
        gridPane.add(name, 1,2 );
        Label password = new Label("Password");
        password.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        password.setTextFill(Color.DARKVIOLET);
        gridPane.add(password, 0,3);
        PasswordField pass = new PasswordField();
        gridPane.add(pass, 1,3);
        // buttons
        Button login_button = new Button("login");
        login_button.setTextFill(Color.GREEN);
        login_button.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        login_button.setPrefSize(90, 10);
        Button register_button = new Button("register");
        register_button.setPrefSize(90, 10);
        register_button.setTextFill(Color.RED);
        register_button.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        HBox temp = new HBox(10);
        temp.setAlignment(Pos.CENTER);
        temp.getChildren().addAll(login_button, register_button);
        gridPane.add(temp, 1, 4);
        // the radio button
        final ToggleGroup group = new ToggleGroup();        
        RadioButton radio_student = new RadioButton("Student");
        radio_student.setToggleGroup(group);
        radio_student.setSelected(true);
        RadioButton radio_organiser = new RadioButton("Organiser");
        radio_organiser.setToggleGroup(group);
        HBox temp2 = new HBox(10);
        temp2.getChildren().addAll(radio_student, radio_organiser);
        gridPane.add(temp2, 1,5);
        
        Label welcome = new Label("Welcome to your Excursion Manager");
        welcome.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
        welcome.setTextFill(Color.BLUE);
        welcome.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(welcome, Pos.CENTER);
        root.setTop(welcome);
        // set the border pane
        root.setCenter(gridPane);
        
        // hyperlink for forgotten password
        Hyperlink renewPassword = new Hyperlink("forgot password?");
        // add to the grid pane
        gridPane.add(renewPassword, 1, 7);
        
        // event handling: forgotten password: click on hyperlink
        renewPassword.setOnAction(e->{
        	// branch to the page where you can renew your password: load a new scene
        	mainClass.getMainStage().setScene(renewPass.renewPasswordScene());
        	
        });
        
        // first, handle radio button events
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() == radio_student) {
					choice = true;
				}
				else if(group.getSelectedToggle() == radio_organiser) {
					choice = false;
				}                
                }
        });
        
        // event handling: when the user presses the login button
        
        login_button.setOnAction(e->{
        	// extract the user name and password fields
        	String userInput = name.getText();
        	String userPass = pass.getText();        	
        	// send them to the respective controller
        	boolean decision = false;
        	if(userInput.length()>0 && userPass.length()>0) {
        	    decision = true;//userAgent.login(userInput, userPass, choice);
        	}
        	// preocess their returned value
        	if(decision) {
        		// confirm successful login
				alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText("Login Confirmation");
				alert.setContentText("Your login is successful");
				alert.showAndWait();
				// branch to the welcome page
				// the welcome screen displays  excursion titles
				mainClass.getMainStage().setScene(homepage.homeScene(excursionAgent.viewALLExcursion(), excursionAgent.getExcursionIDs()));	
        	}
        	else {
				alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Login Failure");
				alert.setContentText("Your login was not successful");
				alert.showAndWait();
        	}
        });
        
        // event handling: when the user presses the register button
        register_button.setOnAction(e->{
        	// branch to the registration portal: change the scene of the main Stage
        	mainClass.getMainStage().setScene(registrationPage.registrationScene());
        });
        
        // return the scene
        return new Scene(root, 700, 700);
    }
}
