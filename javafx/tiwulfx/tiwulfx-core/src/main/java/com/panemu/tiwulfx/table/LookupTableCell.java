package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.control.LookupField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.jetbrains.annotations.NotNull;

public class LookupTableCell<R, C> extends CustomTableCell<R, C> {

	private LookupField<C> lookupField;
	private LookupColumn<R, C> column;

	public LookupTableCell(LookupColumn<R, C> column) {
		super(column);
		this.column = column;
	}

	@Override
	protected void updateCellValue(C value) {
		lookupField.setValue(value);
	}

	@Override
	protected C getEditedValue() {
		return lookupField.getValue();
	}

	@Override
	protected @NotNull Control getEditView() {
		if (lookupField == null) {
			lookupField = new LookupField<>();
			lookupField.setShowSuggestionWaitTime(column.getShowSuggestionWaitTime());
			lookupField.setValue(getItem());
			lookupField.setPropertyName(column.getLookupPropertyName());
			lookupField.setController(column.getLookupController());
			lookupField.setDisableManualInput(column.getDisableLookupManualInput());
    
			/**
			 * Disable traversing focus using LEFT, RIGHT, UP and DOWN.
			 */
			lookupField.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if ((event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT
							  || event.getCode() == KeyCode.UP
							  || event.getCode() == KeyCode.DOWN)
							  && isEditing()) {
						event.consume();
					}
				}
			});

			lookupField.valueProperty().addListener(new ChangeListener<C>() {

				@Override
				public void changed(ObservableValue<? extends C> ov, C t, C newValue) {
					for (CellEditorListener<R, C> svl : column.getCellEditorListeners()) {
						svl.valueChanged(getTableRow().getIndex(), column.getPropertyName(), (R) getTableRow().getItem(), newValue);
					}
				}
			});

			/**
			 * Use event filter instead on onKeyPressed because Enter and Escape have
			 * been consumed by lookupField itself
			 */
			lookupField.addEventFilter(KeyEvent.KEY_PRESSED, t -> {
				if (t.getCode() == KeyCode.ENTER && !t.isControlDown()) {
					commitEdit(lookupField.getValue());
				} else if (t.getCode() == KeyCode.ESCAPE) {
					lookupField.resetDisplayText();
					cancelEdit();
					/**
					 * Propagate ESCAPE key press to cell
					 */
					LookupTableCell.this.fireEvent(t);
				}
			});
		}
		return lookupField;
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
