package basic.database.book;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Member {
	private SimpleIntegerProperty mid;
	private SimpleStringProperty mname;
	private SimpleStringProperty phone;
	private SimpleStringProperty email;

// 중복생성자	
	public Member(String mname, String phone, String email) {
		this.mname = new SimpleStringProperty(mname);
		this.phone = new SimpleStringProperty(phone);
		this.email = new SimpleStringProperty(email);
	};

	public Member(int mid, String mname, String phone, String email) {
		this.mid = new SimpleIntegerProperty(mid);
		this.mname = new SimpleStringProperty(mname);
		this.phone = new SimpleStringProperty(phone);
		this.email = new SimpleStringProperty(email);
	};

// 겟셋
	public int getMid() {
		return this.mid.get();
	}

	public void setMid(int mid) {
		this.mid.set(mid);
	}

	public String getMname() {
		return this.mname.get();
	}

	public void setMname(String mname) {
		this.mname.set(mname);
	}

	public String getPhone() {
		return this.phone.get();
	}

	public void setPhone(String phone) {
		this.phone.set(phone);
	}

	public String getEmail() {
		return this.email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}
}
