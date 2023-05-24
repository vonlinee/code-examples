/*
 * License GNU LGPL
 * Copyright (C) 2012 Amrullah .
 */
package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TableCriteria.Condition;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 *
 * @author amrullah
 */
public class ObjectColumn<R, C> extends BaseColumn<R, C> {

	private TextField searchInputControl = new TextField();
	private boolean emptyStringAsNull = TiwulFXUtil.DEFAULT_EMPTY_STRING_AS_NULL;
	private int maxLength = 0;
	private boolean capitalize = false;
	
	private final SearchMenuItemBase<String> searchMenuItem = new SearchMenuItemBase<String>((BaseColumn<?, String>) this) {
		@Override
		protected Node getInputControl() {
			searchInputControl.setPromptText("kata kunci");
			return searchInputControl;
		}

		@Override
		protected List<Condition> getOperators() {
			List<Condition> lst = new ArrayList<>();
			lst.add(Condition.eq);
			lst.add(Condition.ne);
			lst.add(Condition.lt);
			lst.add(Condition.le);
			lst.add(Condition.gt);
			lst.add(Condition.ge);
			lst.add(Condition.is_null);
			lst.add(Condition.is_not_null);
			return lst;
		}

		@Override
		protected String getValue() {
			return searchInputControl.getText();
		}
	};

	public ObjectColumn() {
		this("");
	}

	public boolean isEmptyStringAsNull() {
		return emptyStringAsNull;
	}

	public void setEmptyStringAsNull(boolean emptyStringAsNull) {
		this.emptyStringAsNull = emptyStringAsNull;
	}

	public ObjectColumn(String propertyName) {
		this(propertyName, 100);
	}

	public ObjectColumn(String propertyName, double preferredWidth) {
		super(propertyName, preferredWidth);

		Callback<TableColumn<R, C>, TableCell<R, C>> cellFactory =
				new Callback<TableColumn<R, C>, TableCell<R, C>>() {
			@Override
			public TableCell call(TableColumn p) {
				return new ObjectTableCell<>(ObjectColumn.this);
			}
		};

		setCellFactory(cellFactory);
	}

	@Override
	MenuItem getSearchMenuItem() {
		if (getDefaultSearchValue() != null) {
			searchInputControl.setText(getStringConverter().toString(getDefaultSearchValue()));
		}
		return searchMenuItem;
	}

	/**
	 * Get max character that could be entered when editing a cell. If 0 then
	 * there is no limitation.
	 * @return 
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * Set maximum character that could be entered when editing a cell. Set it to
	 * 0 to disable this limitation.
	 * @param maxLength 
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * 
	 * @return true if the cell editor automatically convert entered text to capital
	 * letters
	 */
	public boolean isCapitalize() {
		return capitalize;
	}

	/**
	 * Set whether text entered to a cell editor will be automatically converted to capital
	 * letters.
	 * @param capitalize set to true to make the text always capital.
	 */
	public void setCapitalize(boolean capitalize) {
		this.capitalize = capitalize;
	}
	
//	private StringConverter<C> stringConverter = null;
}
