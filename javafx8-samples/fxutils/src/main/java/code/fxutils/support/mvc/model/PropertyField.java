package code.fxutils.support.mvc.model;

import java.io.Serializable;

import javafx.beans.DefaultProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;

@DefaultProperty("defaultValue")
public class PropertyField<T extends Property<V>, V> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
    private T property;
    private Class<V> valueType;
    private V value;
    
    @SuppressWarnings("unchecked")
	public PropertyField(String fieldName, T property, Class<V> valueType, V fieldValue) {
    	this.name = fieldName;
    	this.property = property;
    	if (fieldValue != null) {
			this.valueType = (Class<V>) fieldValue.getClass();
		}
    	this.value = fieldValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
	public static void main(String[] args) {
		PropertyField<SimpleStringProperty, String> name = new PropertyField<>("name", new SimpleStringProperty(), String.class, "zs");
		
		
	}
}
