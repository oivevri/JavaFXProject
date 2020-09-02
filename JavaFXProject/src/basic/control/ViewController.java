package basic.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewController implements Initializable {
	@FXML private ListView<String> listView;
	@FXML private TableView<Phone> tableView;
	@FXML private ImageView imageView;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> list = FXCollections.observableArrayList();
		// new observablelist<String> ~~ 이랑 fxcollections 어쩌고랑 줄여서 이렇게썼다는데 이해 1도안간다
		list.add("갤럭시S1");
		list.add("갤럭시S2");
		list.add("갤럭시S3");
		list.add("갤럭시S4");
		list.add("갤럭시S5");
		list.add("갤럭시S6");
		list.add("갤럭시S7");
		listView.setItems(list); // 위에쓴 observablelist 의 변수명 list가 매개값으로 들어온거		
		
		ObservableList<Phone> phoneList = FXCollections.observableArrayList();
		phoneList.add(new Phone("갤럭시S1", "phone01.png"));
		phoneList.add(new Phone("갤럭시S2", "phone02.png"));
		phoneList.add(new Phone("갤럭시S3", "phone03.png"));
		phoneList.add(new Phone("갤럭시S4", "phone04.png"));
		phoneList.add(new Phone("갤럭시S5", "phone05.png"));
		phoneList.add(new Phone("갤럭시S6", "phone06.png"));
		phoneList.add(new Phone("갤럭시S7", "phone07.png"));
// 리스트뷰에서 선택한 값이 지정되도록 하자		
		// 선택값이 바뀔때마다 인덱스가 바뀌게하는거..? 리스트선택할때마다 속성값이 바뀌게
		listView.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				tableView.getSelectionModel().select(newValue.intValue());	
			}
		});
	
		
// fxml에서 칼럼이 어느 어레이리스트랑 이어질지  지정안해줘서 에러난다고?? 칼럼 get(0),(1) 
// 리스트에 있는 값 == tableView의 칼럽과 매핑 먼저 해주고 setItems 해주자
		
		// 테이블칼럼을 String타입으로 지정해주면 이렇게.. 이게 되는건지는 모르겠다
//		TableColumn<Phone, String> tcSmartPhone
//			= (TableColumn<Phone, String>) tableView.getColumns().get(0);
//		tcSmartPhone.setCellValueFactory(new PropertyValueFactory<Phone, String>("smartPhone"));

		// 이건 제네릭타입으로....... 몰라
		TableColumn<Phone, ?> tcSmartPhone = tableView.getColumns().get(0);
		tcSmartPhone.setCellValueFactory(new PropertyValueFactory<>("smartPhone"));
		
		TableColumn<Phone, ?> tcImage = tableView.getColumns().get(1);
		tcImage.setCellValueFactory(new PropertyValueFactory<>("image")); // 이 image는 필드이름

		tcSmartPhone.setStyle("-fx-alignment: CENTER;");
		tcImage.setStyle("-fx-alignment: CENTER;");
		

		tableView.setItems(phoneList);
		tableView.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<Phone>() {
			@Override
			public void changed(ObservableValue<? extends Phone> obs, Phone oldV, Phone newV) {
// 이미지뷰 등록	: 이미지 매개값으로 이미지 인스턴스 넣어주면 됨(set으로)
				imageView.setImage(new Image("/basic/images/" + newV.getImage()));
				// phone 클래스의 getImage. 값이 바뀔때마다 newV로 새로 불러오는거
			}
		});
	}
}
