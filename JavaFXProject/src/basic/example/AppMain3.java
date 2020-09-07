package basic.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AppMain3 extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Root3.fxml"));
		BorderPane root = loader.load();
		
		RootController3 controller = loader.getController();
		controller.setPrStage(primaryStage);
		
		
		Scene sc = new Scene(root);
		primaryStage.setScene(sc);
		primaryStage.show();
		
		primaryStage.setResizable(false); // 윈도우크기변경불가
	}
	public static void main(String[] args) {
		launch(args);
	}
}
