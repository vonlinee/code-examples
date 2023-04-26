package mybatis;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhaoyong
 * @Date 2022/1/21
 * @Description
 */
public class TreeTableViewSample extends Application {

    List<Employee> employees = Arrays.<Employee>asList(//创建雇员实体对象集合
            new Employee("Ethan Williams", "ethan.williams@example.com"),
            new Employee("Emma Jones", "emma.jones@example.com"),
            new Employee("Michael Brown", "michael.brown@example.com"),
            new Employee("Anna Black", "anna.black@example.com"),
            new Employee("Rodger York", "roger.york@example.com"),
            new Employee("Susan Collins", "susan.collins@example.com"));

    private final ImageView depIcon = new ImageView(
            new Image("file:C:\\Users\\Von\\Pictures\\pexels-jot-singh-2179483.jpg")
    );//创建一个图片视图区域组件

    final TreeItem<Employee> root =
            new TreeItem<>(new Employee("人员", "邮箱"), depIcon);//创建树形结构根节点选项

    public static void main(String[] args) {
        Application.launch(TreeTableViewSample.class, args);
    }

    @Override
    public void start(Stage stage) {
        root.setExpanded(true);//设置根节点展开
        employees.forEach((employee) -> {
            root.getChildren().add(new TreeItem<>(employee));//将员工信息添加到树形选择项中，并将树形选项添加到树根节点中
        });
        stage.setTitle("树形表格");
        final Scene scene = new Scene(new Group(), 400, 400);//场景大小
        Group sceneRoot = (Group) scene.getRoot();//场景上的节点组对象的获取

        TreeTableColumn<Employee, String> empColumn =
                new TreeTableColumn<>("列1");//树形表格列名定义
        empColumn.setPrefWidth(150);//列宽设置
        empColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Employee, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getName())
        );
        //设置列单元格的格式和取值规则

        TreeTableColumn<Employee, String> emailColumn = new TreeTableColumn<>("列2");
        emailColumn.setPrefWidth(190);
        emailColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Employee, String> param) ->
                new ReadOnlyStringWrapper(param.getValue().getValue().getEmail())
        );//设置列单元格的格式和取值规则

        TreeTableView<Employee> treeTableView = new TreeTableView<>(root);//创建树表并添加根节点

        treeTableView.getColumns().setAll(empColumn, emailColumn);//树表中添加列
        sceneRoot.getChildren().add(treeTableView);//根节点容器添加树表
        stage.setScene(scene);//舞台设置场景
        stage.show();//舞台展现
    }

    public class Employee {//domain POJO 类型定义

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
