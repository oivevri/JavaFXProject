package basic.example;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import basic.common.ConnectionDB;
import basic.control.Board;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RootController2 implements Initializable {
	@FXML TableView<Student2> tableView;
	@FXML Button btnAdd, btnBarChart;
	
	ObservableList<Student2> list; // 이거는 정보를 list에 담는거
	String sql = "";
	Connection conn = ConnectionDB.getDB(); // DB연결
	PreparedStatement pstmt;
	
	Stage priStage; // 이건 AppMain이랑 상관없이 새로 설정하는거임
	public void setPriStage(Stage priStage) {
		this.priStage = priStage; //rootController 전체에서 접근가능한 필드
	}
	
	@Override // 얘가 실행되는 애.. main이랑 비슷한역할
	public void initialize(URL location, ResourceBundle resources) {
		
// 성적		
		TableColumn<Student2, ?> tc = tableView.getColumns().get(0); // 첫번째 칼럼을 가져오자
		tc.setCellValueFactory(new PropertyValueFactory<>("name"));
	
		tc = tableView.getColumns().get(1);
		tc.setCellValueFactory(new PropertyValueFactory<>("korean"));
		
		tc = tableView.getColumns().get(2);
		tc.setCellValueFactory(new PropertyValueFactory<>("math"));
		
		tc = tableView.getColumns().get(3);
		tc.setCellValueFactory(new PropertyValueFactory<>("english"));
		// 칼럼 다 갖고오고나서
		
		// 성적을 저장하는 리스트 만들어주고
//		ObservableList<Student> list = FXCollections.observableArrayList();
//		-> 얘 밑의 handleBtnAddAction 메소드에서 만든것도 쓸려고 젤 빼주고 list = ~ 만 쓰자
		list = FXCollections.observableArrayList();
		
		tableView.setItems(list); // 테이블뷰에 넣어준다
		// 지금은 아무 값 없다. add통해 넣을때 추가된다
		
// 추가버튼	
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				handleBtnAddAction(); // 메소드호출
			}
			
		});
// 차트버튼	
		btnBarChart.setOnAction(e -> handleBtnChartAction());

// 마우스 더블클릭하면 수정할수있는 이벤트발생하게
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
			
				if (event.getClickCount() == 2) { // 클릭을 2번하는 이벤트가 발생하면(즉, 더블클릭하면)
					String selectedName = tableView.getSelectionModel().getSelectedItem().getName();
					// 더블클릭한선택한 요소의 이름을 컬렉션(테이블뷰?)에서 찾아서 한건 가져오기
					handleDoubleClikAction(selectedName); // 
				}
			}
		});
// db의 데이터 조회하기		
		tableView.setItems(getStudent2()); // 밑에서 쓴 조회 메소드 여기서 호출
	} // end of initialize()
	
