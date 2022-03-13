package io.maker.extension.poi;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 模板
 */
public abstract class ExcelWriter {

    public abstract Workbook create(File file) throws IOException;

    public abstract void fill(Workbook workbook, List<Map<String, Object>> rows);

    public abstract void write(Workbook workbook, File file) throws IOException;

    public final void write(List<Map<String, Object>> rows, File file) {
        try {
            Workbook workbook = create(file);
            fill(workbook, rows);
            write(workbook, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
