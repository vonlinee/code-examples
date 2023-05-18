package com.panemu.tiwulfx.table;

public interface CellEditorListener<R, C> {

	/**
	 * It will be called when selected value is change although the new value
	 * is not yet been committed to table cell.
	 * 
	 * @param rowIndex
	 * @param propertyName
	 * @param record
	 * @param value property value of the record hold by the cell
	 */
	void valueChanged(int rowIndex, String propertyName, R record, C value);
}
