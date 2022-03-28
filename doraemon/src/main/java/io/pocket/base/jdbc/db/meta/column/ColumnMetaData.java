package io.pocket.base.jdbc.db.meta.column;

import com.google.common.base.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

public class ColumnMetaData {

    private String name;
    private int dataType;
    private String dataTypeName;
    private boolean primaryKey;
    private boolean generated;
    private boolean caseSensitive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    @Override
    public String toString() {
        return "ColumnMetaData{" +
                "name='" + name + '\'' +
                ", dataType=" + dataType +
                ", dataTypeName='" + dataTypeName + '\'' +
                ", primaryKey=" + primaryKey +
                ", generated=" + generated +
                ", caseSensitive=" + caseSensitive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnMetaData that = (ColumnMetaData) o;
        return dataType == that.dataType && primaryKey == that.primaryKey && generated == that.generated && caseSensitive == that.caseSensitive && Objects.equal(name, that.name) && Objects.equal(dataTypeName, that.dataTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, dataType, dataTypeName, primaryKey, generated, caseSensitive);
    }
}
