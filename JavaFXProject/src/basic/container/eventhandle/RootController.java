package basic.container.eventhandle;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;

public class RootController implements Initializable{
//initialize 라는 인터페이스(추상메소드)를 구현
	
	@FXML Label label; //fxml에서 불러오기한거임. 
	@FXML Slider slider; //이름도 root.fxml에서 설정한 id값 그대로임
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			//슬라이더 위치값 변경할때마다 property(속성값)로 리턴
			//리스너 : property변할때마다 듣고있다가 arg0(현재 new~~)로 행동해달라는것
			//매개값으로 changeListenr을 구현하는 객체가 온다
			
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number startValue, Number endValue) {
				//밸류값이 변할때마다 일어나는 처리를 여기 넣어준다
				//ObservableValue : 인터페이스. 값이 바뀔때마다 관찰하다가 ..???엥
				//슬라이드가 움직일때마다 startValue(arg1)와 endValue(arg2) 사이를 다닌다
				label.setFont(new Font(endValue.doubleValue()));
				// 넘버타입은 하위타입인 int, double타입으로 쓸수있다
			}
			
		});

	}

}
