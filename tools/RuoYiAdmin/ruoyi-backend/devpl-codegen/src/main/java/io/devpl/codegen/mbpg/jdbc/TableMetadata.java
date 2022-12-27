package io.devpl.codegen.mbpg.jdbc;

import java.io.Serializable;
import java.util.Objects;

/**
 * @see java.sql.DatabaseMetaData#getTables(String, String, String, String[])
 */
public final class TableMetadata {

    private String tableCat;
    private String tableSchem;
    private String tableName;
    private String tableType;

    /**
     * String => explanatory comment on the table (may be null)
     */
    private String remarks;

    /**
     * String => the types catalog (may be null)
     */
    private String typeCat;

    /**
     * String => the types schema (may be null)
     */
    private String typeSchem;
    private String typeName;
    private String selfReferencingColName;
    private String refGeneration;

    public String getTableCat() {
        return tableCat;
    }

    public void setTableCat(String tableCat) {
        this.tableCat = tableCat;
    }

    public String getTableSchem() {
        return tableSchem;
    }

    public void setTableSchem(String tableSchem) {
        this.tableSchem = tableSchem;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTypeCat() {
        return typeCat;
    }

    public void setTypeCat(String typeCat) {
        this.typeCat = typeCat;
    }

    public String getTypeSchem() {
        return typeSchem;
    }

    public void setTypeSchem(String typeSchem) {
        this.typeSchem = typeSchem;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSelfReferencingColName() {
        return selfReferencingColName;
    }

    public void setSelfReferencingColName(String selfReferencingColName) {
        this.selfReferencingColName = selfReferencingColName;
    }

    public String getRefGeneration() {
        return refGeneration;
    }

    public void setRefGeneration(String refGeneration) {
        this.refGeneration = refGeneration;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TableMetadata{");
        sb.append("tableCat='").append(tableCat).append('\'');
        sb.append(", tableSchem='").append(tableSchem).append('\'');
        sb.append(", tableName='").append(tableName).append('\'');
        sb.append(", tableType='").append(tableType).append('\'');
        sb.append(", remarks='").append(remarks).append('\'');
        sb.append(", typeCat='").append(typeCat).append('\'');
        sb.append(", typeSchem='").append(typeSchem).append('\'');
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", selfReferencingColName='").append(selfReferencingColName).append('\'');
        sb.append(", refGeneration='").append(refGeneration).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableMetadata that = (TableMetadata) o;
        return Objects.equals(tableCat, that.tableCat) && Objects.equals(tableSchem, that.tableSchem) && Objects.equals(tableName, that.tableName) && Objects.equals(tableType, that.tableType) && Objects.equals(remarks, that.remarks) && Objects.equals(typeCat, that.typeCat) && Objects.equals(typeSchem, that.typeSchem) && Objects.equals(typeName, that.typeName) && Objects.equals(selfReferencingColName, that.selfReferencingColName) && Objects.equals(refGeneration, that.refGeneration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableCat, tableSchem, tableName, tableType, remarks, typeCat, typeSchem, typeName, selfReferencingColName, refGeneration);
    }
}
