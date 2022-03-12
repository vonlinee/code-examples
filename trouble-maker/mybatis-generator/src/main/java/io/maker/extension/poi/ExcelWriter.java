package io.maker.extension.poi;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 模板
 */
public abstract class ExcelWriter {

    abstract Workbook create(File file);

    abstract void fill(Workbook workbook, List<Map<String, Object>> rows);

    abstract void write(Workbook workbook, File file);

    public final void write(List<Map<String, Object>> rows, File file) {
        Workbook workbook = create(file);
        fill(workbook, rows);
        write(workbook, file);
    }
}
