package basic.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class ButtonController implements Initializable {
	// @FXML을 통해  fxml에서 지정한 이름대로 불러오는것
	@FXML private CheckBox chk1;
	@FXML private CheckBox chk2;
	@FXML private ImageView checkImageView;
	@FXML private ToggleGroup group;
	@FXML private ImageView radioImageView;
	@FXML private Button btnExit;
	@FXML private RadioButton rad1, rad2, rad3;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
// selectedToggleProperty : 속성값을 읽어오는 메소드 property
// ChangeListener : <토글> 값이 바뀔때마다(change) 듣고(listen) 그에따라 처리하라는거

// 오른쪽 그래픽 이미지 구현
			@Override
			public void changed(ObservableValue<? extends Toggle> observable,
					Toggle oldValue, Toggle newValue) {
				if(oldValue != null && newValue != null)
					System.out.println(oldValue.getUserData());
					System.out.println(newValue.getUserData());
				// 토글바뀔때마다 속성값이(userData) 달라지는걸 확인함
				
				radioImageView.setImage(new Image("/basic/images/" + 
						newValue.getUserData().toString() + ".png"));
					
				Image image = new Image(getClass().getResource("/basic/images/" +
					newValue.getUserData().toString() + ".png").toString());
				radioImageView.setImage(image);
			}
		});
		rad1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				System.out.println("rad1 clicked");
			}
		}); //람다식 표현
		rad2.setOnMouseClicked((a) -> System.out.println("rad2 clicked"));
		rad3.setSelected(true); // 처음 실행했을 때 3번째꺼가 선택된채로 시작하라는거
		// 원래 Button.fxml에서 barchart로 초기화되어있었는데 여기서 덮어씌운거임

		
// 버튼 Exit 구현
//		btnExit.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent arg0) {
//				Platform.exit();
//			}
//		});
// 람다식 표현
		btnExit.setOnAction((event) -> Platform.exit());
		
		
// 모자, 안경 체크박스 구현 Button.fxml에서 onAction 안쓰고하는법 -> 자바소스에서 setOnAction 쓴다
		chk1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				handleChkAction();
			}
			
		});
		// 이걸 람다식으로 쓴거
		chk2.setOnAction((arg0) -> handleChkAction());

	}
	public void handleChkAction() {
		String imgName = "";
		if(chk1.isSelected() && chk2.isSelected()) {
			imgName = "geek-glasses-hair.gif";
			
		} else if(chk1.isSelected()) {
			imgName = "geek-glasses.gif";

		
		} else if(chk2.isSelected()) {
			imgName = "geek-hair.gif";

		} else {
			imgName = "geek.gif";

		} checkImageView.setImage(new Image("/basic/images/" + imgName));
	}
}
