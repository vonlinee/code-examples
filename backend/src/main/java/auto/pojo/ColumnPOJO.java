package auto.pojo;

import java.io.Serializable;

public class ColumnPOJO implements Serializable {
    // 列名
    private Object columnName;
    // 驼峰列名
    private Object humpColumnName;
    // 列类型
    private Object columnTypeName;

    private String javaType;

    private Object commentName;

    private boolean key;

    public Object getColumnName() {
        return columnName;
    }

    public Object getHumpColumnName() {
        return humpColumnName;
    }

    public void setHumpColumnName(Object humpColumnName) {
        this.humpColumnName = humpColumnName;
    }

    public void setColumnName(Object columnName) {
        this.columnName = columnName;
    }

    public Object getColumnTypeName() {
        if (columnTypeName.toString().equals("INT"))
            return "INTEGER";
        else
            return columnTypeName.toString();
    }

    public Object getCommentName() {
        return commentName;
    }

    public void setCommentName(Object commentName) {
        this.commentName = commentName;
    }

    public void setColumnTypeName(Object columnTypeName) {
        this.columnTypeName = columnTypeName;
    }


    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public boolean getKey() {
        return key;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public ColumnPOJO() {
    }

    public ColumnPOJO(Object columnName, Object humpColumnName, Object columnTypeName, String commentName, String javaType, Boolean key) {
        this.columnName = columnName;
        this.humpColumnName = humpColumnName;
        this.columnTypeName = columnTypeName;
        this.commentName = commentName;
        this.javaType = javaType;
        this.key = key;
    }

    public ColumnPOJO(Object columnName, Object columnTypeName) {
        this.columnName = columnName;
        this.columnTypeName = columnTypeName;
    }

    @Override
    public String toString() {
        return "ColumnPOJO{" +
                "columnName=" + columnName +
                ", humpColumnName=" + humpColumnName +
                ", columnTypeName=" + columnTypeName +
                '}';
    }
}
