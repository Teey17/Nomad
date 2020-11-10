package dialog;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClass extends Application {
    // the main stage
    private static Stage mainStage = new Stage();
    private LoginPage loginPage = new LoginPage();
    public static void main(String args[]){
        // set up the javafx application and call the start method
        Application.launch(args);
    }

    public void start(Stage primaryStage) throws Exception{
        // the login screen is displayed
        mainStage.setTitle("Nomad: Your excursion manager");
        Scene scene = loginPage.loginScene();
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
    
    public Stage getMainStage() {
    	return mainStage;
    }
}
