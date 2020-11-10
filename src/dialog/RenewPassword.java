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
/**
 * This class which provides a window where a user can renew his/her password
 */
public class RenewPassword {
	public static LoginPage loginPage = new LoginPage();
	public static MainClass mainClass = new MainClass();
	private static UserAgent userAgent = new UserAgent();
	/**
	 * A method which provides a view where users can renew their passwords
	 * @return Scene
	 * @param none
	 */
	public Scene renewPasswordScene() {
		BorderPane root = new BorderPane();
		// add a background image
		root.setStyle("-fx-background-image: url(images/paris.jfif)");
        root.setPadding(new Insets(10,10,10,10));
        
        // create a grid pane for arranging controls on the border pane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setAlignment(Pos.CENTER);

        // create a description text
        Text message = new Text("Renew Your Password");
        message.setFill(Color.WHITE);
        gridPane.add(message, 1, 0, 2, 1);
        
        // create input fields and their labels
        Label username = new Label("Username");
        gridPane.add(username, 0, 2);
        TextField name = new TextField();
        gridPane.add(name, 1,2 );
        
        Label password = new Label("Old Password");
        gridPane.add(password, 0,3);
        PasswordField pass = new PasswordField();
        gridPane.add(pass, 1,3);
        
        Label newPassword = new Label("New Password");
        gridPane.add(newPassword, 0,4);
        PasswordField newPass = new PasswordField();
        gridPane.add(newPass, 1,4);
        
        Label confirmPassword = new Label("Confirm New Password");
        gridPane.add(confirmPassword, 0,5);
        PasswordField confirmPass = new PasswordField();
        gridPane.add(confirmPass, 1,5);
        
        // create buttons for triggering events
        Button renew_button = new Button("renew now");
        renew_button.setPrefSize(90, 10);
      
        Button back_button = new Button("back to login");
        back_button.setPrefSize(90, 10);

        HBox temp = new HBox(10);
        temp.setAlignment(Pos.CENTER);
        temp.getChildren().addAll(renew_button, back_button);
        gridPane.add(temp, 1, 6);
        
        // create a title for the page
        Label welcome = new Label("PASSWORD RENEWAL PORTAL");
        welcome.setId("renewlabel");
        welcome.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(welcome, Pos.CENTER);
        root.setTop(welcome);
        
        // place the grid pane at the center of the border pane
        root.setCenter(gridPane);
		Scene scene = new Scene(root, MainClass.STAGE_WIDTH, MainClass.STAGE_HEIGHT);
		
		/** event handling: take the user back to the login page when they click on this button */
		back_button.setOnAction(e->{
			mainClass.getMainStage().setScene(loginPage.loginScene());
		});
		/** event handling: make an attempt to renew the user's password when they click on this button */
		renew_button.setOnAction(e->{
            String the_name = name.getText();
            String the_pass = pass.getText();
            String the_newPass = newPass.getText();
			boolean decision = false;
			if(!the_name.contains(" ")&& !the_pass.contains(" ") && !the_newPass.contains(" ") && the_name.length()>0 && 
					the_pass.length()>0 && the_newPass.length()>0 && the_newPass.equals(confirmPass.getText())) {
		      	decision = userAgent.changePassword(name.getText(), pass.getText(), newPass.getText());
			}
			// take appropriate action with respect to the decision
			if(decision) {
				// inform the user that the renewal attempt worked out well
				mainClass.alerts(new Alert(Alert.AlertType.INFORMATION), "Renewal Confirmation", "Your password renewal was successful");
			}
			else {
				// inform the user that the renewal attempt failed
				mainClass.alerts(new Alert(Alert.AlertType.ERROR), "Error Message", "Your password renewal failed");
			}
		});
		/**return a view to the user where they can modify their password*/
        scene.getStylesheets().add("styles/loginStyle.css");
        return scene;
	}
}
