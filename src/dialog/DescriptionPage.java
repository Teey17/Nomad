package dialog;

import java.util.Optional;

import dialogAgent.ExcursionAgent;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * A class that displays the full description of an excursion
 */
public class DescriptionPage {
	private static AddEditEvent addEvent = new AddEditEvent();
	private static AddEditExcursion addEditExcursion = new AddEditExcursion();
	private static ExcursionAgent excursionAgent = new ExcursionAgent();
	private static MainClass mainClass = new MainClass();
	private static HomePage homepage = new HomePage();

	/**
	 *  A method which returns a user interface where the user can view the description of an excursion
	 * @return Scene
	 * @param title
	 * @param description
	 * @param id
	 */
	public Scene descriptionScene(String title, String description, String id) {
		BorderPane root = new BorderPane();
		/**add a background image*/
		root.setStyle("-fx-background-image: url(images/mun1.png)");
		/** borders */
		mainClass.decorator(root);
		/** create a grid pane for organising data on the screen*/	
		GridPane pane = new GridPane();
		pane.setId("grid-pane");
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setPadding(new Insets(10,10,10,10));
		pane.setAlignment(Pos.CENTER);

		/** set up the title */
		Text the_title = new Text(title);		
		root.setTop(the_title);
		the_title.setFill(Color.WHITE);
		the_title.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
		BorderPane.setAlignment(the_title, Pos.CENTER);	

		/**set up the description of this page and place at the centre of this border pane*/
		Text the_description = new Text(description);
		the_description.setId("event");
		the_description.setFill(Color.WHITE);
		the_description.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
		root.setCenter(the_description);
		BorderPane.setMargin(the_description, new Insets(30,60,20,60));
		BorderPane.setAlignment(the_description, Pos.TOP_CENTER);

		/** create labels for managing this excursion */
		Label add_event = new Label("Add an Event");	
		add_event.setTextFill(Color.BLUE);
		Label edit_label = new Label("Edit Excursion");   
		edit_label.setTextFill(Color.BLUE);
		Label delete_label = new Label("Delete Excursion");    
		delete_label.setTextFill(Color.BLUE);
		Label back_label = new Label("back");  
		delete_label.setTextFill(Color.BLUE);
		Label logout_label = new Label("logout");
		delete_label.setTextFill(Color.BLUE);

		/** create a menu bar */
		MenuBar menubar1 = new MenuBar();
		MenuBar menubar2 = new MenuBar();
		Menu menu_add = new Menu("", add_event);
		Menu menu_edit = new Menu("", edit_label);
		Menu menu_delete = new Menu("", delete_label);
		Menu menu_back = new Menu("", back_label);
		Menu menu_logout = new Menu("", logout_label);
		menubar1.getMenus().addAll(menu_add, menu_edit, menu_delete);
		menubar2.getMenus().addAll(menu_back, menu_logout);
		menubar1.setStyle("-fx-background-color: transparent");
		menubar2.setStyle("-fx-background-color: transparent");
		

		/** organise the menus on the menu bar */
		Region rightSpacer = new Region();
		rightSpacer.setStyle("-fx-background-color: transparent");
		HBox.setHgrow(rightSpacer, Priority.SOMETIMES);
		HBox spaced_menubar = new HBox(menubar1, rightSpacer, menubar2);

		/** create a vertical box for the title and the menu bar */
		VBox box = new VBox(30);
		box.getChildren().addAll(spaced_menubar, the_title);
		box.setAlignment(Pos.CENTER); 
		/** add the menu bar and title to the top part of the border pane */
		root.setTop(box);

		/** event handling: make the user add an excursion event when they click on the add_event label */
		add_event.setOnMouseClicked(e->{
			/** add and event*/
				addEvent.addEditEvent(title, description, id, "", "", OPERATION.ADD, "", "");   

		});

		/** event handling: make a user edit excursion when they click on the edit_label */
		edit_label.setOnMouseClicked(e->{
                /**load the UI for editing excursions*/
				mainClass.getMainStage().setScene(addEditExcursion.addEditScene(title, description, OPERATION.EDIT, Integer.parseInt(id)));
		});

		/** event handling: make a user attempt to delete an excursion when they click on the delete_label */
		delete_label.setOnMouseClicked(e->{
			Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this excursion?");
			Optional<ButtonType> result = alert.showAndWait();
			String failed_deletion = "This excursion no longer exists";
			
			if (result.isPresent() && result.get() == ButtonType.OK) {
				boolean delete_decision = excursionAgent.cancelExcursion(Integer.parseInt(id));
				failed_deletion = "The excursion could not be deleted";
				if(delete_decision) {
					/** confirm the deletion of an excursion */
					mainClass.alerts(new Alert(Alert.AlertType.INFORMATION),"Excursion deletion", "This excursion was deleted");
					
					/** load the home page with the up-to-date excursions */
					mainClass.getMainStage().setScene(homepage.homeScene(excursionAgent.viewAllExcursions()));
				}
				else {
					/**inform the user that the deletion failed*/
					mainClass.alerts(new Alert(Alert.AlertType.INFORMATION),"Failed Attempt", failed_deletion);
				}
			}
		});
		
		/** event handling: make a user log out when they press on logout label */
		logout_label.setOnMouseClicked(e->{
			mainClass.logout();
		});
		
		/** event handling: make the user go back to the home page when they click on the back label*/
		back_label.setOnMouseClicked(e->{
			// load the home page scene with the up-to-date excursions
			mainClass.getMainStage().setScene(homepage.homeScene(excursionAgent.viewAllExcursions()));
		});
		
		/**return a scene which displays the description of this excursion*/
		Scene scene =  new Scene(root, MainClass.STAGE_WIDTH, MainClass.STAGE_HEIGHT);
		scene.getStylesheets().add("styles/eventDescriptionStyle.css");
		return  scene;
	}
}
