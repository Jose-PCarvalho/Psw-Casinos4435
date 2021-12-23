package application;
	
import java.io.IOException;

import BlackJack.dkeep.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	  
	    @Override
	    public void start(Stage stage) throws IOException, InterruptedException {
	        
	        stage.setResizable(false);
	        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../Resources/Main.fxml"));
	        Scene scene = new Scene(fxmlLoader.load());
	        Controller Cont= fxmlLoader.getController();
	        stage.setTitle("Casinos 4435");
	        stage.setScene(scene);
	        stage.show();
	        
	        
			
	        
	       
	    }
	
	public static void main(String[] args) {
		launch(args);
		
		
        
	}
}
