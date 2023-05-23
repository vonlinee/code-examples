/*
 * Copyright (C) 2014 Panemu.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.panemu.tiwulfx.table;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;

/**
 * @author amrullah
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
class CustomTableView<R> extends TableView<R> {

    private TableColumn<R, ?> selectedColumn;

    public CustomTableView() {

        getColumns().addListener(new ListChangeListener<TableColumn<R, ?>>() {
            @Override
            public void onChanged(Change<? extends TableColumn<R, ?>> c) {
                while (c.next()) {
                    if (c.wasReplaced()) {
                        ObservableList<? extends TableColumn<R, ?>> list = c.getList();
                        System.out.println(list.get(0).getText());
                    }
                }
            }
        });
    }

    public TableColumn<R, ?> getSelectedColumn() {
        return selectedColumn;
    }

    public void setSelectedColumn(TableColumn<R, ?> selectedColumn) {
        this.selectedColumn = selectedColumn;
    }

    public final R getSelectedItem() {
        return getSelectionModel().getSelectedItem();
    }

    public final void edit(TablePosition position) {
        this.edit(position.getRow(), position.getTableColumn());
    }
}
