package basic.control.chart;
//UI : Chart.fxml
//Control : ChartController.java

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ChartExample extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		HBox hbox
		= FXMLLoader.load(getClass().getResource("Chart.fxml"));
		
	Scene sc = new Scene(hbox);
	primaryStage.setScene(sc);
	primaryStage.show();
	}
	public static void main(String[] args) {
		Application.launch(args);
	}

}
