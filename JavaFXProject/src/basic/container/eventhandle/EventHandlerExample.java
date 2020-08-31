package basic.container.eventhandle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class EventHandlerExample extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root
		= FXMLLoader.load(getClass().getResource("Root.fxml"));
		// fxml파일을 읽어오는 fxml 로더
		// 현재클래스 기준으로 리소스를 불러오는..?? 그래서 겟클래스 겟리소스..?

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();	
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
