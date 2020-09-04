package basic.example;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student2 {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleIntegerProperty korean;
	private SimpleIntegerProperty math;
	private SimpleIntegerProperty english;

	// 생성자 -> 중복생성자.. id 없을때도 할수있게 
	public Student2(String name, int korean, int math, int english) {
		this.name = new SimpleStringProperty(name);
		this.korean = new SimpleIntegerProperty(korean);
		this.math = new SimpleIntegerProperty(math);
		this.english = new SimpleIntegerProperty(english);
	}
	public Student2(int id, String name, int korean, int math, int english) {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.korean = new SimpleIntegerProperty(korean);
		this.math = new SimpleIntegerProperty(math);
		this.english = new SimpleIntegerProperty(english);
	}
	public Student2() {
	}
	
	// 겟셋
	public int getId() {
		return this.id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}	
	public String getName() {
		return this.name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	public int getKorean() {
		return this.korean.get();
	}
	public void setKorean(int korean) {
		this.korean.set(korean);
	}
	public int getMath() {
		return this.math.get();
	}
	public void setMath(int math) {
		this.math.set(math);
	}
	public int getEnglish() {
		return this.english.get();
	}
	public void setEnglish(int english) {
		this.english.set(english);
	}
}