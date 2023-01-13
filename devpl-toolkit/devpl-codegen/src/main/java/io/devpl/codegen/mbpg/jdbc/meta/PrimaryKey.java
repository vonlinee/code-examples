package io.devpl.codegen.mbpg.jdbc.meta;

import lombok.Data;

@Data
public class PrimaryKey {

    // String => table catalog (may be null)
    private String tableCat;

    /**
     * String => table schema (may be null)
     */
    private String tableSchem;

    /**
     * String => table name
     */
    private String tableName;

    /**
     * String => column name
     */
    private String columnName;

    /**
     * short => sequence number within primary key( a value of 1 represents
     * the first column of the primary key, a value of 2 would represent the
     * second column within the primary key).
     */
    private Short keySeq;

    /**
     * String => primary key name (may be null)
     */
    private String pkName;
}
