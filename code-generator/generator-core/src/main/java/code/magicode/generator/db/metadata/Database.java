package code.magicode.generator.db.metadata;

import java.io.Serializable;

public class Database implements Serializable {

    private String databaseName;
    private MetaData metaData;

    public static class MetaData implements Serializable {
        private String driverClassName;
        private String url;
        private String userName;
        private String password;
    }
}
