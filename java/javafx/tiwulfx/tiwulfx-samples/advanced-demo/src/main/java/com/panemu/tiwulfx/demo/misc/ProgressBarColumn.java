/*
 * License BSD License
 * Copyright (C) 2013 Amrullah <amrullah@panemu.com>.
 */
package com.panemu.tiwulfx.demo.misc;

import com.panemu.tiwulfx.table.BaseColumn;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 *
 * @author Amrullah <amrullah@panemu.com>
 */
public class ProgressBarColumn<S, T extends Number> extends BaseColumn<S, T> {

    public ProgressBarColumn(String propertyName) {
        this(propertyName, 100);
    }

    public ProgressBarColumn(String propertyName, double prefWidth) {
        super(propertyName, prefWidth);
        Callback<TableColumn<S, T>, TableCell<S, T>> cellFactory =
                new Callback<TableColumn<S, T>, TableCell<S, T>>() {
                    @Override
                    public TableCell call(TableColumn p) {
                        return new ProgressBarCell();
                    }
                };

        setCellFactory(cellFactory);
    }
    private T max;

    public void setMax(T max) {
        this.max = max;
    }

    public class ProgressBarCell<S> extends TableCell<S, T> {

        private ProgressBar progressBar = new ProgressBar();

        public ProgressBarCell() {
            setGraphic(progressBar);
            setText(null);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            progressBar.setMaxWidth(Double.MAX_VALUE);
				setAlignment(ProgressBarColumn.this.getAlignment());
            contentDisplayProperty().addListener(new ChangeListener<ContentDisplay>() {
                private boolean suspendEvent = false;
                @Override
                public void changed(ObservableValue<? extends ContentDisplay> observable, ContentDisplay oldValue, ContentDisplay newValue) {
                    if (suspendEvent) {
                        return;
                    }
                    if (newValue != ContentDisplay.GRAPHIC_ONLY) {
                        suspendEvent = true;
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        suspendEvent = false;
                    }
                }
            });
        }

		@Override
		protected void updateItem(T item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty) {
				setGraphic(progressBar);
				if (item == null) {
					progressBar.setProgress(0);
				} else {
					progressBar.setProgress(item.doubleValue() / max.doubleValue());
				}
			} else {
				setGraphic(null);
			}
		}
	}
}
