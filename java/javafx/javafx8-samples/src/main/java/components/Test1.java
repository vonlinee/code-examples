package components;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test1 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        DataTableView<FieldInfo> tableView = new DataTableView<>(FieldInfo.class);

        tableView.setDataSupplier(new DataSupplier<FieldInfo>() {
            @Override
            public ObservableList<FieldInfo> getRows(int pageIndex, int pageSize) {
                return null;
            }

            @Override
            public FieldInfo getRow() {
                FieldInfo fieldInfo = new FieldInfo();
                fieldInfo.setName("name");
                fieldInfo.setRemarks("名称");
                return fieldInfo;
            }
        });

        Scene scene = new Scene(tableView, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
