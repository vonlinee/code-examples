package io.devpl.toolkit.fxui.view.navigation;

import io.devpl.codegen.mbpg.jdbc.meta.ColumnMetadata;
import io.devpl.codegen.mbpg.jdbc.meta.TableMetadata;
import io.devpl.toolkit.fxui.model.ConnectionInfo;
import io.devpl.toolkit.fxui.utils.DBUtils;
import io.devpl.toolkit.fxui.view.navigation.impl.DatabaseItem;
import javafx.scene.control.TreeItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class ConnectionTreeItem extends TreeItem<String> {

    private ConnectionInfo connectionInfo;

    TreeItem<String> dbItems;
    TreeItem<String> userItems;
    TreeItem<String> adminUserItems;
    TreeItem<String> systemInfoItems;

    public ConnectionTreeItem(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
        dbItems = new TreeItem<>("数据库");
        userItems = new TreeItem<>("用户");
        adminUserItems = new TreeItem<>("管理员");
        systemInfoItems = new TreeItem<>("系统信息");
        getChildren().addAll(List.of(dbItems, userItems, adminUserItems, systemInfoItems));
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnectionInfo(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public void connect() {
        Properties properties = new Properties();
        properties.setProperty("user", connectionInfo.getUsername());
        properties.setProperty("password", connectionInfo.getPassword());
        String url = "jdbc:mysql://localhost:3306/lgdb_campus_intelligent_portrait?useUnicode=true&characterEncoding=UTF-8&useSSL=false&&serverTimezone=GMT%2B8";
        Connection connection = DBUtils.getConnection(url, properties);

        if (connection == null) {
            System.out.println("连接失败");
            return;
        }
        setValue("jdbc:mysql://localhost:3306");
        List<TableMetadata> tablesMetadata = DBUtils.getTablesMetadata(connection, null, null);
        DatabaseItem databaseItem = new DatabaseItem();
        databaseItem.setDatabaseName("lgdb_campus_intelligent_portrait");


        final DatabaseTreeItem databaseTreeItem = new DatabaseTreeItem();
        databaseTreeItem.setDatabaseName("lgdb_campus_intelligent_portrait");
        dbItems.getChildren().add(databaseTreeItem);


        for (TableMetadata table : tablesMetadata) {

            TableTreeItem item = new TableTreeItem();
            item.setValue(table.getTableName());

            databaseTreeItem.getChildren().add(item);

            // 列
            List<ColumnMetadata> columns = DBUtils.getColumnsMetadata(connection, table.getTableName());
            for (ColumnMetadata column : columns) {
                ColumnTreeItem columnTreeItem = new ColumnTreeItem();
                columnTreeItem.setValue(column.getColumnName());

                item.getChildren().add(columnTreeItem);
            }
        }

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("关闭连接失败", e);
        }
    }
}
