package basic.control;
//UI : BoardControl.fxml
//Control : BoardController.java
//Board.java

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class BoardExample extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane ap = FXMLLoader.load(this.getClass().getResource("BoardControl.fxml"));
// this 의 getClass? BoardExample 를 뜻함.
// BoardExample를 기준으로 리소스를 갖고오겠다는 말(상대경로). 같은경로에 있는 BoardControl.fxml을 가져오겠다는 말
		
		Scene sc = new Scene(ap);
		primaryStage.setScene(sc);
		primaryStage.show();
		
	} 
	
	public static void main(String[] args) {
		launch(args);
	}
}
