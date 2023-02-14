package treeview;

import javafx.scene.control.Skin;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.effect.BlendMode;
import javafx.util.Callback;

public class RecursiveTreeView extends TreeView<TreeModel<?>> {

    public RecursiveTreeView() {
        setRoot(new TreeItem<>());
        setCellFactory(new Callback<TreeView<TreeModel<?>>, TreeCell<TreeModel<?>>>() {
            @Override
            public TreeCell<TreeModel<?>> call(TreeView<TreeModel<?>> param) {
                RecursiveTreeCell cell = new RecursiveTreeCell();
                cell.setBlendMode(BlendMode.GREEN);

                TreeModel<?> item = cell.getItem();

                final TreeModel<?> value = item.getValue();

                return cell;
            }
        });

        final ClassInfo classInfo = new ClassInfo();

        getRoot().getChildren().add(new TreeItem<>(classInfo));
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new RecursiveTreeViewSkin(this);
    }
}