// 마우스 더블클릭 액션을 통해 정보 수정 (데이터업데이트)
	public void handleDoubleClikAction(String name) { // 이 칸에서 쓰는 name은 다 여기서 가져온거
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(priStage);
		
	// 여기부터 레이아웃
		AnchorPane ap = new AnchorPane(); 
		ap.setPrefSize(210, 230);
		
		Label lKorean, lMath, lEnglish;
		TextField tName, tKorean, tMath, tEnglish;
		
		// 앵커페인이니까 위치를 다 지정해줘야함
		lKorean = new Label("국어");
		lKorean.setLayoutX(35);
		lKorean.setLayoutY(73);
		
		lMath = new Label("수학");
		lMath.setLayoutX(35);
		lMath.setLayoutY(99);
		
		lEnglish = new Label("영어");
		lEnglish.setLayoutX(35);
		lEnglish.setLayoutY(132);
		
		tName = new TextField();
		tName.setPrefWidth(110);
		tName.setLayoutX(72);
		tName.setLayoutY(30);
		tName.setText(name); //  -> name이라는거 여기서 만들어서 바로밑 for문에 씀
		tName.setEditable(false); // 이름 수정못하게한다
		
		tKorean = new TextField();
		tKorean.setPrefWidth(110);
		tKorean.setLayoutX(72);
		tKorean.setLayoutY(69);
		
		tMath = new TextField();
		tMath.setPrefWidth(110);
		tMath.setLayoutX(72);
		tMath.setLayoutY(95);
		
		tEnglish = new TextField();
		tEnglish.setPrefWidth(110);
		tEnglish.setLayoutX(72);
		tEnglish.setLayoutY(128);
		
	// 수정값이 업데이트되는 버튼	
		Button btnUpdate = new Button("수정");
		btnUpdate.setLayoutX(55);
		btnUpdate.setLayoutY(184);
		// 수정버튼 클릭했을 때 액션
		btnUpdate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (int i=0; i<list.size(); i++) {
					if(list.get(i).getName().equals(name)) { // name 기준으로 이름이 같다면
						Student2 student = new Student2(name,
								Integer.parseInt(tKorean.getText()),
								Integer.parseInt(tMath.getText()),
								Integer.parseInt(tEnglish.getText())
						// 두번째 생성자??여튼 점수들 매개값이 int라서 점수가져올때 앞에 Integer.parseInt쓴거
						);
						list.set(i, student);
//						updateStudent2(student); // 기준에 해당하는 값을 student에 넣고, 여기 넣어줌
					}
				}
				stage.close();
			}
		});
		
		// 이름 기준으로 국수영 점수 화면에 입력
		for(Student2 stu : list) {
			System.out.println(stu.getName());
			System.out.println(name);
			if(stu.getName().equals(name)) { //학생 클래스에있는 이름이랑 여기서의 매개값 이름이랑 같다면
				System.out.println("sdg");
				tKorean.setText(String.valueOf(stu.getKorean()));
				tMath.setText(String.valueOf(stu.getMath()));
				tEnglish.setText(String.valueOf(stu.getEnglish()));
			}
		}
		
	// 수정을 취소하고 끄는 버튼
		Button btnCancel = new Button("취소");
		btnCancel.setLayoutX(110);
		btnCancel.setLayoutY(184);
		// 취소버튼 클릭했을 때 액션
		btnCancel.setOnAction((event) -> stage.close()); // 스테이지 끄기
		// Platform.exit()) 는 플랫폼을 나간다는것. 즉 전체 끄기
		
		
		
	// 위에서 만든 컨트롤들을 다 ap 컨테이너에 붙여준다
		ap.getChildren().addAll(btnUpdate, btnCancel, lKorean, lMath, lEnglish, tName, tKorean, tMath, tEnglish);
		Scene sc = new Scene(ap); // 씬에 컨테이너 붙여주고
		stage.setScene(sc); // 이 중괄호 안에서 만든 스테이지(!)에 씬 넣어줌
		stage.show();
	}
	
	
// 차트의 버튼 누르는거 따로 메소드 빼서 적은거
	public void handleBtnChartAction() {
		Stage stage = new Stage(StageStyle.UTILITY); //윈도우 모양스타일임
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(priStage); // 이거하려고 저~위에 priStage 만들어준거
		
		try {
			Parent chart = FXMLLoader.load(getClass().getResource("BarChart.fxml"));
			Scene scene = new Scene(chart);
			stage.setScene(scene);
			stage.show(); // 이제 차트화면 보여주기까지 됨
			
// 차트chart 가지고와서 시리즈 series 를 추가해야함. 값을 담아야함. 시리즈는 컬렉션으로 되어있음
			BarChart barChart = (BarChart) chart.lookup("#barChart");
		// 여기에 시리즈를 부여. 시리즈는 이름과 점수들
// 시리즈 만들자
//			XYChart.Series<T, U> > ObservableList<XYCart.Data<T, U>>
//			데이터의 모음들이 시리즈에 연결되고, 시리즈를 차트에 추가하겠다

		// 국어 카테고리	
			XYChart.Series<String, Integer> seK = new XYChart.Series<String, Integer>();
			seK.setName("국어"); // 국어카테고리로 학생의 변수를 담자(국어점수 담자)
			
			ObservableList<XYChart.Data<String, Integer>> koList
										= FXCollections.observableArrayList();
			
			for(int i=0; i<list.size(); i++) { // 국어점수를 컬렉션에 담자. 루틴돌면서
				koList.add(new XYChart.Data<>(list.get(i).getName(), list.get(i).getKorean()));
			}
			
			
			seK.setData(koList); // 사용자가 지금 리스트컬렉션에 담고있다. 그걸 불러와야함. 리스트를 데이터에 갖다붙이고
			barChart.getData().add(seK); // 바 차트에 국어점수 시리즈를 추가한다
			
		// 수학 카테고리	
			XYChart.Series<String, Integer> seM = new XYChart.Series<String, Integer>();
			seM.setName("수학");
			ObservableList<XYChart.Data<String, Integer>> mathList
			= FXCollections.observableArrayList();

			for(int i=0; i<list.size(); i++) { // 국어점수를 컬렉션에 담자. 루틴돌면서
				mathList.add(new XYChart.Data<>(list.get(i).getName(), list.get(i).getMath()));
			}


			seM.setData(mathList); // 사용자가 지금 리스트컬렉션에 담고있다. 그걸 불러와야함. 리스트를 데이터에 갖다붙이고
			barChart.getData().add(seM); // 바 차트에 국어점수 시리즈를 추가한다
			
		// 영어 카테고리	
			XYChart.Series<String, Integer> seE = new XYChart.Series<String, Integer>();
			seE.setName("영어");
			
			ObservableList<XYChart.Data<String, Integer>> engList
			= FXCollections.observableArrayList();

			for(int i=0; i<list.size(); i++) { 
				engList.add(new XYChart.Data<>(list.get(i).getName(), list.get(i).getEnglish()));
			}


			seE.setData(engList);
			barChart.getData().add(seE);
			
		
		} catch(IOException e) {
			e.printStackTrace();
		}
	}	
