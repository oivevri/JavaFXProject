package basic.control;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Phone {
	StringProperty model;
	IntegerProperty price;
	
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
