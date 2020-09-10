package basic.database.movie;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import basic.common.ConnectionDB;
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
import javafx.scene.control.DatePicker;
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

public class ReviewController3 implements Initializable{
	@FXML TableView<Review> tableView; // 얘 메인이고 그 fxml에서 fx:control으로 연결해준거
	@FXML Button btnAdd, btnDelete;
	
	ObservableList<Review> list;
	String sql = "";
	Connection conn = ConnectionDB.getDB();
	PreparedStatement pstmt;
	
	File path;
	String ex;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// ReviewList.fxml 의 칼럼과 순서맞춰서 가져오기
		TableColumn<Review, ?> tc = tableView.getColumns().get(0);
		tc.setCellValueFactory(new PropertyValueFactory<>("name"));
		tc = tableView.getColumns().get(1);
		tc.setCellValueFactory(new PropertyValueFactory<>("day"));
		tc = tableView.getColumns().get(2);
		tc.setCellValueFactory(new PropertyValueFactory<>("rank"));
		tc = tableView.getColumns().get(3);
		tc.setCellValueFactory(new PropertyValueFactory<>("reviewcomment"));
		
// 저장하는 리스트 -> 테이블뷰에 넣어주기
		list = FXCollections.observableArrayList();
		tableView.setItems(list);
		
// 데이터베이스 조회
		tableView.setItems(getReview());
// 추가버튼
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				handleBtnAddAction();
			}
		});
// 삭제버튼
		btnDelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int selectedId = tableView.getSelectionModel().getSelectedItem().getId();
				deleteReview(selectedId);
				tableView.setItems(getReview());
			}
			
		});
		
// 더블클릭으로 상세보기
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 2) { 
					int selectedId = tableView.getSelectionModel().getSelectedItem().getId();
		            // 더블클릭한선택한 요소의 이름을 컬렉션(테이블뷰?)에서 찾아서 한건 가져오기
		               handleDoubleClikAction(selectedId); // 
		           }
			}
	
		});
		
	}
// 상세정보 메소드(더블클릭)
	public void handleDoubleClikAction(int selectedId) {
	   Stage stage = new Stage(StageStyle.UTILITY);
	   stage.initModality(Modality.WINDOW_MODAL);
	   stage.initOwner(btnAdd.getScene().getWindow());
	   stage.setTitle("상세정보");
	   try {
		   Parent parent = FXMLLoader.load(getClass().getResource("ReviewInfo.fxml"));
			
			Scene sc = new Scene(parent);
			stage.setScene(sc);
			stage.show();
		// 라벨이.. 맞니	
			ImageView iView = (ImageView) parent.lookup("#iView");
			Label iName = (Label) parent.lookup("#iName");
			Label iDate = (Label) parent.lookup("#iDate");
			Label iRank = (Label) parent.lookup("#iRank");
			Label iComment = (Label) parent.lookup("#iComment");
		// 읽어오는거..	
			for(Review rv : list) {
				if(rv.getId() == selectedId) {
				// 이미지 불러올때는 DB에 경로로 저장했는걸 가져와서 이미지로 변환시켜줘야한다 
					FileInputStream fis = new FileInputStream(rv.getImg());
					BufferedInputStream bis = new BufferedInputStream(fis);
					Image readimg = new Image(bis);
					iView.setImage(readimg);
					ex = rv.getImg().toString();
					// 이건 나머지 정보 불러오는거
					iDate.setText(rv.getDay());
					iName.setText(rv.getName());
					iRank.setText(String.valueOf(rv.getRank()));
					iComment.setText(rv.getReviewcomment());
				}
			}
		// 수정버튼
			Button btnUpdate = (Button) parent.lookup("#btnUpdate");
			btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					handlebtnUpdateAction(selectedId);
				}
			});
		// 창닫기 버튼	
			Button btnInfoClose = (Button) parent.lookup("#btnInfoClose");
			btnInfoClose.setOnAction(e -> stage.close());
			
	   } catch (IOException e) {
			e.printStackTrace();
		}
	}
