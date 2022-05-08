package io.maker.extension.poi;

import org.apache.poi.ss.usermodel.Row;

public interface RowProcessor<T> {
	T process(Row row);
}