package io.doraemon.pocket.generator.model.db;

import java.io.Serializable;

public class Table implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String databaseName;
    private String tableName;
    private String createdTime;
    private String lastUpdateTime;

    public static class MetaData implements Serializable {
        private boolean autoIncrement = false;
        private boolean caseSensitive;
    }
}
