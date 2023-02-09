package apps.filestructure.java;

import apps.filestructure.java.ClassItem;
import apps.filestructure.java.FieldItem;
import apps.filestructure.java.MethodItem;
import javafx.geometry.Pos;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;

/**
 * Java文件结构树
 */
public class JavaFileStrucutreTreeView extends TreeView<String> {

    public JavaFileStrucutreTreeView() {
        setRoot(new TreeItem<>());
        setShowRoot(false);
        setCellFactory(param -> {
            TextFieldTreeCell<String> cell = new TextFieldTreeCell<>();
            cell.setAlignment(Pos.CENTER_LEFT);
            cell.setEditable(false);
            cell.setOnMouseClicked(event -> {
                event.consume();
                int index = param.getSelectionModel().getSelectedIndex();

                @SuppressWarnings("unchecked") TreeCell<String> selectedTreeCell = (TreeCell<String>) event.getSource();
                TreeItem<String> selectedTreeItem = selectedTreeCell.getTreeItem();

                if (selectedTreeItem == null) {
                    return;
                }
                if (selectedTreeItem instanceof FieldItem) {

                } else if (selectedTreeItem instanceof MethodItem) {

                } else if (selectedTreeItem instanceof ClassItem) {

                }
            });
            return cell;
        });
    }

    public final void addClass(ClassItem classItem) {
        getRoot().getChildren().add(classItem);
    }
}
