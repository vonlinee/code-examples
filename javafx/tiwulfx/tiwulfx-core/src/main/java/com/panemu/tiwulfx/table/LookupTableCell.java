package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.control.LookupField;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LookupTableCell<R, C> extends BaseCell<R, C> {

	private LookupField<C> lookupField;
	private final LookupColumn<R, C> column;

	public LookupTableCell(LookupColumn<R, C> column) {
		super(column);
		this.column = column;
	}

	@Override
	protected void updateValue(C value) {
		lookupField.setValue(value);
	}

	@Override
	protected C getEditedValue() {
		return lookupField.getValue();
	}

	@Override
	protected Control getEditableControl() {
		if (lookupField == null) {
			lookupField = new LookupField<>();
			lookupField.setShowSuggestionWaitTime(column.getShowSuggestionWaitTime());
			lookupField.setValue(getItem());
			lookupField.setPropertyName(column.getLookupPropertyName());
			lookupField.setController(column.getLookupController());
			lookupField.setDisableManualInput(column.getDisableLookupManualInput());
    
			// Disable traversing focus using LEFT, RIGHT, UP and DOWN.
			lookupField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				if ((event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT
						  || event.getCode() == KeyCode.UP
						  || event.getCode() == KeyCode.DOWN)
						  && isEditing()) {
					event.consume();
				}
			});

			lookupField.valueProperty().addListener((ov, t, newValue) -> {
				for (CellEditorListener<R, C> svl : column.getCellEditorListeners()) {
					svl.valueChanged(getTableRow().getIndex(), column.getPropertyName(), getTableRow().getItem(), newValue);
				}
			});
		}
		return lookupField;
	}

	@Override
	protected void attachEnterEscapeEventHandler() {
		// Use event filter instead on onKeyPressed because Enter and Escape have
		// been consumed by lookupField itself
		lookupField.addEventFilter(KeyEvent.KEY_PRESSED, t -> {
			if (t.getCode() == KeyCode.ENTER && !t.isControlDown()) {
				commitEdit(lookupField.getValue());
			} else if (t.getCode() == KeyCode.ESCAPE) {
				lookupField.resetDisplayText();
				cancelEdit();
				// Propagate ESCAPE key press to cell
				LookupTableCell.this.fireEvent(t);
			}
		});

	}

	@Override
	public void commitEdit(C t) {
		super.commitEdit(t);
		forceUpdateRow();
	}

	/**
	 * Force update cell values. It is needed in order to update the values of
	 * cell that display particular property of currently selected lookup object
	 */
	private void forceUpdateRow() {
		((TableControlRow<R>) getTableRow()).refreshLookupSiblings(column.getPropertyName());
	}
}
