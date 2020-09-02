package basic.control;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BoardController implements Initializable {
	@FXML TableView<Board> boardView;
	@FXML TextField txtTitle;
	@FXML ComboBox<String> comboPublic;
	@FXML TextField txtExitDate;
	@FXML TextArea txtContent;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		TableColumn<Board, String> tcTitle = new TableColumn<>("Title");
		// <>에 테이블타입, 화면에 내보낼타입을 순서대로 써준거
		tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		tcTitle.setPrefWidth(80); // 너비 설정
		boardView.getColumns().add(tcTitle); // 하나 만들때마다 보드뷰에 추가해주기
		
		TableColumn<Board, String> tcPublicity = new TableColumn<>("공개여부");
		// <>에 테이블타입, 화면에 내보낼타입을 순서대로 써준거
		tcPublicity.setCellValueFactory(new PropertyValueFactory<>("publicity"));
		tcPublicity.setPrefWidth(80); // 너비 설정
		boardView.getColumns().add(tcPublicity);
		
		boardView.setItems(getBoardList()); // 밑에만들어준 데이터를 여기(보드뷰)에 끌고옴.
		// 근데 데이터 이용하려면 칼럼과 매치해줘야해서 이거 윗쪽에 쓴거
	
		// 보드뷰 말고 나머지 4개..
		boardView.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<Board>() {
			@Override
			public void changed(ObservableValue<? extends Board> arg0, Board o, Board n) {
				txtTitle.setText(n.getTitle());
				comboPublic.setValue(n.getPublicity());
				txtExitDate.setText(n.getExitDate());
				txtContent.setText(n.getContent());
			}
		});
		
	}
	
// DB 연결 -> 나중에 공통메소드로 빼자
	public ObservableList<Board> getBoardList() { 
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "hr", passwd = "hr";
		Connection conn = null; //java.sql의 connection
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, passwd);
		} catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		
		String sql = "select * from new_board order by 1";
//		ObservableList<Board>타입으로 컬렉션(??) 담아넣을거다 돌면서..? list에
		ObservableList<Board> list = FXCollections.observableArrayList();
		
// 예외처리		
		try { 
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){ // db에서 가져올값이 있으면 계속 루틴(?) 돌겠다
				Board board = new Board(rs.getString("title"), // 보드타입의 뭘.. 갖고온거야? 못들었음 여튼
						rs.getString("password"),
						rs.getString("publicity"),
						rs.getString("exit_date"),
// 왜 이건 exitDate가 아니라 exit_date 냐? -> 데이터베이스에 있는 값을 불러오는거라서 db에 적혀져있는 칼럼명으로 끌고와야함
						rs.getString("content"));
				list.add(board); // 만들어진 인스턴스를 리스트에 붙이자
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}
}
