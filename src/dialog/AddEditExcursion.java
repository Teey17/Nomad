package dialog;

import dialogAgent.ExcursionAgent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * A class which enables a user to create or edit excursions
 */
public class AddEditExcursion {
    private static ExcursionAgent excursionAgent = new ExcursionAgent();
    private static MainClass mainClass = new MainClass();
    private static HomePage homepage = new HomePage();
    private static DescriptionPage descriptionPage = new DescriptionPage();
    private String excursionDescription;
    private String excursionName;
    
    /**
     * A method which provides a graphical user interface for creating and editing excursions
     * @return Scene 
     * @param excursionTitle
     * @param description
     * @param type
     * @param id
     * @return Scene
     */
	public Scene addEditScene(String excursionTitle, String description, OPERATION operation, int id) {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(0,0,10,0));
		root.setStyle("-fx-background-image: url(images/paris.jfif)");
		
		/** grid pane for arranging elements horizontally and vertically*/
		GridPane gridpane = new GridPane();
		gridpane.setHgap(10);
		gridpane.setVgap(10);
		gridpane.setPadding(new Insets(0,10,10,10));
		gridpane.setAlignment(Pos.CENTER);
		
		/** the title of this page */
		Text title = new Text("");		
		title.setFill(Color.WHITE);
    	title.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
    	
        /** logout label to log out the user*/
		Label logout_label = new Label("log out");
		/** back label to return to the previous page */
		Label back_label = new Label("back");
        /** create a menu bar */
        MenuBar menubar = new MenuBar();
        Menu logout_menu = new Menu("", logout_label);
        Menu back_menu = new Menu("", back_label);
        menubar.getMenus().addAll(back_menu, logout_menu);
        menubar.setStyle("-fx-background-color: transparent");
        
        /** organise menus */
        Region rightSpacer = new Region();
        rightSpacer.setStyle("-fx-background-color: transparent");
        HBox.setHgrow(rightSpacer, Priority.SOMETIMES);
        HBox spaced_menubar = new HBox(rightSpacer, menubar);
        
        /** create a vertical box for both elements*/
        VBox box = new VBox(20);
        box.getChildren().addAll(spaced_menubar, title);
        box.setAlignment(Pos.CENTER); 
        
        /** add menu bar at the top part of the border pane */
        root.setTop(box); 
        
        /** add margins to the vertical box */
        BorderPane.setMargin(box, new Insets(0,0,5,0));
        BorderPane.setAlignment(box, Pos.CENTER);
		
        /** set up the excursion name and text field */
        Label name = new Label("Excursion Name");
        name.setTextFill(Color.WHITE);
        gridpane.add(name, 0, 2);
        TextField nameText = new TextField();
        nameText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 14));
        nameText.setPrefWidth(250);
        nameText.setText(excursionTitle);
        excursionName = nameText.getText();
        gridpane.add(nameText, 1,2 );
        
        /** set up the description name and text field */
        Label desc = new Label("Excursion Description");
        desc.setTextFill(Color.WHITE);
        gridpane.add(desc, 0, 3);
        
        /** create controls for the user interface */
        TextArea descText = new TextArea();
        descText.setText(description);
        
        /** get the initial text before editing*/
        excursionDescription = descText.getText();
        descText.setPrefWidth(400);
        descText.setPrefHeight(300);
        gridpane.add(descText, 1,3, 2, 5);
		
        /** create buttons for adding or editing excursion: only one will appear at runtime at a time */
        Button add_button = new Button("Create Excursion");
        add_button.setPrefWidth(150);
        add_button.setTextFill(Color.BLACK);
        gridpane.add(add_button, 1, 9);	
        
        Button edit_button = new Button("Edit Excursion");
        edit_button.setPrefWidth(150);
        edit_button.setTextFill(Color.BLACK);
        gridpane.add(edit_button, 1, 9);
        
        /** visibility: only one button appears and depends on the operation: creation or editing of an excursion */
        if(operation == OPERATION.ADD) {
        	title.setText("EXCURSION CREATION PORTAL");
        	edit_button.setVisible(false);
        	 edit_button.managedProperty().bind(edit_button.visibleProperty());
        }
        else if(operation == OPERATION.EDIT) {
        	title.setText("EXCURSION EDITING PORTAL");
        	add_button.setVisible(false);
       	    add_button.managedProperty().bind(add_button.visibleProperty());
        }
        
        /** event handling: log out the user if they click on the logout label in this page */
        logout_label.setOnMouseClicked(MouseEvent->{
        	mainClass.logout();
        });
        
        /** event handling: attempt to create an excursion if the user presses the add button */
        add_button.setOnAction(e->{
        	boolean decision = false;
        	excursionName = nameText.getText();
        	excursionDescription = descText.getText();
        	if(excursionName.length()>0 && excursionDescription.length()>0) {
        		decision = excursionAgent.createExcursion(excursionName, excursionDescription);
        	}
        	if(decision) {
        		/** inform the user of the successful creation of an excursion */
        		mainClass.alerts(new Alert(Alert.AlertType.INFORMATION), "Excursion Creation Confirmation", "The Excursion Was Successfully added");
				nameText.setText("");
				descText.setText("");
        	}
        	else {
        		/**inform the user that the attempt at creating an excursion failed*/
        		mainClass.alerts(new Alert(Alert.AlertType.ERROR), "Excursion Creation Failure", "The Excursion Was Not Created");
        	}
        });
        
        /** event handling: attempt to edit an excursion and inform the user of outcome */
        edit_button.setOnAction(e->{
        	boolean decision = false;
        	excursionName = nameText.getText();
        	excursionDescription = descText.getText();
        	if(excursionName.length()>0 && excursionDescription.length()>0) {
        		decision = excursionAgent.editExcursion(excursionName, excursionDescription, id);
        	}
        	if(decision) {
        		/** confirm successful editing to the user */
        		mainClass.alerts(new Alert(Alert.AlertType.INFORMATION), "Excursion modification", "The Excursion Was Successfully edited");
        	}
        	else {
        		/** announce editing failure to the user*/
        		mainClass.alerts(new Alert(Alert.AlertType.ERROR),"Excursion Editing Failure","The Excursion Was Not Edited");
        	}
        }); 
        
        /** return to the previous page */
        back_label.setOnMouseClicked(e->{
        	/** load either the home page or the excursion description page depending on the operation carried out*/
        	switch(operation) {
        	case ADD:
        		mainClass.getMainStage().setScene(homepage.homeScene(excursionAgent.viewAllExcursions()));
        		break;
        	case EDIT:
        		mainClass.getMainStage().setScene(descriptionPage.descriptionScene(excursionName, excursionDescription, id+""));
        		break;
        	}
        });
        
        /** set the root node by fitting it with the designed grid pane*/
        root.setCenter(gridpane);
        BorderPane.setAlignment(gridpane, Pos.CENTER);
        /**return the scene which displays a UI for adding or editing excursions*/
		Scene scene =  new Scene(root, MainClass.STAGE_WIDTH, MainClass.STAGE_HEIGHT);
        scene.getStylesheets().add("styles/eventDescriptionStyle.css");
		return scene;
	}
}
