package basic.database.book;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
	private SimpleIntegerProperty bid;
	private SimpleStringProperty bname;
	private SimpleStringProperty writer;
	private SimpleStringProperty publisher;
	private SimpleIntegerProperty price;
// 중복생성자	
	public Book() {	}
	public Book(String bname, String writer, String publisher,
			int price) {
		this.bname = new SimpleStringProperty(bname);
		this.writer = new SimpleStringProperty(writer);
		this.publisher = new SimpleStringProperty(publisher);
		this.price = new SimpleIntegerProperty(price);
	}
	public Book(int bid, String bname, String writer, String publisher,
			int price) {
		this.bid = new SimpleIntegerProperty(bid);
		this.bname = new SimpleStringProperty(bname);
		this.writer = new SimpleStringProperty(writer);
		this.publisher = new SimpleStringProperty(publisher);
		this.price = new SimpleIntegerProperty(price);
	}


// 겟셋	
	public int getBid() {
		return this.bid.get();
	}
	public void setBid(int bid) {
		this.bid.set(bid);
	}	
	public String getBname() {
		return this.bname.get();
	}
	public void setBname(String bname) {
		this.bname.set(bname);
	}
	public String getWriter() {
		return this.writer.get();
	}
	public void setWriter(String writer) {
		this.writer.set(writer);
	}
	public String getPublisher() {
		return this.publisher.get();
	}
	public void setPublisher(String publisher) {
		this.publisher.set(publisher);
	}
	public int getPrice() {
		return this.price.get();
	}
	public void setPrice(int price) {
		this.price.set(price);
	}
	
}
