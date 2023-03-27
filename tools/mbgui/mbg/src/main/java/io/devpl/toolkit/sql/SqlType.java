package io.devpl.toolkit.sql;

import java.sql.Types;

/**
 * @see java.sql.Types
 */
public enum SqlType {

    VARCHAR(Types.VARCHAR, new int[]{50}),
    INT(Types.INTEGER, new int[]{10}),
    BIGINT(Types.BIGINT, new int[]{20}),
    TINYINT(Types.TINYINT, new int[]{1}),
    DATETIME(Types.DATE),
    DATE(Types.DATE),
    FLOAT(Types.FLOAT),
    DECIMAL(Types.DECIMAL, new int[]{5, 2}),
    CHAR(Types.CHAR, new int[]{5}),
    TIMESTAMP(Types.TIMESTAMP);

    /**
     * JDBC 类型
     */
    int jdbcType;

    /**
     * 长度
     */
    int[] length;

    SqlType(int jdbcType) {
        this.jdbcType = jdbcType;
    }

    SqlType(int jdbcType, int[] length) {
        this.jdbcType = jdbcType;
        this.length = length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.jdbcType);
        if (this.length != null) {
            int len = this.length.length;
            sb.append("(");
            if (len == 1) {
                sb.append(this.length[0]);
            } else if (len == 2) {
                sb.append(this.length[0]).append(this.length[1]);
            } else {
                throw new IllegalStateException("长度只有2个");
            }
        }
        return sb.toString();
    }
}
