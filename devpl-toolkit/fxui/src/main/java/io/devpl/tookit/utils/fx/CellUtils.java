package io.devpl.tookit.utils.fx;

import javafx.scene.control.TableView;

public class CellUtils {

    /**
     * 删除TableView的选择项，JavaFX提供的API有点问题
     * https://stackoverflow.com/questions/18700430/issue-with-removing-multiple-rows-at-once-from-javafx-tableview
     *
     * @param tableView TableView
     * @param <S>       数据类型
     */
    public static <S> void removeSelected(TableView<S> tableView) {
        final TableView.TableViewSelectionModel<S> selectionModel = tableView.getSelectionModel();
        tableView.getItems().removeAll(selectionModel.getSelectedItems());
        selectionModel.clearSelection(); // 清除选择状态
    }
}
