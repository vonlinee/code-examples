package io.devpl.toolkit.fxui.framework;

import io.devpl.toolkit.fxui.framework.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * 便捷的方法用于创建JavaFX控件
 * 类似于SWT
 */
public final class JFX {

    private final static ApplicationContext context = ApplicationContext.getInstance();

    public static Scene createScene(String fxmlKey) {
        FXMLLoader loader = getFXMLLoader(fxmlKey);
        return new Scene(loader.getRoot());
    }

    public static FXMLLoader getFXMLLoader(String fxmlKey) {
        return context.getFXMLLoader(fxmlKey);
    }

    public static Stage newStage(String title, Window owner, Modality modality, Scene scene) {
        return newStage(title, owner, modality, scene, true);
    }

    public static Stage newStage(String title, Window owner, Modality modality, Scene scene, boolean resiable) {
        Stage stage = new Stage();
        if (title != null) stage.setTitle(title);
        if (owner != null) stage.initOwner(owner);
        if (modality != null) stage.initModality(modality);
        if (scene != null) stage.setScene(scene);
        stage.setMaximized(false);
        stage.setResizable(resiable);
        return stage;
    }

    /**
     * 工具类
     */
    public static final StringConverter<String> DEFAULT_STRING_CONVERTER = new DefaultStringConverter();

    private JFX() {
    }

    public static Button newButton(String text) {
        Button button = new Button();
        button.setText(text);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setDefaultButton(true);
        return button;
    }

    public static Button newButton(String text, EventHandler<? super MouseEvent> value) {
        Button button = new Button();
        button.setText(text);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setDefaultButton(true);
        button.setOnMouseClicked(value);
        return button;
    }

    public static Button newButton(String text, Node graph) {
        Button button = new Button();
        button.setText(text);
        button.setGraphic(graph);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setDefaultButton(true);
        return button;
    }

    public static Button newButton(String text, Node graph, boolean defaultButton) {
        Button button = new Button();
        button.setText(text);
        button.setGraphic(graph);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setDefaultButton(defaultButton);
        return button;
    }

    public static Button newButton(String text, Node graph, Pos alignment, boolean defaultButton) {
        Button button = new Button();
        button.setText(text);
        button.setGraphic(graph);
        button.setAlignment(alignment);
        button.setDefaultButton(defaultButton);
        return button;
    }

    /**
     * 给控件添加一个按钮
     * @param group
     * @param text
     */
    public static Button addButton(Group group, String text, EventHandler<? super MouseEvent> value) {
        Button btn = newButton(text, value);
        group.getChildren().add(btn);
        return btn;
    }

    /**
     * 给控件添加一个按钮
     * @param pane
     * @param text
     */
    public static Button addButton(Pane pane, String text, EventHandler<? super MouseEvent> value) {
        Button btn = newButton(text, value);
        pane.getChildren().add(btn);
        return btn;
    }

    /**
     * 加载图片
     * @param pathname 相对路径
     * @param size     高度=宽度
     * @return ImageView
     */
    public static ImageView loadImageView(String pathname, double size) {
        ImageView dbImage = new ImageView(pathname);
        dbImage.setFitHeight(size);
        dbImage.setFitWidth(size);
        return dbImage;
    }

    /**
     * 加载图片
     * @param pathname 相对路径
     * @param w        宽度
     * @param h        高度
     * @return ImageView
     */
    public static ImageView loadImageView(String pathname, double w, double h) {
        ImageView dbImage = new ImageView(pathname);
        dbImage.setFitHeight(h);
        dbImage.setFitWidth(w);
        return dbImage;
    }

    /**
     * ImageView对象是否可以缓存
     * @param pathname
     * @param size
     * @param userData
     * @return
     */
    public static ImageView loadImageView(String pathname, double size, Object userData) {
        ImageView dbImage = new ImageView(pathname);
        dbImage.setFitHeight(size);
        dbImage.setFitWidth(size);
        dbImage.setUserData(userData);
        return dbImage;
    }

    public static ImageView loadImageView(String pathname, double w, double h, Object userData) {
        ImageView dbImage = new ImageView(pathname);
        dbImage.setFitHeight(h);
        dbImage.setFitWidth(w);
        dbImage.setUserData(userData);
        return dbImage;
    }

    public static void setTooltip(Control control, String tipText) {
        control.setTooltip(new Tooltip(tipText));
    }

    // ================================ 集合封装 ===========================

    /**
     * Observable集合实质上也是对普通集合的包装
     * @param elements 元素
     * @param <E>      元素类型
     * @return
     */
    public static <E> ObservableList<E> arrayOf(E[] elements) {
        return FXCollections.observableArrayList(elements);
    }

    public static <E> ObservableList<E> observableList(E[] elements) {
        return FXCollections.observableList(Arrays.asList(elements));
    }

    // ================================ 集合封装 ===========================

    /**
     * When accessing a Stage, timing is important, as the Stage is not created
     * until the very end of a View-creation process.
     * https://edencoding.com/stage-controller/
     * @param event JavaFX event
     * @return
     */
    public static <W extends Window> W getStage(@NotNull Event event) {
        final Object nodeSource = event.getSource();
        if (nodeSource instanceof Node) {
            return getStage((Node) nodeSource);
        }
        return null;
    }

    /**
     * 不要在Controller的构造，initialize方法里调用
     * @param node 节点
     * @param <W>
     * @return
     */
    public static <W extends Window> W getStage(@NotNull Node node) {
        @SuppressWarnings("unchecked") final W window = (W) node.getScene().getWindow();
        return window;
    }
}
