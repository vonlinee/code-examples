package io.devpl.toolkit.fxui.view.navigation;

import io.devpl.toolkit.fxui.model.ConnectionInfo;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.Ikonli;
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands;
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrandsIkonHandler;
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
