package mybatis;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import mybatis.tree.TreeNode;
import mybatis.tree.Visitor;

/**
 * Mapper 变量表
 */
public class MapperStatementTable extends TreeTableView<NamedValue> {

    final TreeItem<NamedValue> root;

    public MapperStatementTable() {
        setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        setEditable(true);
        setShowRoot(false);
        TreeTableColumn<NamedValue, String> col_name = new TreeTableColumn<>("名称");
        col_name.setCellValueFactory(param -> {
            TreeItem<NamedValue> item = param.getValue();
            if (item == null) {
                return new SimpleStringProperty();
            }
            NamedValue value = item.getValue();
            if (value == null) {
                return new SimpleStringProperty();
            }
            return new SimpleStringProperty(value.getName());
        });
        TreeTableColumn<NamedValue, Object> col_value = new TreeTableColumn<>("值");

        col_value.setCellValueFactory(param -> {
            TreeItem<NamedValue> item = param.getValue();
            if (item == null) {
                return new SimpleObjectProperty<>();
            }
            // 目录节点
            if (!item.getChildren().isEmpty()) {
                return new SimpleObjectProperty<>();
            }
            NamedValue value = item.getValue();
            if (value == null) {
                return new SimpleObjectProperty<>();
            }
            if (value.getValue() == null) {
                return new SimpleObjectProperty<>();
            }
            return new SimpleObjectProperty<>(value.getValue());
        });

        col_value.setCellFactory(new Callback<TreeTableColumn<NamedValue, Object>, TreeTableCell<NamedValue, Object>>() {
            @Override
            public TreeTableCell<NamedValue, Object> call(TreeTableColumn<NamedValue, Object> param) {
                TextFieldTreeTableCell<NamedValue, Object> cell = new TextFieldTreeTableCell<NamedValue, Object>(new StringConverter<Object>() {
                    @Override
                    public String toString(Object object) {
                        if (object == null) {
                            return null;
                        }
                        return object.toString();
                    }

                    @Override
                    public Object fromString(String string) {
                        return string;
                    }
                }) {
                    @Override
                    public void startEdit() {
                        super.startEdit();
                    }
                };
                cell.setEditable(true);
                return cell;
            }
        });
        col_value.setEditable(true);

        col_value.setOnEditCommit(event -> {
            Object newValue = event.getNewValue();
            TreeItem<NamedValue> rowValue = event.getRowValue();
            NamedValue value = rowValue.getValue();
            // 手动绑定数据
            if (value != null) {
                value.setValue(newValue);
            }
        });

        setRoot(root = new TreeItem<>());

        getColumns().add(col_name);
        getColumns().add(col_value);
    }

    public <T> void addItems(TreeNode<T> root) {
        // 去掉根节点
        for (TreeNode<T> child : root.getChildren()) {
            child.accept(new MyVisitor<>(this.root));
        }
    }

    public static class MyVisitor<T> implements Visitor<T> {

        TreeItem<NamedValue> parentItem;

        public MyVisitor(TreeItem<NamedValue> root) {
            this.parentItem = root;
        }

        @Override
        public Visitor<T> visitTree(TreeNode<T> tree) {
            return new MyVisitor<>(this.parentItem);
        }

        /**
         * 当前节点
         * @param parent 父节点
         * @param data   当前节点的数据
         */
        @Override
        public void visitData(TreeNode<T> parent, T data) {
            // 当前节点
            TreeItem<NamedValue> current = new TreeItem<>(new NamedValue(String.valueOf(data), null));
            this.parentItem.getChildren().add(current);
            // 默认展开
            current.setExpanded(true);
            if (parent.hasChildren()) {
                // 向子树遍历
                this.parentItem = current;
            }
        }
    }

    public void clear() {
        this.root.getChildren().clear();
    }
}
