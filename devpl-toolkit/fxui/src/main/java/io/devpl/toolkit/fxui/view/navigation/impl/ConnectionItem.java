package io.devpl.toolkit.fxui.view.navigation.impl;

import io.devpl.codegen.mbpg.jdbc.meta.ColumnMetadata;
import io.devpl.codegen.mbpg.jdbc.meta.TableMetadata;
import io.devpl.toolkit.fxui.model.props.ConnectionInfo;
import io.devpl.toolkit.fxui.utils.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionItem implements NavigationItem {

    // 连接名称
    private String connectionName;

    private ConnectionInfo connectionInfo = new ConnectionInfo();

    private List<DirectoryItem<? extends NavigationItem>> rawChildren;
    private ObservableList<DirectoryItem<? extends NavigationItem>> children;

    public ConnectionItem() {
        rawChildren = new ArrayList<>();
        children = FXCollections.observableList(rawChildren);
        // 默认的目录
        rawChildren.add(0, new DirectoryItem<DatabaseItem>("数据库"));
    }

    @Override
    public String getDispalyValue() {
        return connectionName;
    }

    @Override
    public void setDispalyValue(String dispalyValue) {
        connectionName = dispalyValue;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    @Override
    public boolean hasChild() {
        return true;
    }

    @Override
    public List<DirectoryItem<? extends NavigationItem>> getChildren() {
        return rawChildren;
    }

    public void addDatabase(DatabaseItem databaseItem) {
        DirectoryItem<? extends NavigationItem> directoryItem = rawChildren.get(0);
        directoryItem.addChildItem(databaseItem);
    }

    public void connect() {
        Properties properties = new Properties();
        properties.setProperty("user", connectionInfo.getUsername());
        properties.setProperty("password", connectionInfo.getPassword());
        String url = "jdbc:mysql://localhost:3306/lgdb_campus_intelligent_portrait?useUnicode=true&characterEncoding=UTF-8&useSSL=false&&serverTimezone=GMT%2B8";
        Connection connection = null;
        try {
            connection = DBUtils.getConnection(url, properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (connection == null) {
            System.out.println("连接失败");
            return;
        }
        setConnectionName("jdbc:mysql://localhost:3306");
        List<TableMetadata> tablesMetadata = DBUtils.getTablesMetadata(connection, null, null);
        DatabaseItem databaseItem = new DatabaseItem();
        databaseItem.setDatabaseName("lgdb_campus_intelligent_portrait");
        addDatabase(databaseItem);
        for (TableMetadata table : tablesMetadata) {
            final TableItem tableItem = new TableItem();
            databaseItem.addTable(tableItem);
            tableItem.setTableName(table.getTableName());
            // 列
            List<ColumnMetadata> columns = DBUtils.getColumnsMetadata(connection, table.getTableName());
            for (ColumnMetadata column : columns) {
                ColumnItem columnItem = new ColumnItem();
                columnItem.setColumnName(column.getColumnName());
                tableItem.addColumn(columnItem);
            }
        }

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("关闭连接失败", e);
        }
    }
}
