package io.maker.extension.poi;

import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.Map;

public interface ExcelRowProcessor<T> {

    T[] toArray(Row row);

    List<T> toList(Row row);

    Map<String, T> toMap(Row row);
}
