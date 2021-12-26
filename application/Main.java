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
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	@Override
	public void start(Stage stage) throws IOException {
		stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../Resources/Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Casinos 4435");
        stage.setScene(scene);
        stage.show();
        Controller cont =fxmlLoader.getController();
        
        
        try {
        	  
        	  socket = new Socket("localhost", 1234);
			  bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        	  bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	  


        	  new Thread(() -> {
        	    try{
        	        while(socket.isConnected() && !socket.isClosed()){
        	            String messageFromServer= bufferedReader.readLine();

        	            System.out.println(messageFromServer);

        	            Platform.runLater( () -> {
        	            	if(messageFromServer.contains("Available Spots")) {
        	            		cont.setJoinButton(messageFromServer);
        	            	}
        	            	if(messageFromServer.contains("GameState")) {
        	            		cont.absorbInfo(messageFromServer);
        	            	}

        	            });


        	        }
        	    }
        	    catch(IOException e){
        	    	e.printStackTrace();
        	    }

        	  }).start();
        	  
        	  
        	
        	  new Thread(() -> {
        	    while(socket.isConnected() && !socket.isClosed()){
        	    	Platform.runLater( () -> {
        	    		boolean ReadyFlag=cont.isReady();
        	    		String message=cont.getMessage();
        	    		
        	    		
        	    		if(ReadyFlag) {
        	    			try {
        	    			System.out.println("I just sent this message"+  message);
	        	    		bufferedWriter.write(message);
	          				bufferedWriter.newLine();
	          				bufferedWriter.flush();
	          				cont.messageSent();
        	    			}catch(IOException e) {
        	    				e.printStackTrace();
        	    			}
        	    		}

    	            });	
  				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
  			}

        	  }).start();
        	  
        	  
        
	}catch (IOException e) {
		e.printStackTrace();
	}
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                try {
                	try {
                	if(bufferedWriter!=null) {
                	bufferedWriter.write("Shutdown");
      				bufferedWriter.newLine();
      				bufferedWriter.flush();}}
                	catch(IOException e) {
                		e.printStackTrace();
                	}
                	if(socket!=null)
        				socket.close();
                	if(bufferedReader!=null)
        				bufferedReader.close();
        			if(bufferedWriter!=null)
        				bufferedWriter.close();	
        			
                	
               
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                stage.close();
                Platform.exit();
            }
        });        
          
 

        
        
        
        
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
