package io.devpl.toolkit.fxui.test;

import io.devpl.toolkit.fxui.framework.JavaFXApplication;
import io.devpl.toolkit.fxui.model.DBTableListModel;
import io.devpl.toolkit.fxui.utils.ConnectionManager;
import io.devpl.toolkit.fxui.utils.DBUtils;
import io.devpl.toolkit.fxui.utils.TableMetadata;
import io.devpl.toolkit.fxui.view.CodeGenMainView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public class TestFXMLApplication extends JavaFXApplication {

    @Override
    public void start(Stage primaryStage) throws Exception {
        CodeGenMainView root = new CodeGenMainView();
        final Connection connection = ConnectionManager.getConnection();
        List<TableMetadata> tmds = DBUtils.getTablesMetadata(connection);
        int i = 1;
        for (TableMetadata tmd : tmds) {
            final DBTableListModel model = new DBTableListModel();
            model.setSelected(false);
            model.setId(i++);
            model.setTableName(tmd.getTableName());
            model.setTableComment(tmd.getRemarks());
            model.setCreateTime(LocalDateTime.now());
            root.addRow(model);
        }
        final Scene scene = new Scene(root);

        primaryStage.setMinWidth(800.0);
        primaryStage.setMinHeight(400.0);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
