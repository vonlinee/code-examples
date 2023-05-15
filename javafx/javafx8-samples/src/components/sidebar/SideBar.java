package components.sidebar;

import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * @see com.sun.javafx.scene.control.skin.TreeViewSkin
 */
public class SideBar extends TreeView<String> {

    public SideBar() {

        MenuBar mb = new MenuBar();

        this.setRoot(new TreeItem<>("Root"));
        for (int i = 0; i < 5; i++) {
            this.getRoot().getChildren().add(new TreeItem<>(String.valueOf(i)));
        }
        for (int i = 0; i < this.getRoot().getChildren().size(); i++) {
            if (i % 2 == 0) {
                final TreeItem<String> stringTreeItem = this.getRoot().getChildren().get(i);
                for (int j = 0; j < i; j++) {
                    stringTreeItem.getChildren().add(new TreeItem<>(i + " - " + j));
                }
            }
        }

        this.setCellFactory(param -> new MenuTreeCell());
    }
}
