package basic.control;
// UI : Button.fxml
// Controller : ButtonController.java

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ButtonExample extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane bt
		= FXMLLoader.load(getClass().getResource("Button.fxml"));
	
	Scene sc = new Scene(bt);
	primaryStage.setScene(sc);
	primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
