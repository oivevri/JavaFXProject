package basic;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AppMain extends Application {
// AppMain에서 start라는 추상메소드 구현해줘야함
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// arg0 부분이 스테이지 : 지금 primaryStage으로 바뀐부분임
		// 씬을 구현하는 화면이 스테이지
		
		HBox hbox = new HBox(); //컨테이너.
		hbox.setPadding(new Insets(10)); // padding : 컨테이너와 컨트롤 사이의 여백
		hbox.setSpacing(10); // 컨트롤 사이의 간격
		
		TextField tField = new TextField(); // 텍스트 = 컨트롤의 일종
		// 뭐 쓰기전에 생성자 만들어야함
		// 필드만들때? set 씀
		tField.setPrefWidth(200); // 너비. double타입임
		
		Button btn = new Button(); // 버튼 = 컨트롤의 일종
		btn.setText("확인"); // 텍스트. String타입임
		btn.setOnAction(new EventHandler<ActionEvent>() {
			// setOnAction : 버튼 눌렀을때 이벤트가 발생하는것
			// 액션이 발생하면 뒤의 것들을 handle을 진행하라고 
			// EventHandler : 인터페이스
			// ActionEvent 라는 매개값
			
			@Override 
			public void handle(ActionEvent arg0) {
				Platform.exit(); 
			}
			
		});
		
		// 인터페이스를 구현하는 객체가 와야함
		
		// hbox라는 컨테이너에 tField와 btn 라는 컨트롤을 달아주자
		hbox.getChildren().add(tField);
		hbox.getChildren().add(btn);
		// hbox가 갖고있는 자식객체가 tField와 btn이 되는거임
		
		// Scene을 생성해보자. import생성자가 없다. 그냥 만들때 컨테이너 담아서 만들어준다
		Scene scene = new Scene(hbox);
		
		// 이 스테이지에 해당하는 씬을 넣어주겠다
		primaryStage.setScene(scene);
		primaryStage.show(); // 보여주겠다
		primaryStage.setTitle("Application");
	}

	public static void main(String[] args) {
		Application.launch(args);
		// 위에서 앱 짜놓은 모든것들을 실행해주는거 
	}
}
