package io.devpl.spring.extension.utils.print.enums;

import io.devpl.spring.extension.utils.print.table.TableCell;

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
