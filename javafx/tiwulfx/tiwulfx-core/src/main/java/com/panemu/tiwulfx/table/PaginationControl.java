package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TiwulFXUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

/**
 * 分页控制组件
 */
public class PaginationControl extends HBox implements EventHandler<ActionEvent> {

    public TableControl<?> tableControl;

    public Button btnFirstPage;
    public Button btnLastPage;
    public Button btnNextPage;
    public Button btnPrevPage;
    public ComboBox<Integer> cmbPage;

    public <R> PaginationControl(TableControl<R> tableControl) {
        this.setAlignment(Pos.CENTER);

        this.tableControl = tableControl;

        /**
         * 分页相关控件
         */
        Button btnFirstPage = new Button();
        btnFirstPage.setGraphic(TiwulFXUtil.getGraphicFactory().createPageFirstGraphic());
        btnFirstPage.setOnAction(this);
        btnFirstPage.setDisable(true);
        btnFirstPage.setFocusTraversable(false);
        btnFirstPage.getStyleClass().addAll("pill-button", "pill-button-left");

        Button btnPrevPage = new Button();
        btnPrevPage.setGraphic(TiwulFXUtil.getGraphicFactory().createPagePrevGraphic());
        btnPrevPage.setOnAction(this);
        btnPrevPage.setDisable(true);
        btnPrevPage.setFocusTraversable(false);
        btnPrevPage.getStyleClass().addAll("pill-button", "pill-button-center");

        Button btnNextPage = new Button();
        btnNextPage.setGraphic(TiwulFXUtil.getGraphicFactory().createPageNextGraphic());
        btnNextPage.setOnAction(this);
        btnNextPage.setDisable(true);
        btnNextPage.setFocusTraversable(false);
        btnNextPage.getStyleClass().addAll("pill-button", "pill-button-center");

        Button btnLastPage = new Button();
        btnLastPage.setGraphic(TiwulFXUtil.getGraphicFactory().createPageLastGraphic());
        btnLastPage.setOnAction(this);
        btnLastPage.setDisable(true);
        btnLastPage.setFocusTraversable(false);
        btnLastPage.getStyleClass().addAll("pill-button", "pill-button-right");

        ComboBox<Integer> cmbPage = new ComboBox<>();
        cmbPage.setEditable(true);
        cmbPage.setOnAction(this);
        cmbPage.setFocusTraversable(false);
        cmbPage.setDisable(true);
        cmbPage.getStyleClass().addAll("combo-page");
        cmbPage.setPrefWidth(75);

        TiwulFXUtil.setToolTip(btnFirstPage, "go.to.first.page");
        TiwulFXUtil.setToolTip(btnLastPage, "go.to.last.page");
        TiwulFXUtil.setToolTip(btnNextPage, "go.to.next.page");
        TiwulFXUtil.setToolTip(btnPrevPage, "go.to.prev.page");

        this.btnFirstPage = btnFirstPage;
        this.btnNextPage = btnNextPage;
        this.btnLastPage = btnLastPage;
        this.btnPrevPage = btnPrevPage;
        this.cmbPage = cmbPage;

        this.getChildren().addAll(btnFirstPage, btnPrevPage, cmbPage, btnNextPage, btnLastPage);
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == btnFirstPage) {
            tableControl.reloadFirstPage();
        } else if (event.getSource() == btnPrevPage) {
            cmbPage.getSelectionModel().selectPrevious();
        } else if (event.getSource() == btnNextPage) {
            cmbPage.getSelectionModel().selectNext();
        } else if (event.getSource() == btnLastPage) {
            cmbPage.getSelectionModel().selectLast();
        } else if (event.getSource() == cmbPage) {
            if (cmbPage.getValue() != null) {
                // since the combobox is editable, it might have String value
                // enen though the generic is Integer, it still can be a String
                int pageNum = Integer.parseInt(String.valueOf(cmbPage.getValue()));
                tableControl.pageChangeFired(0, pageNum);
            }
        }
    }

    /**
     * 切换按钮
     * @param moreRows  是否有更多行
     * @param firstPage 是否是第一页
     */
    public void toggleButtons(boolean moreRows, boolean firstPage) {
        btnFirstPage.setDisable(firstPage);
        btnPrevPage.setDisable(firstPage);
        btnNextPage.setDisable(!moreRows);
        btnLastPage.setDisable(!moreRows);
    }

    public void updatePage(long maxPageNum, int selectIndex) {
        cmbPage.setDisable(maxPageNum == 0);
        cmbPage.getItems().clear();
        for (int i = 1; i <= maxPageNum; i++) {
            cmbPage.getItems().add(i);
        }
        cmbPage.getSelectionModel().select(selectIndex);
    }
}