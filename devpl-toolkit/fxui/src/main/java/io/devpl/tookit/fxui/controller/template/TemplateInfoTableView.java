package io.devpl.tookit.fxui.controller.template;

import io.devpl.tookit.fxui.editor.CodeMirrorEditor;
import io.devpl.tookit.fxui.editor.LanguageMode;
import io.devpl.tookit.fxui.event.EventUtils;
import io.devpl.tookit.fxui.model.TemplateInfo;
import io.devpl.tookit.utils.DBHelper;
import io.devpl.tookit.utils.DBUtils;
import io.devpl.tookit.utils.FileUtils;
import io.devpl.tookit.utils.StringUtils;
import io.fxtras.Alerts;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 模板信息表
 */
public class TemplateInfoTableView extends TableView<TemplateInfo> {

    private final CodeMirrorEditor editor = CodeMirrorEditor.newInstance(LanguageMode.VELOCITY);

    // TODO 做成弹窗
    Stage stage = new Stage();

    public TemplateInfoTableView() {
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<TemplateInfo, String> templateIdColumn = new TableColumn<>("模板ID");
        templateIdColumn.setCellValueFactory(new PropertyValueFactory<>("templateId"));
        templateIdColumn.setMaxWidth(230.0);
        templateIdColumn.setMinWidth(230.0);
        TableColumn<TemplateInfo, String> templateNameColumn = new TableColumn<>("模板名称");
        templateNameColumn.setCellValueFactory(new PropertyValueFactory<>("templateName"));
        TableColumn<TemplateInfo, String> templatePathColumn = new TableColumn<>("模板路径");
        templatePathColumn.setCellValueFactory(new PropertyValueFactory<>("templatePath"));
        TableColumn<TemplateInfo, Boolean> builtinColumn = new TableColumn<>("是否内置");
        builtinColumn.setCellValueFactory(new PropertyValueFactory<>("builtin"));
        builtinColumn.setMaxWidth(60.0);
        builtinColumn.setMinWidth(60.0);
        TableColumn<TemplateInfo, String> remarkColumn = new TableColumn<>("备注信息");
        remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));

        getColumns().add(templateIdColumn);
        getColumns().add(templateNameColumn);
        getColumns().add(templatePathColumn);
        getColumns().add(builtinColumn);
        getColumns().add(remarkColumn);

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(new Scene(editor.getView()));

        setRowFactory(param -> {
            TableRow<TemplateInfo> row = new TableRow<>();
            row.setTextAlignment(TextAlignment.CENTER);
            row.setOnMouseClicked(event -> {
                if (!EventUtils.isPrimaryButtonDoubleClicked(event)) {
                    return;
                }
                TemplateInfo item = row.getItem();
                if (item == null) {
                    return;
                }
                System.out.println(11111);
                String content = FileUtils.readToString(new File(item.getTemplatePath()));
                if (StringUtils.hasText(content)) {
                    editor.setContent(content, true);
                    stage.show();
                }
            });
            return row;
        });

        stage.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                editor.setContent("", true);
            }
        });
    }

    public void refreshItemsData() {
        super.refresh();
        ObservableList<TemplateInfo> items = getItems();
        items.clear();
        List<TemplateInfo> list = DBHelper.selectList("select * from template_info", TemplateInfo.class);
        items.addAll(list);
    }
}
