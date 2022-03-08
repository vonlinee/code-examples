package io.maker.extension.poi;

import io.maker.base.lang.TypeMetaHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExcelColumn<T> extends TypeMetaHolder implements Serializable {

    private String title;
    private List<T> data = new ArrayList<>();

    public ExcelColumn(Class<T> dataType) {
        super();
        this.typeClass = dataType == null ? String.class : dataType;
    }

    public ExcelColumn(String title, Class<T> dataType) {
        super();
        this.title = title;
        this.typeClass = dataType == null ? String.class : dataType;
    }

    public boolean isEmpty() {
        return data.size() == 0;
    }

    @SuppressWarnings("unchecked")
    public <K> void add(K data) {
        if (data != null && data.getClass() == this.typeClass) {
            this.data.add((T) data);
        }
    }

    public T get(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= data.size()) {
            throw new UnsupportedOperationException("column index should >= 1, or if = 1, use getTitle() instead: " + columnIndex);
        }
        return data.get(columnIndex);
    }

    private void addIfNull() {
        throw new UnsupportedOperationException();
    }

    public int length() {
        return data.size() + 1;
    }

    public String getTitle() {
        return title;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
