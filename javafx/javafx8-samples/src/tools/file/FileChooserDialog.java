package tools.file;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import resources.Icon;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @see javafx.scene.control.ChoiceDialog
 * @see javafx.stage.FileChooser
 */
public class FileChooserDialog extends Dialog<ButtonType> {

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
        DialogPane dialogPane = getDialogPane();

        ToolBar toolBar = new ToolBar();
        Button btn1 = new Button("Hide path");
        Button btn2 = new Button("Button 2");
        Button btn3 = new Button("Button 3");
        Button btn4 = new Button("Button 4");
        toolBar.getItems().addAll(btn1, btn2, btn3, btn4);

        dialogPane.setHeader(toolBar);
        currentSelectedPath = new ComboBox<>();
        currentSelectedPath.setEditable(true);

        btn1.setOnAction(event -> currentSelectedPath.setVisible(!currentSelectedPath.isVisible()));

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
                    TreeCell<File> fileTreeCell = (TreeCell<File>) property.getBean();
                    String absolutePath = fileTreeCell.getItem().getAbsolutePath();
                    currentSelectedPath.getItems().add(absolutePath);
                    currentSelectedPath.getSelectionModel().selectLast();
                }
            });
            return cell;
        });

        CompletableFuture.runAsync(() -> {
            fillRecursivly(root, new File("D:\\"));
        });
    }

    public void fillRecursivly(TreeItem<File> parent, File dir) {
        final File[] files = dir.listFiles();
        if (files != null) {
            for (File item : files) {
                if (item.isFile()) {
                    parent.getChildren().add(new TreeItem<>(item));
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
