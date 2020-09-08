package basic.database.movie;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ReviewController implements Initializable{
	@FXML TableView<Review> tableView; // 얘 메인이고 그 fxml에서 fx:control으로 연결해준거
	@FXML Button btnAdd;
	
	ObservableList<Review> list;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// ReviewList.fxml 의 칼럼과 순서맞춰서 가져오기
		TableColumn<Review, ?> tc = tableView.getColumns().get(0);
		tc.setCellValueFactory(new PropertyValueFactory<>("date"));
		tc = tableView.getColumns().get(1);
		tc.setCellValueFactory(new PropertyValueFactory<>("name"));
		tc = tableView.getColumns().get(2);
		tc.setCellValueFactory(new PropertyValueFactory<>("rank"));
		tc = tableView.getColumns().get(3);
		tc.setCellValueFactory(new PropertyValueFactory<>("comment"));
		
// 저장하는 리스트 -> 테이블뷰에 넣어주기
		list = FXCollections.observableArrayList();
		tableView.setItems(list);
		
// 추가버튼
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				handleBtnAddAction();
			}
		});
// 더블클릭으로 상세보기
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 2) { 
					String selectedName = tableView.getSelectionModel().getSelectedItem().getName();
		            // 더블클릭한선택한 요소의 이름을 컬렉션(테이블뷰?)에서 찾아서 한건 가져오기
		               handleDoubleClikAction(selectedName); // 
		           }
			}

			
		});
		
	}
// 상세정보 메소드(더블클릭)
	public void handleDoubleClikAction(String name) {
	   Stage stage = new Stage(StageStyle.UTILITY);
	   stage.initModality(Modality.WINDOW_MODAL);
	   stage.initOwner(btnAdd.getScene().getWindow());
	   stage.setTitle("상세정보");
	   try {
		   Parent parent = FXMLLoader.load(getClass().getResource("ReviewInfo.fxml"));
			
			Scene sc = new Scene(parent);
			stage.setScene(sc);
			stage.show();
			ImageView imageView = (ImageView) parent.lookup("#imageView");
			Label iname = (Label) parent.lookup("#iname");
			Label iDate = (Label) parent.lookup("#iDate");
			Label iRank = (Label) parent.lookup("#iRank");
			Label iComment = (Label) parent.lookup("#iComment");
			
			for(Review rv : list) {
				if(rv.getName().equals(name)) {
					
				}
				
			}
			
			
			Button btnInfoClose = (Button) parent.lookup("#btnInfoClose");
			btnInfoClose.setOnAction(e -> stage.close());
			
	   } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
// 리뷰추가
	public void handleBtnAddAction() {
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(btnAdd.getScene().getWindow());
		stage.setTitle("리뷰 추가");
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("ReviewAdd.fxml"));
			
			Scene sc = new Scene(parent);
			stage.setScene(sc);
			stage.show();
			
	// 저장버튼
			Button btnFormAdd = (Button) parent.lookup("#btnFormAdd");
			btnFormAdd.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					TextField txtDate = (TextField) parent.lookup("#txtDate");
					TextField txtName = (TextField) parent.lookup("#txtName");
					TextField txtRank = (TextField) parent.lookup("#txtRank");
					TextField txtComment = (TextField) parent.lookup("#txtComment");
					
					Review review = new Review(txtDate.getText(), txtName.getText(),
							txtRank.getText(), txtComment.getText()
					); 
					list.add(review);
					
					stage.close();
				}
			});
	// 취소버튼	
			Button btnFormClose = (Button) parent.lookup("#btnFormClose");
			btnFormClose.setOnAction(e -> stage.close());
			
	// 이미지 불러오기 버튼 -> 뭔가 오류가나는데 왜인지모르겠어
			Button btnSelect = (Button) parent.lookup("#btnSelect");
			ImageView imageView = (ImageView) parent.lookup("#imageView");
			btnSelect.setOnAction( img -> imgSel(imageView));
			
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

// 이미지 불러오기 메소드
	public void imgSel(ImageView imageView) {
		FileChooser fc = new FileChooser();
		fc.setTitle("이미지 선택");
		fc.setInitialDirectory(new File("c:/"));//기본 디렉토리 설정 
		
	//선택할 파일 종류 제한
		ExtensionFilter imgType = new ExtensionFilter("image file", "*.jpg", "*.gif","*.png");
		fc.getExtensionFilters().add(imgType); //다른 확장자는 허용하지 않음 
	
	//선택 파일명 변환
		File path =fc.showOpenDialog(null);//어디에 창을 띄울지
		//1. 파일 경로 지정
		System.out.println(path); // 선택한 경로가 출력됨
		
		try {
		//2. 스트림 지정
			FileInputStream fis = new FileInputStream(path);
			BufferedInputStream bis = new BufferedInputStream(fis);

		//3. 읽어오기
			Image img = new Image(bis);
			imageView.setImage(img);
//
//		//4. 자원반납
//			try {
//				bis.close();
//				fis.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
}
