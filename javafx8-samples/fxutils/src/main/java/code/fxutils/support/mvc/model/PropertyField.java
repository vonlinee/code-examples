package code.fxutils.support.mvc.model;

import javafx.beans.DefaultProperty;
import javafx.beans.property.Property;

import java.io.Serializable;

@DefaultProperty("defaultValue")
public abstract class PropertyField<T extends Property<V>, V> implements Serializable {

    private String filedName;
    private T property;             //属性类型
    private V propertyType;         //属性值类型

}
