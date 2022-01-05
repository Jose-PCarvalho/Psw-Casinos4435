package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Init{

    @FXML
    private Button BJBut;
    @FXML
    private ImageView Img;
    @FXML
    private AnchorPane Anchor;
    private Stage stage;
    private Scene scene;
    private Parent root;


    /*@Override
    public void start(Stage stage) throws Exception {
        Img.fitWidthProperty().bind(Anchor.widthProperty());
        Img.fitHeightProperty().bind(Anchor.heightProperty());

        Scene scene = new Scene(Anchor);
        stage.setScene(scene);
        stage.show();
    }*/


    public void GoToLog(ActionEvent Event) throws IOException{
    	
        changeScene(Event,"../Resources/login.fxml");
    }
    
    public void changeScene(ActionEvent event, String path) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		root= loader.load();
		stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
		scene= new Scene(root);
		stage.setScene(scene);
    }
    
    


}
