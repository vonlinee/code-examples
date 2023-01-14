package io.devpl.toolkit.fxui.view.navigation;

import io.devpl.toolkit.fxui.model.props.ConnectionInfo;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.javafx.FontIcon;

public class DatabaseNavigationTreeView extends TreeView<String> {

    public DatabaseNavigationTreeView() {
        setRoot(new TreeItem<>());
        setShowRoot(false);
        setCellFactory(param -> {
            TextFieldTreeCell<String> treeCell = new TextFieldTreeCell<>();
            treeCell.setGraphicTextGap(5);
            return treeCell;
        });
    }

    public void addConnection(ConnectionInfo connectionInfo) {
        ConnectionTreeItem treeItem = new ConnectionTreeItem(connectionInfo);
        FontIcon fontIcon = FontIcon.of(FontAwesomeRegular.FOLDER);
        treeItem.setGraphic(fontIcon);
        treeItem.connect();

        getRoot().getChildren().add(treeItem);
    }
}
