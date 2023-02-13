package beans;

import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class TestBinding {

	
	public static void main(String[] args) {
		
		StringProperty nameProperty = new SimpleStringProperty();
		
		nameProperty.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("listener1 => " + oldValue + " " + newValue);
			}
		});
		
		nameProperty.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("listener2 => " + oldValue + " " + newValue);
			}
		});
		
		Object bean = nameProperty.getBean();
		
		System.out.println(bean);
		
		nameProperty.set("zs");


	}
}
