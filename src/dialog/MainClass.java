/**
 * @version = 1.0
 * @author NOMAD
 * @ A class to start the javafx application by loading the login screen
 */
package dialog;
import java.util.Optional;

import clientNomad.ClientNomad;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * A class which provides the starting point of this javafx application. this class contains the main method 
 * which starts this application
 */
public class MainClass extends Application {

	public static final int STAGE_WIDTH = 700;
	public static final int STAGE_HEIGHT = 700;
	private static Stage mainStage = new Stage();
	private static LoginPage loginPage;
	private static ChatPage chatPage = new ChatPage();

	/**
	 * A method which sets up the javafx application and calls the start method via the launch method
	 * @return nothing
	 * @param args
	 */
	public static void main(String args[]) {
		// create an instance of the login page
		loginPage = new LoginPage();
		// set up this javafx application and call the start method
		Application.launch(args);
	}

	/**
	 * Method which serves as entry point for this javafx application: here it is used to load the login page
	 * @param primaryStage
	 * @return nothing
	 */
	@Override
	public void start(Stage primaryStage) throws Exception{
		//Connect the client to the server
		ClientNomad.getInstance().connect();     
		// set the title the main window
		mainStage.setTitle("Nomad: Your excursion manager");
		// display the login screen in the main window
		Scene scene = loginPage.loginScene();
		mainStage.setScene(scene);
		mainStage.setResizable(false);
		mainStage.show();
	}

	/**
	 * A method which disconnects the client from the server
	 */
	@Override
	public void stop() {
		chatPage.setComment(ChatPage.leave_message);
		ClientNomad.getInstance().disconnect();
	}
	/**
	 * a method which returns the main window
	 * @param nothing
	 * @return Stage
	 */
	public Stage getMainStage() {
		return mainStage;
	}
	/**
	 * A method to log the user out
	 * @param nothing
	 * @return nothing
	 */
	public void logout() {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to log out?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			getMainStage().setScene(loginPage.loginScene());
		}
	}

	/**
	 * A method for showing alert messages
	 * @param alert
	 * @param header
	 * @param message
	 */
	public void alerts(Alert alert, String header, String message) {
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	/**
	 * A method used to decorate the sides on bottom part of the pages
	 * @param root
	 */
	public void decorator(BorderPane root) {
		Label limit = new Label("NOMAD");
		limit.setVisible(false);

		VBox rightSide = new VBox(10);
		rightSide.getChildren().add(limit);	
		rightSide.setPadding(new Insets(0, 10, 0, 10));
		root.setRight(rightSide);
		
		VBox leftSide = new VBox(10);
		leftSide.getChildren().add(limit);
		leftSide.setAlignment(Pos.CENTER);
		leftSide.setPadding(new Insets(0, 10, 0, 10));
		root.setLeft(leftSide);
		
		VBox bottomSide = new VBox(10);
		bottomSide.getChildren().add(limit);
		bottomSide.setAlignment(Pos.CENTER);
		bottomSide.setPadding(new Insets(10, 0, 10, 0));
		root.setBottom(bottomSide);
	}
}
