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

public class ViewController1 implements Initializable {
	@FXML private ListView<String> listView;
	@FXML private TableView<Phone> tableView;
	@FXML private ImageView imageView;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listView.setItems(FXCollections.observableArrayList(
				"갤럭시S1","갤럭시S2","갤럭시S3","갤럭시S4","갤럭시S5","갤럭시S6","갤럭시S7")
		);
		listView.getSelectionModel().selectedIndexProperty().addListener(
			new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue,
					Number newValue) {
					tableView.getSelectionModel().select(newValue.intValue());
					tableView.scrollTo(newValue.intValue());
				}
			});
		
		ObservableList phoneList = FXCollections.observableArrayList(
				new Phone("갤럭시S1", "phone1.png"),
				new Phone("갤럭시S2", "phone2.png"),
				new Phone("갤럭시S3", "phone3.png"),
				new Phone("갤럭시S4", "phone4.png"),
				new Phone("갤럭시S5", "phone5.png"),
				new Phone("갤럭시S6", "phone6.png"),
				new Phone("갤럭시S7", "phone7.png")
		);
		
		TableColumn tcSmartPhone = tableView.getColumns().get(0);
		tcSmartPhone.setCellValueFactory(new PropertyValueFactory("smartPhone"));
		tcSmartPhone.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcImage = tableView.getColumns().get(1);
		tcImage.setCellValueFactory(new PropertyValueFactory("image"));
		tcImage.setStyle("-fx-alignment: CENTER;");		
		
		tableView.setItems(phoneList);
		
		tableView.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<Phone>() {
			@Override
			public void changed(ObservableValue<? extends Phone> observable, Phone oldValue, Phone newValue) {
				if(newValue != null) {
					imageView.setImage(new Image(
						getClass().getResource("/basic/images/" + newValue.getImage()).toString()
					));
				}	
			}
		});
			
	}

}
