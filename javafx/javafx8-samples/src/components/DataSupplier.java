package components;

import javafx.collections.ObservableList;

public interface DataSupplier<T> {

    /**
     * 返回多行数据
     * @return 返回多行数据
     */
    ObservableList<T> getRows(int pageIndex, int pageSize);

    /**
     * 一行数据
     * @return 返回一行数据
     */
    T getRow();
}
