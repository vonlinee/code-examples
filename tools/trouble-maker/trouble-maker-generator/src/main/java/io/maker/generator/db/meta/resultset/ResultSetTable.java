package io.maker.generator.db.meta.resultset;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ResultSetTable {

    private int rowCount;
    private int columnCount;
    private List<Map<String, Object>> data;

    public Map<String, Object> getRow(int rowNum) {
        rangeCheck(rowNum, rowCount);
        return data.get(rowNum);
    }

    public List<Object> getColumn(int colNum) {
        rangeCheck(colNum, columnCount);
        ArrayList<Object> list = new ArrayList<>();
        return list;
    }

    private void rangeCheck(int i, int target) {
        StringBuilder sb = new StringBuilder();
        if (i < 0) {
            sb.append(" >= 0");
        }
        if (i >= target) {
            sb.append(" < ").append(target);
        }
        if (sb.length() > 0) {
            throw new ArrayIndexOutOfBoundsException("rowNum should meet the condition : " + sb);
        }
    }
}
