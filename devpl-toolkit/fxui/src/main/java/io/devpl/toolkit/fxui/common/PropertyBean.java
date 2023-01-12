package io.devpl.toolkit.fxui.common;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Map;

public class PropertyBean {

    final Map<String, Object> rawMap = new HashMap<>();
    final ObservableMap<String, Object> observableMap = FXCollections.observableMap(rawMap);
    // 用于限制字段类型
    final Map<String, Class<?>> fieldTypes = new HashMap<>();
    final Map<String, Property<?>> bindedProperties = new HashMap<>();

    public PropertyBean() {
        observableMap.addListener((MapChangeListener<String, Object>) change -> {
            @SuppressWarnings("unchecked") Property<Object> property = (Property<Object>) bindedProperties.get(change.getKey());
            if (property != null) {
                property.setValue(change.getValueAdded());
            }
        });
    }

    public final <T> void bind(String filed, Property<T> property) {
        bindedProperties.put(filed, property);
    }

    public final <T> void bindBidirectional(String filed, Property<T> other) {
        bindedProperties.put(filed, other);
        other.addListener((observable, oldValue, newValue) -> {
            rawMap.put(filed, newValue); // 防止递归回调
        });
    }

    public void put(String key, Object value) {
        if (value != null) {
            fieldTypes.put(key, value.getClass());
        }
        if (bindedProperties.isEmpty()) {
            rawMap.put(key, value);
        } else {
            observableMap.put(key, value);
        }
    }

    public Object get(String key) {
        return rawMap.get(key);
    }

    public static void main(String[] args) {
        final PropertyBean bean = new PropertyBean();
        final SimpleStringProperty property = new SimpleStringProperty("222");
        bean.bindBidirectional("name", property);
        bean.put("name", "ls");
        System.out.println(property.get());
        property.set("9999");

        System.out.println(bean.get("name"));
    }
}
