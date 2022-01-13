package code.fxutils.core.db;

import java.io.Serializable;

public class Table {
    private String databaseName;
    private String tableName;
    private String createdTime;
    private String lastUpdateTime;

    public static class MetaData implements Serializable {
        private boolean autoIncrement = false;
        private boolean caseSensitive;
    }
}
