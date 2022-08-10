package io.maker.extension.utils.print.enums;

import io.maker.extension.utils.print.table.TableCell;

public enum NullPolicy {

	THROW {
		@Override
		public TableCell getCell(TableCell tableCell) {
			throw new IllegalArgumentException("cell or value is null: " + tableCell);
		}
	},
	NULL_STRING {
		@Override
		public TableCell getCell(TableCell tableCell) {
			if (tableCell == null) {
				return new TableCell("null");
			}
			tableCell.setValue("null");
			return tableCell;
		}
	},
	EMPTY_STRING {
		@Override
		public TableCell getCell(TableCell tableCell) {
			if (tableCell == null) {
				return new TableCell("");
			}
			tableCell.setValue("");
			return tableCell;
		}
	};

	public abstract TableCell getCell(TableCell tableCell);
}
