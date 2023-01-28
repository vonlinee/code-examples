package io.devpl.toolkit.fxui.view.navigation;

import io.devpl.codegen.mbpg.jdbc.meta.ColumnMetadata;
import io.devpl.codegen.mbpg.jdbc.meta.TableMetadata;
import io.devpl.toolkit.fxui.model.props.ConnectionInfo;
import io.devpl.toolkit.fxui.utils.DBUtils;
import io.devpl.toolkit.fxui.utils.StringUtils;
import javafx.scene.control.TreeItem;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionTreeItem extends TreeItem<String> {

    private final ConnectionInfo connectionInfo;

    private static Log log = LogFactory.getLog(ConnectionTreeItem.class);

    public ConnectionTreeItem(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public void fillChildren() {
        try {
            connect();
        } catch (Exception exception) {
            log.error("连接失败", exception);
        }
    }

    /**
     * 根据连接信息获取数据库，表信息，添加子元素
     * @throws SQLException SQLException
     */
    public void connect() throws SQLException {
        Connection connection = connectionInfo.getConnection();
        List<DatabaseTreeItem> databaseTreeItemList = new ArrayList<>();
        if (StringUtils.hasText(connectionInfo.getSchema())) {
            DatabaseTreeItem databaseTreeItem = new DatabaseTreeItem();
            databaseTreeItem.setDatabaseName(connectionInfo.getSchema());
            databaseTreeItem.setGraphic(new FontIcon(FontAwesomeSolid.DATABASE));
            databaseTreeItemList.add(databaseTreeItem);
            addTables(connectionInfo, databaseTreeItem);
        } else {
            List<String> results = DBUtils.query(connection, "show databases", new ColumnListHandler<>("Database"));
            for (String result : results) {
                DatabaseTreeItem databaseTreeItem = new DatabaseTreeItem();
                databaseTreeItem.setDatabaseName(String.valueOf(result));
                databaseTreeItem.setGraphic(new FontIcon(FontAwesomeSolid.DATABASE));
                databaseTreeItemList.add(databaseTreeItem);
                addTables(connectionInfo, databaseTreeItem);
            }
        }
        this.getChildren().addAll(databaseTreeItemList);
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("关闭连接失败", e);
        }
    }

    private void addTables(ConnectionInfo connectionInfo, DatabaseTreeItem databaseTreeItem) throws SQLException {
        Connection connection = connectionInfo.getConnection(databaseTreeItem.getValue(), null);
        // 加载所有的数据库表
        List<TableMetadata> tablesMetadata = DBUtils.getTablesMetadata(connection, null, null);
        // databaseTreeItemList
        for (TableMetadata table : tablesMetadata) {
            TableTreeItem item = new TableTreeItem();
            item.setValue(table.getTableName());
            item.setGraphic(FontIcon.of(FontAwesomeSolid.TABLE));
            databaseTreeItem.getChildren().add(item);
            // 列
            List<ColumnMetadata> columns = DBUtils.getColumnsMetadata(connection, table.getTableName());
            for (ColumnMetadata column : columns) {
                ColumnTreeItem columnTreeItem = new ColumnTreeItem();
                columnTreeItem.setGraphic(FontIcon.of(FontAwesomeSolid.COLUMNS));
                columnTreeItem.setValue(column.getColumnName());
                item.getChildren().add(columnTreeItem);
            }
        }
    }
}
