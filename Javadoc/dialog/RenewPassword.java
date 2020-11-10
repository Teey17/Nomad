package dialog;

import dialogAgent.UserAgent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class RenewPassword {
	public static LoginPage loginPage = new LoginPage();
	public static MainClass mainClass = new MainClass();
	private static UserAgent userAgent = new UserAgent();
	private Alert alert;
	public Scene renewPasswordScene() {
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
        Text message = new Text("Please renew here");
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
        
        Label password = new Label("Old Password");
        password.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        password.setTextFill(Color.GREEN);
        gridPane.add(password, 0,3);
        PasswordField pass = new PasswordField();
        gridPane.add(pass, 1,3);
        
        Label newPassword = new Label("New Password");
        newPassword.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        newPassword.setTextFill(Color.RED);
        gridPane.add(newPassword, 0,4);
        PasswordField newPass = new PasswordField();
        gridPane.add(newPass, 1,4);
        
        Label confirmPassword = new Label("Confirm New Password");
        confirmPassword.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        confirmPassword.setTextFill(Color.ORANGE);
        gridPane.add(confirmPassword, 0,5);
        PasswordField confirmPass = new PasswordField();
        gridPane.add(confirmPass, 1,5);
        
        // buttons
        Button renew_button = new Button("renew now");
        renew_button.setPrefSize(90, 10);
        renew_button.setTextFill(Color.GREEN);
        renew_button.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
        Button back_button = new Button("back to login");
        back_button.setPrefSize(90, 10);
        back_button.setTextFill(Color.BROWN);
        back_button.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
        HBox temp = new HBox(10);
        temp.setAlignment(Pos.CENTER);
        temp.getChildren().addAll(renew_button, back_button);
        gridPane.add(temp, 1, 6);
        
        Label welcome = new Label("Welcome to your password renewal portal");
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
			// return to the login page
			// return to the login screen 
			mainClass.getMainStage().setScene(loginPage.loginScene());
		});
		// event handling: password renewal
		renew_button.setOnAction(e->{
			// make sure the newly entered password match
			// then pass it to the designated controller
			boolean decision = false;
			if(name.getText().length()>0 && pass.getText().length()>0 && newPass.getText().length()>0 && newPass.getText().equals(confirmPass.getText())) {
		      	decision = userAgent.changePassword(name.getText(), pass.getText(), newPass.getText());
			}
			// take appropriate action with respect to the decision
			if(decision) {
				alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText("Renewal Confirmation");
				alert.setContentText("Your password renewal was successful");
				alert.showAndWait();
			}
			else {
				alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error Message");
				alert.setContentText("Your password renewal failed.");
				alert.showAndWait();
			}
		});
		
		return scene;
	}
}
