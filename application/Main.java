package application;
	

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	@Override
	public void start(Stage stage) throws IOException {
		stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Resources/init.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Casinos 4435");
        stage.setScene(scene);
        stage.show();
        
        
                
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {          	
                Controller.shutdown();
                stage.close();
                Platform.exit();
            }
        });    
        
        
        
        
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	
	
	
}
