package basic.container;
// p875
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VBoxExample extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox(); // 새 컨테이너 VBox
		root.setPadding(new Insets(10, 10, 10, 10));
		
		ImageView iv = new ImageView(); // vbox에 들어갈 새 인스턴스
		iv.setFitWidth(200);
		iv.setPreserveRatio(true);
		iv.setImage(new Image("/basic/images/Earth.png"));
		// url은 프로젝트 폴더 밑의 bin 폴더를 기준으로 함
		
		HBox hbox = new HBox(); // 새 컨테이너 HBox
		hbox.setAlignment(Pos.CENTER);
		// Pos. : 열거형 타입
		hbox.setSpacing(20);
		
		Button btnPrev = new Button(); // hbox에 들어갈 새 인스턴스(컨트롤)
		btnPrev.setText("이전");
		Button btnNext = new Button("다음"); // 이번엔 생성자에 바로넣어보자
		
		HBox.setHgrow(btnNext, Priority.ALWAYS);
		// hbox의 남은 공간을 btnNext로 채우겠다...??
		btnNext.setMaxWidth(Double.MAX_VALUE);
		// btnNext 의 폭을 창 크기의 최대치로 자동확장하겠다

		// 컨테이너에 컨트롤들 넣어주기
		hbox.getChildren().add(btnPrev);
		hbox.getChildren().add(btnNext);
		
		VBox.setMargin(hbox, new Insets(10));
		
		
		root.getChildren().add(iv); // 컨트롤을 담고있는 컨테이너
		root.getChildren().add(hbox);
		
		Scene scene = new Scene(root); // 컨테이너를 담고있는 씬
		primaryStage.setScene(scene); // 씬을 담고있는게 스테이지
		primaryStage.show();
		primaryStage.setTitle("VBox 예제");
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
