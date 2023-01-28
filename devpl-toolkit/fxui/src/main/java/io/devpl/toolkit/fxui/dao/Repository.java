package io.devpl.toolkit.fxui.dao;

import io.devpl.toolkit.fxui.utils.ConnectionManager;

import java.sql.Connection;

public class Repository {

    protected Connection getConnection() {
        Connection conn;
        try {
            conn = ConnectionManager.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}
