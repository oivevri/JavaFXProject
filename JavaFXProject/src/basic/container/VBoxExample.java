package basic.container;

// p875
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class VBoxExample extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox(); // 새 컨테이너 VBox
		root.setPadding(new Insets(10, 10, 10, 10));

		ImageView iv = new ImageView(); // vbox에 들어갈 새 인스턴스
		iv.setFitWidth(200);
		iv.setPreserveRatio(true);
		iv.setImage(new Image("/basic/images/fruit1.jpg"));
		// url은 프로젝트 폴더 밑의 bin 폴더를 기준으로 함

		HBox hbox = new HBox(); // 새 컨테이너 HBox
		hbox.setAlignment(Pos.CENTER);
		// Pos. : 열거형 타입
		hbox.setSpacing(20);

		// btnPrev, btnNext 는 컨트롤이다
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

// 이벤트 핸들러를 해당 컨트롤에 등록
//		이벤트핸들러를 구현하는 익명의 구현개체를 만들자
//		EventHandler라는 펑셔널 인터페이스functional Interface고 <ActionEvent>제네릭타입??
		// on Action : 액션이 발생했을때 value 이벤트핸들러라는 인터페이스로 등록하겠다

// "다음"버튼을 누르면 다음사진이 뜨도록 해보자		
		btnNext.setOnAction(new EventHandler<ActionEvent>() {
			int nx = 1;

			@Override
			public void handle(ActionEvent ae) {
				if (nx == 9)
					nx = 1;
				iv.setImage(new Image("/basic/images/fruit" + nx++ + ".jpg"));
			}
		});

// "이전"버튼을 누르면 이전사진이 뜨도록 해보자
		btnPrev.setOnAction(new EventHandler<ActionEvent>() {
			int pr = 8; // 이걸 헷갈리면........
			@Override
			public void handle(ActionEvent event) {
				if (pr == 1)
					pr = 8;
				iv.setImage(new Image("/basic/images/fruit" + pr-- + ".jpg"));
			}
			
		});
		
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
