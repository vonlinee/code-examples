package components.sidebar;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;
import utils.ResourceLoader;

import java.net.URL;

public class SideBar extends TreeView<String> {
    public SideBar() {

        setRoot(new TreeItem<>("Root"));
        for (int i = 0; i < 5; i++) {
            getRoot().getChildren().add(new TreeItem<>(String.valueOf(i)));
        }

        for (int i = 0; i < getRoot().getChildren().size(); i++) {
            if (i % 2 == 0) {
                final TreeItem<String> stringTreeItem = getRoot().getChildren().get(i);
                for (int j = 0; j < i; j++) {
                    stringTreeItem.getChildren().add(new TreeItem<>(i + " - " + j));
                }
            }
        }

        this.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> param) {
                TreeCell<String> cell = new TreeCell<>();

                final URL url = ResourceLoader.loadFromLocalProject("components\\sidebar\\arrow.svg");


                SVGImage svg = SVGLoader.load(url);

                final StackPane vBox = new StackPane(svg);
                vBox.setPrefSize(20, 20);

                cell.treeItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        newValue.expandedProperty().addListener((observable1, oldValue1, newValue1) -> {
                            if (newValue1) {
                                svg.setRotate(90);
                            } else {
                                svg.setRotate(0);
                            }
                        });
                    }
                });

                cell.setDisclosureNode(vBox);
                return cell;
            }
        });
    }
}
