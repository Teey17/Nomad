// view all available excursions
package dialog;


import java.util.*;

import dialogAgent.ExcursionAgent;
import excursionManager.MultiMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
 * A class that yields a view which shows all excursions
 * @author NOMAD
 *
 */
public class HomePage {
	private static AddEditExcursion addExcursion = new AddEditExcursion();
	private static MainClass mainClass = new MainClass();
	private static ExcursionAgent excursionAgent = new ExcursionAgent();
	private static DescriptionPage descriptionPage = new DescriptionPage();
	private static ChatPage chatPage = new ChatPage();
	private static AllEvents events = new AllEvents();
	/** 
	 *  A method which returns a screen with all the available excursions
	 * @param data
	 * @return Scene
	 */
	public Scene homeScene(MultiMap<String, String> excursionDetails) {		
		BorderPane root = new BorderPane();	
		// add a background image
		root.setStyle("-fx-background-image: url(images/eiffel.jpg)");
		
		// borders 
		mainClass.decorator(root);

		// set up the title of the page
		Text the_title = new Text("Available Excursions");
        the_title.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		the_title.setFill(Color.WHITE);
        root.setTop(the_title);
        BorderPane.setMargin(the_title, new Insets(0,10,10,5));
        BorderPane.setAlignment(the_title, Pos.CENTER);
        
		// create labels
		Label add_label = new Label("Add Excursion");		
		Label joinChat = new Label("join chat");
		Label logout_label = new Label("log out");  
		Label events_label = new Label("All Events");
       
        // create a menu bar
		MenuBar menubar1 = new MenuBar();
		MenuBar menubar2 = new MenuBar();
		Menu menu_joinChat = new Menu("", joinChat);
		Menu menu_edit = new Menu("", add_label);
		Menu menu_delete = new Menu("", events_label);
		Menu menu_logout = new Menu("", logout_label);
		menubar1.getMenus().addAll(menu_edit, menu_delete, menu_joinChat);
		menubar2.getMenus().addAll(menu_logout);
		menubar1.setStyle("-fx-background-color: transparent");
		menubar2.setStyle("-fx-background-color: transparent");
		
		// organise menus in the menu bar
        Region rightSpacer = new Region();
        rightSpacer.setStyle("-fx-background-color: transparent");
        rightSpacer.setId("spacer");
        HBox.setHgrow(rightSpacer, Priority.SOMETIMES);
        HBox spaced_menubar = new HBox(menubar1, rightSpacer, menubar2);
            
        // add the title and the menu bar at the top of this border pane
        VBox box = new VBox(30);
        box.getChildren().addAll(spaced_menubar, the_title);
        box.setAlignment(Pos.CENTER); 
        root.setTop(box); 
        BorderPane.setMargin(box, new Insets(0,0,5,0));
        BorderPane.setAlignment(box, Pos.CENTER);
        
        /** event handling: make a user join the chat when they click on the join_chat label */
        joinChat.setOnMouseClicked(e->{
        	mainClass.getMainStage().setScene(chatPage.chatScene(USERTYPE.ORGANISER));
        });
		
        /** event handling: make a user log out when they click on the logout label */
		logout_label.setOnMouseClicked(e->{
			// load the login page
			mainClass.logout();
		});
		
		/** event handling: enable the user to creation excursions when they click on the add label */
		add_label.setOnMouseClicked(e->{
			mainClass.getMainStage().setScene(addExcursion.addEditScene("", "", OPERATION.ADD, 0));
		});
		
		/**event handling: make this user able to see most of the existing events*/
		events_label.setOnMouseClicked(e->{
			//load a view with most of the events
			mainClass.getMainStage().setScene(events.allEventsScene(excursionAgent.viewAllExcursionEvents(), USERTYPE.ORGANISER));
			
		});
		
		// store all excursion id's in a data structure
		ArrayList<String> excursionIDs = new ArrayList<>();
		for(String id: excursionDetails.keySet()) {
			excursionIDs.add(id);
		}
			
		// column and row variables for organising data in the grip pane
		int column = 0;
		int row = 4;

		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(10);
		// show excursion titles on the screen
		for(String id : excursionIDs) {
			String name = ((ArrayList<String>)excursionDetails.get(id)).get(0);
			String description = ((ArrayList<String>)excursionDetails.get(id)).get(1);	
			// create a link for each excursion
			Hyperlink excursion = new Hyperlink(name);
			excursion.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
			excursion.setStyle("-fx-underline: false");
			GridPane childGrid = new GridPane();
			childGrid.setPrefSize(200,75);
			childGrid.setAlignment(Pos.CENTER);
			childGrid.setStyle("-fx-border-width:2px;-fx-border-color:white");
			// add the excursion event to the child grid pane
			childGrid.add(excursion,0,0,4,4);
			// add child grid pane to parent grid pane
			grid.add(childGrid, column, row, 4, 1);
            /** event handling: display the description of this excursion to user that clicks on it*/
			excursion.setOnAction(e->{		            											
				mainClass.getMainStage().setScene(descriptionPage.descriptionScene(name, description, id));
			});
			
			/**make use of a tooltip object to show the description of the excursion when it gets hovered*/
			Tooltip hover= new Tooltip();
			hover.setText(description);
			excursion.setTooltip(hover);
			// update column and rows
			column+=4;
			if(column > 11) {
				column = 0;
				row += 1;
			}
		}
		// place the grid pane at the center of the border pane
		root.setCenter(grid);
		grid.setAlignment(Pos.TOP_CENTER);
		/**return a scene which shows most of the existing excursions to the user*/
		Scene scene = new Scene(root, MainClass.STAGE_WIDTH, MainClass.STAGE_HEIGHT);
		scene.getStylesheets().add("styles/eventDescriptionStyle.css");
		return scene;
	}
}
