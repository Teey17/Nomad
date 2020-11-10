package dialog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import excursionManager.MultiMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HomePage {

	private static ArrayList<Integer> IdStore = new ArrayList<>();
	private static MultiMap<String, String> excursionDetails = new MultiMap<String, String>();

	
	/** 
	 * @param multiMap A multimap object with excursion details.
	 * @param iDs Excursion IDs.
	 * @return Scene A scene for homepage.
	 */
	public Scene homeScene(MultiMap<String, String> multiMap, ArrayList<Integer> iDs) {
		IdStore = iDs;
		excursionDetails = multiMap;		
		BorderPane root = new BorderPane();	
		Image image = new Image("earth.jpg");		
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);


		ArrayList<String> titles = new ArrayList<>();
		// array list of title
		for(String title: excursionDetails.keySet()) {
			titles.add(title);
		}
		int c = 0;
		int r = 4;
		GridPane grid = new GridPane();
		// show excursions on the screen
		for(int i = 0; i < titles.size(); ++i) {
			String temp = titles.get(i);
			// create a label for the excursion
			Hyperlink excursionLabel = new Hyperlink(temp);
			excursionLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
			excursionLabel.setTextFill(Color.web("blue"));
			
			VBox vbox1 = new VBox(5);
			vbox1.getChildren().addAll(imageView, excursionLabel);
			
			grid.setHgap(20);
			grid.setVgap(10);
			grid.add(vbox1, c, r, 2, 1);
			final int index = i;
			excursionLabel.setOnAction(e->{
                  System.out.println(titles.get(index));
			});

			if(c == 8) {
				c = 0;
				r += 1;
			}
			c+=2;
		}
		root.setCenter(grid);
		Scene scene = new Scene(root, 700, 700);
		return scene;
	}

	// getters

}