// 추가화면 보여주는 작업 -> 따로빼서 적은거
	public void handleBtnAddAction() {
		Stage stage = new Stage(StageStyle.UTILITY); //윈도우 모양스타일임
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(btnAdd.getScene().getWindow()); // 나중에 추가설명
		// btnAdd라는 컨트롤의 씬의 윈도우(= 메인윈도우)가 주인????몰라..
		
		try { // 모든것을 받게하려고 parent 클래스
			Parent parent = FXMLLoader.load(getClass().getResource("AddForm.fxml"));
	
			Scene sc = new Scene(parent);
			stage.setScene(sc);
			stage.show();
			
// 추가화면의 컨트롤 사용하기
	// 추가화면의 저장버튼 : 추가하기add 버튼		
			Button btnFormAdd = (Button) parent.lookup("#btnFormAdd");
			// lookup(#btnFormAdd) = #id 라는걸 찾아와서 버튼의 btnFormAdd에 담겠다
			btnFormAdd.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					TextField txtName = (TextField) parent.lookup("#txtName");
					TextField txtKorean = (TextField) parent.lookup("#txtKorean");
					TextField txtMath = (TextField) parent.lookup("#txtMath");
					TextField txtEnglish = (TextField) parent.lookup("#txtEnglish");
					
					Student2 student = new Student2(txtName.getText(),
							Integer.parseInt(txtKorean.getText()),
							Integer.parseInt(txtMath.getText()),
							Integer.parseInt(txtEnglish.getText())
					); 
// 밑에써준 입력 메소드를 여기서 호출해줌 (여기서 메소드 자체생성하는건 안됨)
				insertStudent2(student);
				
// 이까지만 하면 db에 추가는 되는데, 창에는 안뜸
// 윈도우창을 리프레시 해줘야함 : 그냥 테이블뷰를 다시불러와주면 됨
				tableView.setItems(getStudent2());

				stage.close(); // 추가(add) 화면닫아주기
				}
			});
			
	// 추가화면의 취소버튼 : 닫기cancel 버튼
			Button btnFormCancel = (Button) parent.lookup("#btnFormCancel");
			btnFormCancel.setOnAction(e -> { 
				TextField txtName = (TextField) parent.lookup("#txtName");
				TextField txtKorean = (TextField) parent.lookup("#txtKorean");
				TextField txtMath = (TextField) parent.lookup("#txtMath");
				TextField txtEnglish = (TextField) parent.lookup("#txtEnglish");
				
				txtName.clear();
				txtKorean.clear();
				txtMath.clear();
				txtEnglish.clear();
				
			});
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}	
//sql문 : 조회 (select)
	public ObservableList<Student2> getStudent2() {
		sql = "select * from student order by 1";
		ObservableList<Student2> list = FXCollections.observableArrayList();
			
		try { 
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			// 정보를 가져왔으니 틀에 담아야한다
			while(rs.next()) {
				Student2 student = new Student2(rs.getInt("id"),
						rs.getString("name"),
						rs.getInt("koScore"),
						rs.getInt("mathScore"),
						rs.getInt("engScore")
				);
				list.add(student);
			}
				
		} catch (SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
// 입력 sql : insert	-> 이걸 save 버튼누르면 추가되도록 하자 (메소드는 저 밑에서 호출)
	public void insertStudent2(Student2 student) {
		sql = "insert into student values(student_seq.NEXTVAL, ?, ?, ?, ?)" ;
		 
		try { 
			pstmt = conn.prepareStatement(sql);
// 여기 번호 1,2,3,4 쓴게 물음표? 숫자의 순서임
			pstmt.setString(1, student.getName());
			pstmt.setInt(2, student.getKorean());
			pstmt.setInt(3, student.getMath());
			pstmt.setInt(4, student.getEnglish());
			pstmt.executeUpdate(); //테이블이 변하게 되면 executeUpdate()사용
					
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
// 수정 sql : update	
	public void updateStudent2(Student2 student) {
		String sql = "update student set koScore = ?, mathScore = ?, engScore = ? where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, student.getKorean());
			pstmt.setInt(2, student.getMath());
			pstmt.setInt(3, student.getEnglish());
			pstmt.setInt(4, student.getId());
			pstmt.executeUpdate(); //테이블이 변하게 되면 executeUpdate()사용

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
		
}