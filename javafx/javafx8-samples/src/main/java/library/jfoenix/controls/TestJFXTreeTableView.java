package library.jfoenix.controls;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TestJFXTreeTableView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        JFXTreeTableView<A> treeTableView = new JFXTreeTableView<>();

        JFXTreeTableColumn<A, String> name = new JFXTreeTableColumn<>("name");
        treeTableView.getColumns().add(name);
        name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<A, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<A, String> param) {
                String value = String.valueOf(param.getValue().getValue().getGroupedValue());
                return new SimpleStringProperty(value);
            }
        });
        final A a = new A();
        a.setGroupedValue("12");

        TreeItem<A> root = new TreeItem<>(a);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(true);
        Scene scene = new Scene(treeTableView, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

class A extends RecursiveTreeObject<A> {

}

class B extends A {

}