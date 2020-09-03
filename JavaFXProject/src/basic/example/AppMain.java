package basic.example;
//UI : Root.fxml(기본), AddForm.fxml(추가), BarChart.fxml(차트)
//Control : RootController.java
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AppMain extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		// fxml의 정적메소드를 가져올수없어서 new 로 만듦??
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Root.fxml"));
		BorderPane root = loader.load();
	
		RootController controller = loader.getController();
		// Root.fxml에 가면 컨트롤러가 지정되어있음. 그걸 가져오는게 getController?
		// basic.example 밑에있는 RootController 파일 -> 얘가갖고있는 필드?를 쓸수있음?
		controller.setPriStage(primaryStage);
		// RootController에서 만든 스테이지에 primaryStage를 집어넣는다???
		
		Scene sc = new Scene(root);
		primaryStage.setScene(sc);
		primaryStage.show();
		primaryStage.setResizable(false); // 윈도우 크기를 변경할수있게true 없게false
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
