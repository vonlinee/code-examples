package components.sidebar;

import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import utils.ResourceLoader;

import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * 菜单单元格
 * @see javafx.scene.control.cell.TextFieldTreeCell
 */
public class MenuTreeCell extends TreeCell<String> {

    private final InvalidationListener treeItemGraphicListener = observable -> updateDisplay(getItem(), isEmpty());
    private final WeakInvalidationListener weakTreeItemGraphicListener = new WeakInvalidationListener(treeItemGraphicListener);
    private WeakReference<TreeItem<String>> treeItemRef;

    public MenuTreeCell() {
        super.setEditable(false);
        setPrefHeight(40.0);

        setTextFill(Color.BLACK);

        Background bg = new Background(new BackgroundFill(Color.valueOf("#E3D47FFF"), new CornerRadii(0), new Insets(0)));

        setOnMouseEntered(event -> {
            setBackground(bg);
        });
        setOnMouseExited(event -> setBackground(null));

        treeItemProperty().addListener(new WeakInvalidationListener(observable -> {
            TreeItem<String> oldTreeItem = treeItemRef == null ? null : treeItemRef.get();
            if (oldTreeItem != null) {
                oldTreeItem.graphicProperty().removeListener(weakTreeItemGraphicListener);
            }
            TreeItem<String> newTreeItem = getTreeItem();
            if (newTreeItem != null) {
                newTreeItem.graphicProperty().addListener(weakTreeItemGraphicListener);
                treeItemRef = new WeakReference<>(newTreeItem);
            }
        }));
        if (getTreeItem() != null) {
            getTreeItem().graphicProperty().addListener(weakTreeItemGraphicListener);
        }
    }

    protected final void updateDisplay(String text, Node graphic) {
        setText(text);
        setGraphic(graphic);
    }

    protected void updateDisplay(String item, boolean empty) {
        if (item == null || empty) {
            updateDisplay(null, null);
        } else {
            // update the graphic if one is set in the TreeItem
            TreeItem<String> treeItem = getTreeItem();
            if (treeItem != null && treeItem.getGraphic() != null) {
                updateDisplay(item, treeItem.getGraphic());
            } else {
                updateDisplay(item, null);
            }
        }
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        updateDisplay(item, empty);
    }

    /**
     * @return
     * @see com.sun.javafx.scene.control.skin.TreeCellSkin
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new MenuTreeCellSkin(this);
    }
}