// 리뷰수정 메소드
	public void handlebtnUpdateAction(int selectedId) {
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(btnAdd.getScene().getWindow());
		stage.setTitle("리뷰 수정");
		
		try {
			Parent updateParent = FXMLLoader.load(getClass().getResource("ReviewUpdate.fxml"));
			Scene sc = new Scene(updateParent);
			stage.setScene(sc);
			stage.show();
			
			ImageView updateView = (ImageView) updateParent.lookup("#updateView");
			DatePicker newDate = (DatePicker) updateParent.lookup("#newDate");
			TextField newName = (TextField) updateParent.lookup("#newName");
			TextField newRank = (TextField) updateParent.lookup("#newRank");
			TextField newComment = (TextField) updateParent.lookup("#newComment");
	
	// 이미지 불러오기 버튼 
			Button updateSelect = (Button) updateParent.lookup("#updateSelect");
			updateSelect.setOnAction( img -> imgSel(updateView));
			
	// 수정 버튼 누르면 저장되기		
			Button UpdateSave = (Button) updateParent.lookup("#UpdateSave");
			UpdateSave.setOnAction(new EventHandler<ActionEvent>() {
			// 순서대로 값 들어가게 하기
				@Override
				public void handle(ActionEvent event) {
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getId() == selectedId) {
							Review review = new Review();

							System.out.println(ex);
							if (path != null) {
								review = new Review(
										path.toString(), newName.getText(), newDate.getValue().toString(),
										Integer.parseInt(newRank.getText()), newComment.getText());
								review.setId(selectedId);
							} else {
									review = new Review(
											ex, newName.getText(), newDate.getValue().toString(),
											Integer.parseInt(newRank.getText()), newComment.getText()
											);
							}
							updateReview(review);
						}
					}
					tableView.setItems(getReview());
//					stage.close();
				}
			});
	// 수정 전 기존정보 불러오기
		    for(Review rev : list) {
		        if(rev.getId() == selectedId) {
		    // 이미지 불러오기 : DB에 경로로 저장했는걸 가져와서 이미지로 변환시켜줘야한다 
		        	FileInputStream fis = new FileInputStream(rev.getImg());
					BufferedInputStream bis = new BufferedInputStream(fis);
					Image readimg = new Image(bis);
					updateView.setImage(readimg);
					
		        	newName.setText(rev.getName());
		        	newDate.setValue(rev.getDay());
		        	// 이부분 어떻게해야하는지 ??
		        	// 난 그냥 ㅇㅇㅇㅇ-ㅇㅇ-ㅇㅇ 형태라서 로컬데이트 형태가 아닌데.. 셋밸류 어케해야하노 
		        	newRank.setText(String.valueOf(rev.getRank()));
					newComment.setText(rev.getReviewcomment());
				}
		    }

// 창닫기 버튼
			Button btnUpdateClose = (Button) updateParent.lookup("#btnUpdateClose");
			btnUpdateClose.setOnAction(e -> {
				stage.close();

			});
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
// 리뷰추가 메소드
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
			
	// 이미지 불러오기 버튼
			Button btnSelect = (Button) parent.lookup("#btnSelect");
			ImageView imageView = (ImageView) parent.lookup("#imageView");
			btnSelect.setOnAction( img -> imgSel(imageView));
			
	// 저장버튼
			Button btnFormAdd = (Button) parent.lookup("#btnFormAdd");
			btnFormAdd.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					TextField txtName = (TextField) parent.lookup("#txtName");
					DatePicker txtDate = (DatePicker) parent.lookup("#txtDate");
					TextField txtRank = (TextField) parent.lookup("#txtRank");
					TextField txtComment = (TextField) parent.lookup("#txtComment");
					
					Review review = new Review(
							path.toString(), txtName.getText(), txtDate.getValue().toString(),
							Integer.parseInt(txtRank.getText()), txtComment.getText()
					);
