package org.example;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.table.TableControl;
import com.panemu.tiwulfx.table.TableControlBehavior;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class App extends Application {

    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiwulfx-demoPU");

    final EntityManager entityManager = factory.createEntityManager();

    @Override
    public void start(Stage primaryStage) {

        TableControl<FieldInfo> table = new TableControl<>(FieldInfo.class);

        table.setBehavior(new TableControlBehavior<>() {
            @Override
            public TableData<FieldInfo> loadData(int startIndex, List<TableCriteria> filteredColumns, List<String> sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {

                String sql = "select * from field_info";

                final Query query = entityManager.createNativeQuery(sql, FieldInfo.class);
                final List resultList = query.getResultList();

                final TableData<FieldInfo> data = new TableData<>();
                data.setRows(resultList);

                data.setTotalRows(resultList.size());
                return data;
            }

            @Override
            public List<FieldInfo> insert(List<FieldInfo> newRecords) {
                for (FieldInfo newRecord : newRecords) {
                    entityManager.persist(newRecord);
                }
                return newRecords;
            }

            @Override
            public List<FieldInfo> update(List<FieldInfo> records) {
                return super.update(records);
            }
        });

        Scene scene = new Scene(table, 600, 400);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}