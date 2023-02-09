package scenegraph.node.parent.control.tableview.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PropertyData {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public IntegerProperty idProperty() {
		return id;
	}
	
	public StringProperty nameProperty() {
		return name;
	}

	public PropertyData(int id, String name) {
		setId(id);
		setName(name);
	}

	@Override
	public String toString() {
		return "PropertyData [id=" + id.get() + ", name=" + name.get() + "]";
	}
}
