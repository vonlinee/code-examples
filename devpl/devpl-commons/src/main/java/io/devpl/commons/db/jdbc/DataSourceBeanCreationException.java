package io.devpl.commons.db.jdbc;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;

public class DataSourceBeanCreationException extends BeanCreationException {

    private final DataSourceProperties properties;

    private final EmbeddedDatabaseConnection connection;

    DataSourceBeanCreationException(String message, DataSourceProperties properties,
                                    EmbeddedDatabaseConnection connection) {
        super(message);
        this.properties = properties;
        this.connection = connection;
    }

    DataSourceProperties getProperties() {
        return this.properties;
    }

    EmbeddedDatabaseConnection getConnection() {
        return this.connection;
    }
}