package com.panemu.tiwulfx.table;

/**
 * This interface is used to listen to editCommit event of a CustomTableColumn. Add an
 * implementation of this interface to {@link CustomTableColumn#addEditCommitListener(com.panemu.tiwulfx.table.EditCommitListener)}
 * to listen to any value change of that column.
 * @author amrullah
 * @see CustomTableColumn#addEditCommitListener(com.panemu.tiwulfx.table.EditCommitListener)
 */
public interface EditCommitListener<R, C> {
    void editCommited(CustomTableColumn<R, C> column, R record, C oldValue, C newValue);
}
