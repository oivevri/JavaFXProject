package basic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxmlMain extends Application {

	@Override // UI로 구현하는 부분들이 들어감
	public void start(Stage primaryStage) throws Exception {
		// 불러오고싶은거를 다 "" 안에 넣으면 됨
//		보더페인
		Parent root = FXMLLoader.load(getClass().getResource("BorderPaneRoot.fxml"));
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene); // scene을 담는다
		primaryStage.show();
		primaryStage.setTitle("FXML 화면"); // 타이틀 설정
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
