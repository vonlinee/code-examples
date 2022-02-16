package code.magicode.generator.db.extra;

import java.io.Serializable;

public class Table implements Serializable {
    private static final long serialVersionUID = 1L;

    private String databaseName;
    private String tableName;
    private String createdTime;
    private String lastUpdateTime;
    private MetaData metaData;

    public static class MetaData implements Serializable {
        private boolean autoIncrement = false;
        private boolean caseSensitive;
    }
}
