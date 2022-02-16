package code.magicode.generator.db.extra;

import java.io.Serializable;

public class Database implements Serializable {

    private static final long serialVersionUID = 1L;
    private String databaseName;
    private MetaData metaData;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public static class MetaData implements Serializable {
        private String driverClassName;
        private String driverVersion;
        private String driverMajorVersion;
        private String driverMinorVersion;
        private String maxStatements;
        private String jdbcMajorVersion;

    }
}
