package basic.control;
//UI : View.fxml
//Controller : ViewController.java

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewExample extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane ap
		= FXMLLoader.load(getClass().getResource("Input.fxml"));
		
	Scene sc = new Scene(ap);
	primaryStage.setScene(sc);
	primaryStage.show();
	}
	public static void main(String[] args) {
		Application.launch(args);
	}
}
