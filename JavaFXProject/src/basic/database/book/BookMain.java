package basic.database.book;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BookMain extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("BookList.fxml"));
		BorderPane booklist = loader.load();
		
		Scene sc = new Scene(booklist);
		primaryStage.setScene(sc);
		primaryStage.show();
		primaryStage.setTitle("도서관리프로그램");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
