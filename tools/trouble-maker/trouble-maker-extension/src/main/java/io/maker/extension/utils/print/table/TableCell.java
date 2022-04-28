package io.maker.extension.utils.print.table;

import io.maker.extension.utils.print.enums.Alignment;

public class TableCell {

	private Alignment alignment;

	private String value;

	public TableCell(Alignment alignment, String value) {
		this.alignment = alignment;
		this.value = value;
	}

	public TableCell(String value) {
		this.alignment = Alignment.LEFT;
		this.value = value;
	}

	public void setAlign(Alignment alignment) {
		this.alignment = alignment;
	}

	public Alignment getAlign() {
		return alignment;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.format("{%s: %s,%s: %s}", "value", value, "align", alignment.name());
	}
}
