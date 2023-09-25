package io.devpl.fxui.layout;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * 导航面板
 */
public class LayoutPane extends SplitPane {

    // 菜单映射
    Map<MenuItem, Node> contentMap = new IdentityHashMap<>();

    MenuContainer menu;
    ContentContainer contentPane;

    public LayoutPane() {
        contentPane = new ContentContainer();
        menu = new MenuContainer(node -> contentPane.switchTo(node));

        getItems().addAll(menu, contentPane);

        final Label label = new Label("AAA");

        label.setPrefSize(600.0, 500.0);

        menu.prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                final double prefWidth = LayoutPane.this.getPrefWidth();
                final double percentage = newValue.doubleValue() / prefWidth;
                setDividerPosition(0, percentage);
            }
        });

        final Divider divider = getDividers().get(0);

        divider.positionProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                menu.setPrefWidth(LayoutPane.this.getPrefWidth() * newValue.doubleValue());
            }
        });

        menu.addMenu(new MenuItem("A", label));
        menu.addMenu(new MenuItem("B", new Label("BBB")));
        menu.addMenu(new MenuItem("C", new Label("CCC")));
        menu.addMenu(new MenuItem("D", new Label("DDD")));
    }
}
