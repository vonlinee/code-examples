package org.example;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.table.TableControl;
import com.panemu.tiwulfx.table.TableControlBehavior;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application {

    public App() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        TableControl<FieldInfo> table = new TableControl<>(FieldInfo.class);

        table.setBehavior(new TableControlBehavior<>() {
            @Override
            public TableData<FieldInfo> loadData(int startIndex, List<TableCriteria> filteredColumns, List<String> sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {
                return null;
            }

            @Override
            public List<FieldInfo> insert(List<FieldInfo> newRecords) {
                return newRecords;
            }
        });

        Scene scene = new Scene(table, 600, 400);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}