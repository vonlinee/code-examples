package com.panemu.tiwulfx.core;

import com.dlsc.formsfx.model.structure.Element;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.panemu.tiwulfx.control.anno.TableViewColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 扩展TableView
 * @param <S>
 * @see TableView
 */
public class DataTableView<S> extends TableView<S> {

    public DataTableView() {
        setEditable(false);
        setRowFactory(param -> {
            TableRow<S> row = new TableRow<>();
            row.setOnMouseClicked(event -> {

            });
            return row;
        });
    }

    public FormRenderer initForm(Class<S> rowClass) {
        List<Element<?>> elements = new ArrayList<>();
        final Field[] declaredFields = rowClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            final TableViewColumn tvc = declaredField.getAnnotation(TableViewColumn.class);
            if (tvc == null) {
                continue;
            }
        }
        return new FormRenderer(Form.of(Group.of(elements.toArray(new Element[0]))));
    }
}
