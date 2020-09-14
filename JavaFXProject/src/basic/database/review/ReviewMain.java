package basic.database.review;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ReviewMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ReviewList.fxml"));
		BorderPane reviewList = loader.load();
		
		Scene sc = new Scene(reviewList);
		primaryStage.setScene(sc);
		primaryStage.show();
		primaryStage.setTitle("영화 리뷰");
	}

	public static void main(String[] args) {
		launch(args);
	}

}
