package basic.control;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InputController implements Initializable {
	// InputController를 구현하기위해 implements Initializable 쓰는거??
	
	// FXML에서 fx:id로 선언된 요소들을 불러온다 -> 그다음 임포트
	@FXML private TextField txtTitle;
	@FXML private PasswordField txtPassword;
	@FXML private ComboBox<String> comboPublic;
// 콤보박스 : 제네릭타입. 여기서 String해줘서 fxml에서 <String fx:value="공개"/>라고 쓰는거
	@FXML private DatePicker dateExit;
	@FXML private TextArea txtContent;
	@FXML private Button btnReg;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		txtTitle.setText("안녕하세요");
//		comboPublic.setValue("public"); // 컨트롤에 값 넣고싶으면 set, 갖고오고싶으면 get
		
		
// 등록,취소 버튼		
//		btnReg.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				handleBtnRegAction(); 
//			}
//		});
		//람다식으로
		btnReg.setOnAction((ae) -> handleBtnRegAction());
	}
	
	// 버튼 눌렀을때 null이거나 빈 값이 있으면 넣어주세요 라는 의미로?? 만들어보자
	public void handleBtnRegAction() {
		if(txtTitle.getText() == null || txtTitle.getText().equals("")) {
			showPopup("타이틀을 입력하세요!"); // 타이틀이 비었을때
		} else if (txtPassword.getText() == null || txtPassword.getText().equals("")) {
			showPopup("비밀번호를 입력하세요!"); // 비번이 비었을때
		} else if (comboPublic.getValue() == null || comboPublic.getValue().equals("")) {
			showPopup("공개여부를 지정하세요!");
		} else if (dateExit.getValue() == null) {
			showCustomDialog("날짜를 입력하세요");
		} else { //입력코드
			insertData();
		}
	}
	// 데이터베이스 연결
	public void insertData() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "hr", passwd = "hr";
		Connection conn = null; //java.sql의 connection
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, passwd);
		} catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		// 5가지 파라미터 기입한걸 테이블에 넣는 sql문을 작성
		String sql = "insert into new_board values(?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, txtTitle.getText()); // 첫번째 파라미터 입력한 값을 넣겠다
			pstmt.setString(2, txtPassword.getText());
			pstmt.setString(3, comboPublic.getValue());
			pstmt.setString(4, dateExit.getValue().toString());
			pstmt.setString(5, txtContent.getText());
			
			int r = pstmt.executeUpdate();
			System.out.println(r + "건 입력됨.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
// 반복되는 부분을 이용하기 위해 팝업을 메소드로 따로 빼자
	public void showPopup(String msg) {
// popup 타이틀 등록 : 팝업 -> 얘도 컨트롤 있어야함? -> 컨테이너 HBox 만들어서 팝업에 넣어주자!
		HBox hbox = new HBox(); // 컨테이너 만드는 법
		// 스타일 : 백그라운드, 폰트, 간격(radius) 등을 설정하자
		hbox.setStyle("-fx-background-color: black; -fx-background-radius: 20;");
		hbox.setAlignment(Pos.CENTER); // 가운데정렬
					
		// 컨트롤인 라벨, 이미지뷰를 만들어서 hbox에 넣어주자!
		ImageView iv = new ImageView();
		iv.setImage(new Image("basic/images/dialog-info.png"));
					
		Label label = new Label();
		label.setText(msg); // <- 이부분이 반복되니까 따로 해주는거임
		label.setStyle("-fx-text-fill: yellow; ");
					
		hbox.getChildren().addAll(iv, label); // hbox에 넣어준다
			
		Popup pop = new Popup();
		pop.getContent().add(hbox); // content : add를 써서 컨텐츠 hbox를 팝업에 넣어준다
		pop.setAutoHide(true);
		pop.show(btnReg.getScene().getWindow()); // 지금은 primaryStage가 기본인데 불러올수없다??
		// 그래서 btnReg를 이용해서 걔한테 등록된 것의 씬과, 씬이 포함된 윈도우를 데려온다
	}
	// content나 날짜가 null 이면 팝업 뜨는거도 만들어주자
	public void showCustomDialog(String msg) {
		Stage stage = new Stage(StageStyle.UTILITY); // 매개값이 primaryStage에서 넘어간거?? 몰라..무슨말이지..
		// 자바fx에서 스테이지는 윈도우 역할을 한다
		// 스테이지 중에서도 유틸리티는 ..뭐라고?
		stage.initModality(Modality.WINDOW_MODAL); //이건또뭔데.. 모달윈도우...
		stage.initOwner(btnReg.getScene().getWindow());
		// 어느 윈도우에 공급시킬건지?? -> 버튼이 저장된 윈도우를 불러온거?
		// 모달윈도우를 수행하고있는 윈도우로 지정해줬다고??
		
//	컨테이너와 컨트롤 만들어보자
		AnchorPane ap = new AnchorPane(); // 앵커페인 컨테이너 만듦
		ap.setPrefSize(400, 150); // 너비와 높이 한번에 설정하기 -> prefSize
		
		// 이미지뷰 만들자
		ImageView iv = new ImageView();
		iv.setImage(new Image("/basic/images/dialog-info.png"));
		// 사이즈조절
		iv.setFitWidth(50);
		iv.setFitHeight(50);
		iv.setLayoutX(15);
		iv.setLayoutY(15);
		iv.setPreserveRatio(true);
		
		// 버튼 만들자
		Button btnOk = new Button("확인");
		btnOk.setLayoutX(336);
		btnOk.setLayoutY(104);
		btnOk.setOnAction((e) -> stage.close()); // 확인버튼 누르면 스테이지 닫는 액션이벤트를 추가해주자
		
		// 라벨만들자
		Label label = new Label(msg); // msg받아오는 라벨
		label.setLayoutX(87);
		label.setLayoutY(33);
		label.setPrefSize(290, 15);
		
		//앵커페인에 컨트롤들을 넣어주자
		ap.getChildren().addAll(iv, btnOk, label);
		
		Scene scene = new Scene(ap);
		stage.setScene(scene); // 위에서 만들어둔 스테이지 stage에 씬 scene을 등록해주자
		stage.show(); // 쇼를 만들자 <- 메인메소드에서 해준거랑 같은건데 딴내용
		
	}
	
	
}
