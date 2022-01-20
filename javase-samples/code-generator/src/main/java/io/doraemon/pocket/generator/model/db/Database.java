package io.doraemon.pocket.generator.model.db;

import java.io.Serializable;

public class Database implements Serializable {

	private static final long serialVersionUID = 1L;
	private String databaseName;
    private MetaData metaData;
    
    public static class MetaData implements Serializable {
        private String driverClassName;
        private String url;
        private String userName;
        private String password;
    }
}
