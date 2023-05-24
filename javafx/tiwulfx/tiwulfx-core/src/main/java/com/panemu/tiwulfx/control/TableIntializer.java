package com.panemu.tiwulfx.control;

import com.panemu.tiwulfx.control.anno.TableViewColumn;
import com.panemu.tiwulfx.table.*;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TableIntializer<R> {

    /**
     * 初始化表格
     * @param recordClass 数据模型
     * @param tableView   表格
     */
    public void initTableView(Class<R> recordClass, TableView<R> tableView) {
        final Field[] declaredFields = recordClass.getDeclaredFields();
        final List<BaseColumn<R, ?>> columnsToBeAdd = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            final TableViewColumn tvc = declaredField.getAnnotation(TableViewColumn.class);
            if (tvc == null) {
                continue;
            }
            final Class<?> type = declaredField.getType();
            final String propertyName = declaredField.getName();
            // 根据数据类型推断选择使用什么列
            BaseColumn<R, ?> column;
            if (Number.class.isAssignableFrom(type)) {
                column = new NumberColumn<>(propertyName, type);
            } else if (type == String.class) {
                column = new TextColumn<>(propertyName);
            } else if (type == LocalDate.class) {
                column = new LocalDateColumn<>(propertyName);
            } else if (type == Date.class) {
                column = new DateColumn<>(propertyName);
            } else {
                column = new ObjectColumn<>(propertyName);
            }
            column.setText(tvc.name());
            columnsToBeAdd.add(column);
        }
        tableView.getColumns().addAll(columnsToBeAdd);
    }
}
