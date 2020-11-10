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

public class RegistrationPage {
	
	public static LoginPage loginPage = new LoginPage();
	public static MainClass mainClass = new MainClass();
	private static UserAgent userAgent = new UserAgent();
	private Alert alert;
	
	
    /** 
     * 
     * @return Scene A scene for registration page.
     */
    public Scene registrationScene() {
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
        Text message = new Text("Please register here");
        message.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        message.setFill(Color.BLACK);
        gridPane.add(message, 1, 0, 2, 1);
        
        // input fields and their labels
        Label username = new Label("Username");
        username.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        username.setTextFill(Color.DARKBLUE);
        gridPane.add(username, 0, 2);
        TextField name = new TextField();
        gridPane.add(name, 1,2 );
        
        Label password = new Label("Password");
        password.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        password.setTextFill(Color.DARKRED);
        gridPane.add(password, 0,3);
        PasswordField pass = new PasswordField();
        gridPane.add(pass, 1,3);
        
        Label confirmPassword = new Label("Confirm Password");
        confirmPassword.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        confirmPassword.setTextFill(Color.DARKGREEN);
        gridPane.add(confirmPassword, 0,4);
        PasswordField confirmPass = new PasswordField();
        gridPane.add(confirmPass, 1,4);
        
        // buttons
        Button register_button = new Button("register");
        register_button.setPrefSize(90, 10);
        register_button.setTextFill(Color.GREEN);
        register_button.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        Button back_button = new Button("back to login");
        back_button.setPrefSize(90, 10);
        back_button.setTextFill(Color.BLUE);
        back_button.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
        HBox temp = new HBox(10);
        temp.setAlignment(Pos.CENTER);
        temp.getChildren().addAll(register_button, back_button);
        gridPane.add(temp, 1, 5);
        
        Label welcome = new Label("Welcome to the student registration portal");
        welcome.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
        welcome.setTextFill(Color.BLUE);
        welcome.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(welcome, Pos.CENTER);
        root.setTop(welcome);
        
        // set the border pane
        root.setCenter(gridPane);
		Scene scene = new Scene(root, 600, 600);
		
		// event handling: back button
		back_button.setOnAction(e->{
			// return to the login screen 
			mainClass.getMainStage().setScene(loginPage.loginScene());
		});
		
		// event handling: register button
		register_button.setOnAction(e->{
			// extract data from the fields and pass it to the controller
			boolean decision = false;
			if(name.getText().length()>0 && pass.getText().length()>0 && pass.getText().equals(confirmPass.getText())) {
		      	decision = userAgent.register(name.getText(), pass.getText());
			}
			// take appropriate action with respect to the decision
			if(decision) {
				alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText("Registration Confirmation");
				alert.setContentText("Your registration was successful");
				alert.showAndWait();
			}
			else {
				alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error Message");
				alert.setContentText("Your registration failed.");
				alert.showAndWait();
			}
		});
		
		return scene;
	}

}
