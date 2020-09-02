package basic.control;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
// 폰 모델 클래스
public class Phone {
	StringProperty model;
	IntegerProperty price;
	
	// 뷰 컨트롤러용
	private SimpleStringProperty smartPhone;
	private SimpleStringProperty image;
	
	// 심플 스트링 프로펄티를 스트링타입으로 써주니까 생성자도 방법 다름
	public Phone(String smartPhone, String image) {
		this.smartPhone = new SimpleStringProperty(smartPhone);
		this.image = new SimpleStringProperty(image);
	}
	// 겟셋도 방법다름
	public String getSmartPhone() {
		return this.smartPhone.get();
	}

	public void setSmartPhone(String smartPhone) {
		this.smartPhone.set(smartPhone);
	}

	public String getImage() {
		return this.image.get();
	}
	
	public void setImage(String image) {
		this.smartPhone.set(image);
	}

	// set
	public void setModel(String model) {
		this.model.set(model);
	} // get
	public String getModel() {
		return this.model.get();
	} //property
	public StringProperty modelProperty() {
		return this.model;
	}
}
