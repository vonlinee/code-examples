package tools.file;

import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import resources.Icon;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件选择弹窗
 *
 * @see javafx.scene.control.ChoiceDialog
 * @see javafx.stage.FileChooser
 */
public class FileChooserDialog extends Dialog<ButtonType> {

    /**
     * 是否显示隐藏文件
     */
    private final BooleanProperty showHidden = new SimpleBooleanProperty(false);

    private final TreeView<File> fileTreeView;
    private final ComboBox<String> currentSelectedPath;

    /**
     * The initial directory for the displayed file dialog.
     */
    private ObjectProperty<File> initialDirectory;

    public final void setInitialDirectory(final File value) {
        initialDirectoryProperty().set(value);
    }

    public final File getInitialDirectory() {
        return (initialDirectory != null) ? initialDirectory.get() : null;
    }

    public final ObjectProperty<File> initialDirectoryProperty() {
        if (initialDirectory == null) {
            initialDirectory = new SimpleObjectProperty<File>(this, "initialDirectory");
        }
        return initialDirectory;
    }

    public FileChooserDialog() {
        setTitle("Choose File");
        DialogPane dialogPane = getDialogPane();
        ToolBar toolBar = new ToolBar();
        Button btn1 = new Button("Home");
        Button btn2 = new Button("Desktop");
        Button btn3 = new Button("Refresh");
        Button btn4 = new Button("New Folder");
        Button btn5 = new Button("Hide path");
        btn4.setGraphic(Icon.of("resources/svg/newFolder.svg"));
        btn3.setGraphic(Icon.of("resources/svg/refresh.svg"));
        btn2.setGraphic(Icon.of("resources/svg/desktop.svg"));
        btn1.setGraphic(Icon.of("resources/svg/homeFolder.svg"));
        btn5.setGraphic(Icon.of("resources/svg/show.svg"));

        toolBar.getItems().addAll(btn1, btn2, btn3, btn4, btn5);

        dialogPane.setHeader(toolBar);
        currentSelectedPath = new ComboBox<>();
        currentSelectedPath.setEditable(true);

        btn5.setOnAction(event -> currentSelectedPath.setVisible(!currentSelectedPath.isVisible()));

        fileTreeView = new TreeView<>();
        VBox vBox = new VBox(currentSelectedPath, fileTreeView);
        currentSelectedPath.prefWidthProperty().bind(vBox.heightProperty());
        dialogPane.setContent(vBox);
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        setResizable(true);

        TreeItem<File> root = new TreeItem<>();
        fileTreeView.setRoot(root);
        fileTreeView.setShowRoot(false);

        fileTreeView.setCellFactory(param -> {
            TextFieldTreeCell<File> cell = new TextFieldTreeCell<>(new StringConverter<File>() {
                @Override
                public String toString(File object) {
                    return object.getName();
                }

                @Override
                public File fromString(String string) {
                    return null;
                }
            });

            cell.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue && currentSelectedPath.isVisible()) {
                    ReadOnlyProperty<?> property = (ReadOnlyProperty<?>) observable;
                    @SuppressWarnings("unchecked") TreeCell<File> fileTreeCell = (TreeCell<File>) property.getBean();
                    String absolutePath = fileTreeCell.getItem().getAbsolutePath();
                    currentSelectedPath.getItems().add(absolutePath);
                    currentSelectedPath.getSelectionModel().selectLast();
                }
            });
            return cell;
        });

        setOnShowing(event -> {
            if (this.getInitialDirectory() != null) {
                fillRecursivly(root, this.getInitialDirectory());
            }
        });
    }

    private static final Map<String, String> fileIconMapping = new HashMap<>();

    static {
        fileIconMapping.put(".html", "resources/svg/html.svg");
        fileIconMapping.put(".java", "resources/svg/java.svg");
        fileIconMapping.put(".xml", "resources/svg/xml.svg");
        fileIconMapping.put(".properties", "resources/svg/properties.svg");
        fileIconMapping.put(".class", "resources/svg/javaClass.svg");
        fileIconMapping.put(".jar", "resources/svg/ppJar.svg");
        fileIconMapping.put(".md", "resources/svg/MarkdownPlugin.svg");
        fileIconMapping.put(".MD", "resources/svg/MarkdownPlugin.svg");
        fileIconMapping.put(".MARKDOWN", "resources/svg/MarkdownPlugin.svg");
        fileIconMapping.put(".txt", "resources/svg/text.svg");
        fileIconMapping.put(".fxml", "resources/svg/xml.svg");
        fileIconMapping.put(".css", "resources/svg/css.svg");
        fileIconMapping.put(".png", "resources/svg/imageGutter.svg");
        fileIconMapping.put(".jpg", "resources/svg/imageGutter.svg");
        fileIconMapping.put(".jpeg", "resources/svg/imageGutter.svg");
        fileIconMapping.put(".webp", "resources/svg/imageGutter.svg");
    }

    public void fillRecursivly(TreeItem<File> parent, File dir) {
        System.out.println(dir.getAbsolutePath());
        final File[] files = dir.listFiles();
        if (files != null) {
            for (File item : files) {
                if (item.isFile()) {
                    final TreeItem<File> fileTreeItem = new TreeItem<>(item);

                    final String fileExtension = FileUtils.getFileExtension(item);

                    final String path = fileIconMapping.get("." + fileExtension);
                    if (path == null) {
                        fileTreeItem.setGraphic(Icon.of("resources/svg/ignore_file.svg"));
                    } else {
                        fileTreeItem.setGraphic(Icon.of(path));
                    }
                    parent.getChildren().add(fileTreeItem);
                } else {
                    TreeItem<File> currentDirItem = new TreeItem<>(item);
                    Node graphicUnExpanded = Icon.of("resources/icon/folder.svg");
                    Node graphicExpanded = Icon.of("resources/icon/openRecentProject.svg");
                    currentDirItem.setGraphic(graphicUnExpanded);
                    currentDirItem.expandedProperty()
                            .addListener((observable, oldValue, newValue) -> currentDirItem.setGraphic(newValue ? graphicExpanded : graphicUnExpanded));

                    parent.getChildren().add(currentDirItem);
                    fillRecursivly(currentDirItem, item);
                }
            }
        }
    }

    public final List<File> getSelectedFiles() {
        return fileTreeView.getSelectionModel()
                .getSelectedItems()
                .stream()
                .map(TreeItem::getValue)
                .collect(Collectors.toList());
    }

    public final File getSelectedFile() {
        return fileTreeView.getSelectionModel().getSelectedItem().getValue();
    }
}
