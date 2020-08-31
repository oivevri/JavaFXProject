package basic.container;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class TilePaneExample extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		TilePane tp = new TilePane(); // 새 컨테이너 생성
		tp.setPrefTileHeight(100);
		tp.setPrefTileWidth(100);
		
//		ImageView i1 = new ImageView();
//		i1.setImage(new Image("/basic/images/fruit1.jpg"));
//		ImageView i2 = new ImageView();
//		i2.setImage(new Image("/basic/images/fruit2.jpg"));
//		ImageView i3 = new ImageView();
//		i3.setImage(new Image("/basic/images/fruit3.jpg"));
//		ImageView i4 = new ImageView();
//		i4.setImage(new Image("/basic/images/fruit4.jpg"));
//		ImageView i5 = new ImageView();
//		i5.setImage(new Image("/basic/images/fruit5.jpg"));
//		
//		tp.getChildren().add(i1); // 컨테이너에 컨트롤 담기
//		tp.getChildren().add(i2);
//		tp.getChildren().add(i3);
//		tp.getChildren().add(i4);
//		tp.getChildren().add(i5);
		
//		배열 이용하기
		ImageView[] ary = new ImageView[5];
		for (int i = 0; i < 5; i++) {
			ImageView iv = new ImageView();
			iv.setImage(new Image("basic/images/fruit" + (i+1) + ".jpg"));
			// i 범위가 0~4까지니까 (i+1)해준다
			ary[i] = iv;
			tp.getChildren().add(ary[i]);
		}
		
		Scene sc = new Scene(tp); // 씬에 컨테이너 담기
		primaryStage.setScene(sc);
		primaryStage.show();
		primaryStage.setTitle("TilePane 예제");
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