// 이미지 불러온거는 밑에서 path에 담은거고, 나머지 정보는 textField에 입력한값임. 그걸 지금 새로운 review에 담겠다는것
					insertReview(review); // 추가 저장
					tableView.setItems(getReview()); // 새로고침
					stage.close();
				}
			});
	// 취소버튼	
			Button btnFormClose = (Button) parent.lookup("#btnFormClose");
			btnFormClose.setOnAction(e -> stage.close());
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
		path = fc.showOpenDialog(null);//어디에 창을 띄울지
		//1. 파일 경로 지정
		System.out.println(path); // 선택한 경로가 출력됨
		
		try {
//			if(path != null) {
				//2. 스트림 지정
				FileInputStream fis = new FileInputStream(path);
				BufferedInputStream bis = new BufferedInputStream(fis);

				//3. 읽어오기
				Image readimg = new Image(bis);
				imageView.setImage(readimg);
//			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
// 데이터베이스 조회하는 메소드
	public ObservableList<Review> getReview() {
		sql = "select * from review order by 1";
		list = FXCollections.observableArrayList();
		
	    try { 
	        pstmt = conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        while(rs.next()) {
	           Review review = new Review(rs.getInt("id"),
	        		 rs.getString("img"),
	                 rs.getString("name"),
	                 rs.getString("day"),
	                 rs.getInt("rank"),
	                 rs.getString("reviewcomment")
	           );
	           list.add(review);
	        }
	             
	    } catch (SQLException e){
	       e.printStackTrace();
	    }
		return list;
	}
// 데이터베이스 추가하는 메소드	
	public void insertReview(Review review) {
		sql = "insert into review values(review_seq.NEXTVAL, ?, ?, ?, ?, ?)" ;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, review.getImg());
			pstmt.setString(2, review.getName());
			pstmt.setString(3, review.getDay());
			pstmt.setInt(4, review.getRank());
			pstmt.setString(5, review.getReviewcomment());
			pstmt.executeUpdate(); // 테이블이 변하게 되면 executeUpdate()사용
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
// 데이터베이스 수정 메소드
	public void updateReview(Review review) {
		String sql = "update review set img = ?, name = ?, day = ?, rank = ?, reviewcomment = ? where id = ?";
		try {
			
//			위에 저장버튼 누르는걸로 했으니까 다시 할 필요 없음
//			Parent updateParent = FXMLLoader.load(getClass().getResource("ReviewUpdate.fxml"));
//			ImageView updateView = (ImageView) updateParent.lookup("#updateView");
//			TextField newDate = (TextField) updateParent.lookup("#newDate");
//			TextField newName = (TextField) updateParent.lookup("#newName");
//			TextField newRank = (TextField) updateParent.lookup("#newRank");
//			TextField newComment = (TextField) updateParent.lookup("#newComment");
//			Review review = new Review(selectedId, path.toString(), newName.getText(),
//					newDate.getText(), Integer.parseInt(newRank.getText()), newComment.getText());

				try {
					pstmt = conn.prepareStatement(sql);
	         
					pstmt.setString(1, review.getImg());
					pstmt.setString(2, review.getName());
					pstmt.setString(3, review.getDay());
					pstmt.setInt(4, review.getRank());
					pstmt.setString(5, review.getReviewcomment());
					pstmt.setInt(6, review.getId());
					pstmt.executeUpdate(); //테이블이 변하게 되면 executeUpdate()사용

				} catch (SQLException s) {
					s.printStackTrace();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
// 데이터베이스 삭제 메소드
	public void deleteReview(int selId) {
	    String sql =  "delete from review where id = " + selId;
	       try {
	          pstmt = conn.prepareStatement(sql);
	          pstmt.executeUpdate();
	       } catch (SQLException e) {
	          e.printStackTrace();
	       }
	}
}