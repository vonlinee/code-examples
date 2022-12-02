package auto.pojo;

import java.io.Serializable;
import java.util.List;

public class TablePOJO implements Serializable {
    // 表名
    private String tableName;
    // 列名集
    private List<ColumnPOJO> columnCountList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnPOJO> getColumnCountList() {
        return columnCountList;
    }

    public void setColumnCountList(List<ColumnPOJO> columnCountList) {
        this.columnCountList = columnCountList;
    }

    public TablePOJO() {
    }

    public TablePOJO(String tableName, List<ColumnPOJO> columnCountList) {
        this.tableName = tableName;
        this.columnCountList = columnCountList;
    }

    @Override
    public String toString() {
        return "TablePOJO{" +
                "tableName=" + tableName +
                ", columnCountList=" + columnCountList +
                '}';
    }
}
