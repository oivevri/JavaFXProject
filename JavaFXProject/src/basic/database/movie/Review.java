package basic.database.movie;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Review {
	private SimpleIntegerProperty id;
	private SimpleStringProperty img;
	private SimpleStringProperty name;
	private SimpleStringProperty day;
	private SimpleIntegerProperty rank;
	private SimpleStringProperty reviewcomment;
	
// 이중생성자
	public Review() {
		id = new SimpleIntegerProperty();
		img = new SimpleStringProperty();
		name = new SimpleStringProperty();
		day = new SimpleStringProperty();
		rank  = new SimpleIntegerProperty();
		reviewcomment = new SimpleStringProperty();
	}
	public Review(String name, String day, int rank, String reviewcomment) {
		this.name = new SimpleStringProperty(name);
		this.day = new SimpleStringProperty(day);
		this.rank = new SimpleIntegerProperty(rank);
		this.reviewcomment = new SimpleStringProperty(reviewcomment);
	}
	public Review(String img, String name, String day, int rank, String reviewcomment) {
		id = new SimpleIntegerProperty();
		this.img = new SimpleStringProperty(img);
		this.name = new SimpleStringProperty(name);
		this.day = new SimpleStringProperty(day);
		this.rank = new SimpleIntegerProperty(rank);
		this.reviewcomment = new SimpleStringProperty(reviewcomment);
	}
	public Review(int id, String img, String name, String day, int rank, String reviewcomment) {
		this.id = new SimpleIntegerProperty(id);
		this.img = new SimpleStringProperty(img);
		this.name = new SimpleStringProperty(name);
		this.day = new SimpleStringProperty(day);
		this.rank = new SimpleIntegerProperty(rank);
		this.reviewcomment = new SimpleStringProperty(reviewcomment);
	}
// 겟셋	
	public int getId() {
		return this.id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}
	public String getImg() {
		return this.img.get();
	}

	public void setImg(String img) {
		this.img.set(img);
	}
	
	public String getName() {
		return this.name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}
	
	public String getDay() {
		return this.day.get();
	}

	public void setDay(String day) {
		this.day.set(day);
	}
	
	public int getRank() {
		return this.rank.get();
	}

	public void setRank(int rank) {
		this.rank.set(rank);
	}
	
	public String getReviewcomment() {
		return this.reviewcomment.get();
	}

	public void setReviewcomment(String reviewcomment) {
		this.reviewcomment.set(reviewcomment);
	}

}
