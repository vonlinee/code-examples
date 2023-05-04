package org.apache.ddlutils;

public enum DbType {

    DB2("DB2", "", ""),
    DERBY("Derby", "", "");

    final String dbTypeName;
    final String driverClassName;
    final String subProtocol;

    DbType(String dbTypeName, String driverClassName, String subProtocol) {
        this.dbTypeName = dbTypeName;
        this.driverClassName = driverClassName;
        this.subProtocol = subProtocol;
    }

    public static DbType valueOfName(String dbTypeName) {
        for (DbType value : values()) {
            if (value.dbTypeName.equalsIgnoreCase(dbTypeName)) {
                return value;
            }
        }
        return null;
    }
}
