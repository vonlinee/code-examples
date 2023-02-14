package scenegraph.node.parent.control.treetableview;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class MultipleColumnsData extends Application {

    List<Employee> employees = Arrays.asList(
            new Employee("Ethan Williams", "ethan.williams@example.com"),
            new Employee("Emma Jones", "emma.jones@example.com"),
            new Employee("Michael Brown", "michael.brown@example.com"),
            new Employee("Anna Black", "anna.black@example.com"),
            new Employee("Rodger York", "roger.york@example.com"),
            new Employee("Susan Collins", "susan.collins@example.com"));

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MultiplateCloumnsData");
        Group group = new Group();
        final TreeItem<Employee> root = new TreeItem<>(new Employee("Sales Department", ""));
        root.setExpanded(true);

        // 多级此处设置
        employees.forEach((employee) -> {
            TreeItem<Employee> child = new TreeItem<>(employee);
            employees.forEach((employee2) -> {
                child.getChildren().add(new TreeItem<>(employee2));
            });
            root.getChildren().add(child);
        });

        Scene scene = new Scene(group);
        TreeTableColumn<Employee, String> empColumn = new TreeTableColumn<>("Employee");
        empColumn.setPrefWidth(150);
        empColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Employee, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getName())
        );

        TreeTableColumn<Employee, String> emailColumn = new TreeTableColumn<>("Email");
        emailColumn.setPrefWidth(190);
        emailColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Employee, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getEmail())
        );
        TreeTableView<Employee> treeTableView = new TreeTableView<>(root);
        treeTableView.getColumns().setAll(empColumn, emailColumn);
        group.getChildren().add(treeTableView);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 数据POJO
    public static class Employee {

        private SimpleStringProperty name;
        private SimpleStringProperty email;

        public SimpleStringProperty nameProperty() {
            if (name == null) {
                name = new SimpleStringProperty(this, "name");
            }
            return name;
        }

        public SimpleStringProperty emailProperty() {
            if (email == null) {
                email = new SimpleStringProperty(this, "email");
            }
            return email;
        }

        private Employee(String name, String email) {
            this.name = new SimpleStringProperty(name);
            this.email = new SimpleStringProperty(email);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String fName) {
            name.set(fName);
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String fName) {
            email.set(fName);
        }
    }
}