package basic.database.movie;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Review {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleStringProperty date;
	private SimpleStringProperty rank;
	private SimpleStringProperty comment;
	
// 이중생성자
	public Review(String name, String date, String rank, String comment) {
		this.name = new SimpleStringProperty(name);
		this.date = new SimpleStringProperty(date);
		this.rank = new SimpleStringProperty(rank);
		this.comment = new SimpleStringProperty(comment);
	}
	public Review(int id, String name, String date, String rank, String comment) {
		this.name = new SimpleStringProperty(name);
		this.date = new SimpleStringProperty(date);
		this.rank = new SimpleStringProperty(rank);
		this.comment = new SimpleStringProperty(comment);
		this.id = new SimpleIntegerProperty(id);
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
	
	public String getDate() {
		return this.date.get();
	}

	public void setDate(String date) {
		this.date.set(date);
	}
	
	public String getRank() {
		return this.rank.get();
	}

	public void setRank(String rank) {
		this.rank.set(rank);
	}
	
	public String getComment() {
		return this.comment.get();
	}

	public void setComment(String comment) {
		this.comment.set(comment);
	}

}
