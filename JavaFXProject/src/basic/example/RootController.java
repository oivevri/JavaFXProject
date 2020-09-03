package basic.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RootController implements Initializable {
	@FXML TableView<Student> tableView;
	@FXML Button btnAdd, btnBarChart;
	
	ObservableList<Student> list; // 전체에서 쓸수있도록 필드영역으로 빼줌?
	
	Stage priStage; // 이건 AppMain이랑 상관없이 새로 설정하는거임
	public void setPriStage(Stage priStage) {
		this.priStage = priStage; //rootController 전체에서 접근가능한 필드
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
// 성적		
		TableColumn<Student, ?> tc = tableView.getColumns().get(0); // 첫번째 칼럼을 가져오자
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
		
	}

// 차튼버튼 누르는거 따로 메소드 빼서 적은거
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
			XYChart.Series<String, Integer> seK = new XYChart.Series<String, Integer>();
			seK.setName("국어");
			
			ObservableList koList = FXCollections.observableArrayList();
			
			for(int i=0; i<list.size(); i++) { // 국어점수를 컬렉션에 담자. 루틴돌면서
				koList.add(new XYChart.Data<>(list.get(i).getName(), list.get(i).getKorean()));
			}
			
			
			seK.setData(); // 사용자가 지금 리스트컬렉션에 담고있다. 그걸 불러와야함
			barChart.getData().add(seK);
			
			XYChart.Series<String, Integer> seM = new XYChart.Series<String, Integer>();
			seM.setName("수학");
			XYChart.Series<String, Integer> seE = new XYChart.Series<String, Integer>();
			seE.setName("영어");
			
		
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
					
					Student student = new Student(txtName.getText(),
							Integer.parseInt(txtKorean.getText()),
							Integer.parseInt(txtMath.getText()),
							Integer.parseInt(txtEnglish.getText())
					); 
					list.add(student); // 리스트에 이 student 만든거 넣어주기
					// 추가(add) 화면닫아주기
					stage.close();
				}
			});
			
	// 추가화면의 취소버튼 : 닫기cancel 버튼
			Button btnFormCancel = (Button) parent.lookup("btnFormCancel");
			btnFormCancel.setOnAction(e -> { // 람다식으로.....?
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
}
