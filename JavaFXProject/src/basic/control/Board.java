package basic.control;

import javafx.beans.property.SimpleStringProperty;

public class Board { // 보드 클래스
	private SimpleStringProperty title; // 대문자 SSP 치고 컨트롤스페이스
	private SimpleStringProperty password;
	private SimpleStringProperty publicity;
	private SimpleStringProperty exitDate;
	private SimpleStringProperty content;
	
// 생성자
	public Board(String title, String password, String publicity
			, String exitDate, String content) {
		this.title = new SimpleStringProperty(title);
		this.password = new SimpleStringProperty(password);
		this.publicity = new SimpleStringProperty(publicity);
		this.exitDate = new SimpleStringProperty(exitDate);
		this.content = new SimpleStringProperty(content);
	}
	
// 겟셋, 타이틀프로퍼티(이름 그냥 내가정한거) -> 5세트 만들기
	public String getTitle() {
		return this.title.get(); // 타이틀에있는 문자열을 가져옴
	}
	public void setTitle(String title) {
		this.title.set(title);
	}
	public SimpleStringProperty titleProperty() {
		return this.title; // private으로 해뒀던 속성값을 가지고올수있도록 한거
	}
	
	public String getPassword() {
		return this.password.get();
	}
	public void setPassword(String password) {
		this.password.set(password);
	}
	public SimpleStringProperty passwordProperty() {
		return this.password;
	}
	
	public String getPublicity() {
		return this.publicity.get();
	}
	public void setPublicity(String publicity) {
		this.publicity.set(publicity);
	}
	public SimpleStringProperty publicityProperty() {
		return this.publicity;
	}
	
	public String getExitDate() {
		return this.exitDate.get();
	}
	public void setExitDate(String exitDate) {
		this.exitDate.set(exitDate);
	}
	public SimpleStringProperty exitDateProperty() {
		return this.exitDate;
	}
	
	public String getContent() {
		return this.content.get();
	}
	public void setContent(String content) {
		this.content.set(content);
	}
	public SimpleStringProperty contentProperty() {
		return this.content;
	}
}
