package com.panemu.tiwulfx.table;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import org.jetbrains.annotations.Nullable;

/**
 * TableView 工具类
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public final class TableViewHelper {

    private TableViewHelper() {
    }

    static int TREE_VIEW_HBOX_GRAPHIC_PADDING = 3;

    /**
     * 根据值发生改变的单元格获取正在编辑的行对应的数据项
     * @param cellEditEvent 单元格编辑事件
     * @return {@link S} 为null表示编辑事件无效，不满足判断条件
     */
    @Nullable
    public static <S, T> S getEditingItem(TableColumn.CellEditEvent<S, T> cellEditEvent) {
        TablePosition<S, T> editPosition = cellEditEvent.getTablePosition();
        TableView<S> tableView = cellEditEvent.getTableView();
        if (editPosition == null || tableView == null) {
            return null;
        }
        final int editRow = editPosition.getRow();
        if (editRow >= tableView.getItems().size()) {
            return null;
        }
        final T newValue = cellEditEvent.getNewValue();
        final T oldValue = cellEditEvent.getOldValue();
        if ((newValue == null && oldValue == null) || (newValue != null && newValue.equals(oldValue))) {
            return null;
        }
        return tableView.getItems().get(editRow);
    }

    /**
     * 单元格编辑事件是否值发生改变
     * @param cellEditEvent 单元格编辑事件
     * @param <S>           表格数据类型
     * @param <T>           单元格数据类型
     * @return 是否值发生改变
     */
    public static <S, T> boolean isValueChanged(TableColumn.CellEditEvent<S, T> cellEditEvent) {
        final T newValue = cellEditEvent.getNewValue();
        final T oldValue = cellEditEvent.getOldValue();
        return (newValue != null || oldValue != null) && (newValue == null || !newValue.equals(oldValue));
    }

    /**
     * 获取单元格文本
     * @param cell      单元格
     * @param converter 转换器
     * @param <T>       Cell数据类型
     * @return 字符串
     * @see javafx.scene.control.cell.CellUtils
     */
    public static <T> String getItemText(Cell<T> cell, StringConverter<T> converter) {
        return converter == null ? cell.getItem() == null ? "" : cell.getItem()
                .toString() : converter.toString(cell.getItem());
    }

    /* *************************************************************************
     *                                                                         *
     * Private fields                                                          *
     *                                                                         *
     **************************************************************************/

    private static final StringConverter<?> defaultStringConverter = new StringConverter<>() {
        @Override
        public String toString(Object t) {
            return t == null ? null : t.toString();
        }

        @Override
        public Object fromString(String string) {
            return string;
        }
    };

    private final static StringConverter<?> defaultTreeItemStringConverter = new StringConverter<TreeItem<?>>() {
        @Override
        public String toString(TreeItem<?> treeItem) {
            return (treeItem == null || treeItem.getValue() == null) ? "" : treeItem.getValue().toString();
        }

        @Override
        public TreeItem<?> fromString(String string) {
            return new TreeItem<>(string);
        }
    };

    /* *************************************************************************
     *                                                                         *
     * General convenience                                                     *
     *                                                                         *
     **************************************************************************/

    /*
     * Simple method to provide a StringConverter implementation in various cell
     * implementations.
     */
    @SuppressWarnings("unchecked")
    static <T> StringConverter<T> defaultStringConverter() {
        return (StringConverter<T>) defaultStringConverter;
    }

    /*
     * Simple method to provide a TreeItem-specific StringConverter
     * implementation in various cell implementations.
     */

    static <T> StringConverter<TreeItem<T>> defaultTreeItemStringConverter() {
        return (StringConverter<TreeItem<T>>) defaultTreeItemStringConverter;
    }

    static Node getGraphic(TreeItem<?> treeItem) {
        return treeItem == null ? null : treeItem.getGraphic();
    }

    /* *************************************************************************
     *                                                                         *
     * ChoiceBox convenience                                                   *
     *                                                                         *
     **************************************************************************/

    static <T> void updateItem(final Cell<T> cell, final StringConverter<T> converter, final ChoiceBox<T> choiceBox) {
        updateItem(cell, converter, null, null, choiceBox);
    }

    static <T> void updateItem(final Cell<T> cell, final StringConverter<T> converter, final HBox hbox, final Node graphic, final ChoiceBox<T> choiceBox) {
        if (cell.isEmpty()) {
            cell.setText(null);
            cell.setGraphic(null);
        } else {
            if (cell.isEditing()) {
                if (choiceBox != null) {
                    choiceBox.getSelectionModel().select(cell.getItem());
                }
                cell.setText(null);
                if (graphic != null) {
                    hbox.getChildren().setAll(graphic, choiceBox);
                    cell.setGraphic(hbox);
                } else {
                    cell.setGraphic(choiceBox);
                }
            } else {
                cell.setText(getItemText(cell, converter));
                cell.setGraphic(graphic);
            }
        }
    }

    static <T> ChoiceBox<T> createChoiceBox(final Cell<T> cell, final ObservableList<T> items, final ObjectProperty<StringConverter<T>> converter) {
        ChoiceBox<T> choiceBox = new ChoiceBox<T>(items);
        choiceBox.setMaxWidth(Double.MAX_VALUE);
        choiceBox.converterProperty().bind(converter);
        choiceBox.showingProperty().addListener(o -> {
            if (!choiceBox.isShowing()) {
                cell.commitEdit(choiceBox.getSelectionModel().getSelectedItem());
            }
        });
        return choiceBox;
    }

    /* *************************************************************************
     *                                                                         *
     * TextField convenience                                                   *
     *                                                                         *
     **************************************************************************/

    static <T> void updateItem(final Cell<T> cell, final StringConverter<T> converter, final TextField textField) {
        updateItem(cell, converter, null, null, textField);
    }

    static <T> void updateItem(final Cell<T> cell, final StringConverter<T> converter, final HBox hbox, final Node graphic, final TextField textField) {
        if (cell.isEmpty()) {
            cell.setText(null);
            cell.setGraphic(null);
        } else {
            if (cell.isEditing()) {
                if (textField != null) {
                    textField.setText(getItemText(cell, converter));
                }
                cell.setText(null);

                if (graphic != null) {
                    hbox.getChildren().setAll(graphic, textField);
                    cell.setGraphic(hbox);
                } else {
                    cell.setGraphic(textField);
                }
            } else {
                cell.setText(getItemText(cell, converter));
                cell.setGraphic(graphic);
            }
        }
    }

    static <T> void startEdit(final Cell<T> cell, final StringConverter<T> converter, final HBox hbox, final Node graphic, final TextField textField) {
        if (textField != null) {
            textField.setText(getItemText(cell, converter));
        }
        cell.setText(null);

        if (graphic != null) {
            hbox.getChildren().setAll(graphic, textField);
            cell.setGraphic(hbox);
        } else {
            cell.setGraphic(textField);
        }

        if (textField != null) {
            textField.selectAll();

            // requesting focus so that key input can immediately go into the
            // TextField (see RT-28132)
            textField.requestFocus();
        }
    }

    static <T> void cancelEdit(Cell<T> cell, final StringConverter<T> converter, Node graphic) {
        cell.setText(getItemText(cell, converter));
        cell.setGraphic(graphic);
    }

    static <T> TextField createTextField(final Cell<T> cell, final StringConverter<T> converter) {
        final TextField textField = new TextField(getItemText(cell, converter));

        // Use onAction here rather than onKeyReleased (with check for Enter),
        // as otherwise we encounter RT-34685
        textField.setOnAction(event -> {
            if (converter == null) {
                throw new IllegalStateException("Attempting to convert text input into Object, but provided " + "StringConverter is null. Be sure to set a StringConverter " + "in your cell factory.");
            }
            cell.commitEdit(converter.fromString(textField.getText()));
            event.consume();
        });
        textField.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ESCAPE) {
                cell.cancelEdit();
                t.consume();
            }
        });
        return textField;
    }



    /* *************************************************************************
     *                                                                         *
     * ComboBox convenience                                                   *
     *                                                                         *
     **************************************************************************/

    static <T> void updateItem(Cell<T> cell, StringConverter<T> converter, ComboBox<T> comboBox) {
        updateItem(cell, converter, null, null, comboBox);
    }

    static <T> void updateItem(final Cell<T> cell, final StringConverter<T> converter, final HBox hbox, final Node graphic, final ComboBox<T> comboBox) {
        if (cell.isEmpty()) {
            cell.setText(null);
            cell.setGraphic(null);
        } else {
            if (cell.isEditing()) {
                if (comboBox != null) {
                    comboBox.getSelectionModel().select(cell.getItem());
                }
                cell.setText(null);

                if (graphic != null) {
                    hbox.getChildren().setAll(graphic, comboBox);
                    cell.setGraphic(hbox);
                } else {
                    cell.setGraphic(comboBox);
                }
            } else {
                cell.setText(getItemText(cell, converter));
                cell.setGraphic(graphic);
            }
        }
    }

    ;

    static <T> ComboBox<T> createComboBox(final Cell<T> cell, final ObservableList<T> items, final ObjectProperty<StringConverter<T>> converter) {
        ComboBox<T> comboBox = new ComboBox<T>(items);
        comboBox.converterProperty().bind(converter);
        comboBox.setMaxWidth(Double.MAX_VALUE);

        // setup listeners to properly commit any changes back into the data model.
        // First listener attempts to commit or cancel when the ENTER or ESC keys are released.
        // This is applicable in cases where the ComboBox is editable, and the user has
        // typed some input, and also when the ComboBox popup is showing.
        comboBox.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                tryComboBoxCommit(comboBox, cell);
            } else if (e.getCode() == KeyCode.ESCAPE) {
                cell.cancelEdit();
            }
        });

        // Second listener attempts to commit when the user is in the editor of
        // the ComboBox, and moves focus away.
        comboBox.getEditor().focusedProperty().addListener(o -> {
            if (!comboBox.isFocused()) {
                tryComboBoxCommit(comboBox, cell);
            }
        });

        // Third listener makes an assumption about the skin being used, and attempts to add
        // a listener to the ListView within it, such that when the user mouse clicks on a
        // on an item, that is immediately committed and the cell exits the editing mode.
        boolean success = listenToComboBoxSkin(comboBox, cell);
        if (!success) {
            comboBox.skinProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    boolean successInListener = listenToComboBoxSkin(comboBox, cell);
                    if (successInListener) {
                        comboBox.skinProperty().removeListener(this);
                    }
                }
            });
        }

        return comboBox;
    }

    private static <T> void tryComboBoxCommit(ComboBox<T> comboBox, Cell<T> cell) {
        StringConverter<T> sc = comboBox.getConverter();
        if (comboBox.isEditable() && sc != null) {
            T value = sc.fromString(comboBox.getEditor().getText());
            cell.commitEdit(value);
        } else {
            cell.commitEdit(comboBox.getValue());
        }
    }

    private static <T> boolean listenToComboBoxSkin(final ComboBox<T> comboBox, final Cell<T> cell) {
        Skin<?> skin = comboBox.getSkin();
        if (skin instanceof ComboBoxListViewSkin cbSkin) {
            Node popupContent = cbSkin.getPopupContent();
            if (popupContent instanceof ListView) {
                popupContent.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> cell.commitEdit(comboBox.getValue()));
                return true;
            }
        }
        return false;
    }
}