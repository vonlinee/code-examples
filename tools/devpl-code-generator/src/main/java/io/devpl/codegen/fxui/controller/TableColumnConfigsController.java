package io.devpl.codegen.fxui.controller;

import io.devpl.codegen.fxui.config.Constants;
import io.devpl.codegen.fxui.common.model.ColumnCustomConfiguration;
import io.devpl.codegen.fxui.framework.Alerts;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 定制列配置控制器
 */
public class TableColumnConfigsController extends FXControllerBase {

    @FXML
    private Label currentTableNameLabel;
    @FXML
    private TextField columnNamePrefixTextLabel;

    private TableView<ColumnCustomConfiguration> columnListView;
    private String tableName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // do nothing
    }

    @FXML
    public void cancel() {
        this.closeDialogStage();
    }

    @FXML
    public void confirm() {
        try {
            // 1. generator bean propert name
            this.genProertyNameByColumnNamePrefix();
            // close window
            this.closeDialogStage();
        } catch (Exception e) {
            log.error("confirm throw exception.", e);
            Alerts.showErrorAlert(e.getMessage());
        }
    }

    public void setColumnListView(TableView<ColumnCustomConfiguration> columnListView) {
        this.columnListView = columnListView;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
        currentTableNameLabel.setText(tableName);
    }

    private void genProertyNameByColumnNamePrefix() {
        String columnNamePrefix = this.columnNamePrefixTextLabel.getText();
        if (StringUtils.isNotBlank(columnNamePrefix)) {
            if (StringUtils.endsWith(columnNamePrefix.trim(), Constants.OR_REGEX)) {
                columnNamePrefix = StringUtils.removeEnd(columnNamePrefix.trim(), Constants.OR_REGEX);
            }
            String regex = String.format(Constants.COLUMN_PREFIX_PATTERN, columnNamePrefix);
            log.info("table:{}, column_name_prefix:{}, regex:{}", this.tableName, columnNamePrefix, regex);
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

            ObservableList<ColumnCustomConfiguration> items = columnListView.getItems();
            if (CollectionUtils.isEmpty(items)) {
                return;
            }
            items.forEach(item -> {
                String columnName = item.getColumnName();
                Matcher matcher = pattern.matcher(columnName);
                if (matcher.find()) {
                    // use first match result
                    String regexColumnName = matcher.group();
                    if (StringUtils.isNotBlank(regexColumnName)) {
                        String propertyName = JavaBeansUtil.getCamelCaseString(regexColumnName, false);
                        log.debug("table:{} column_name:{} regex_column_name:{} property_name:{}", tableName, columnName, regexColumnName, propertyName);

                        if (StringUtils.isNotBlank(propertyName)) item.setPropertyName(propertyName);
                    } else {
                        log.warn("table:{} column_name:{} regex_column_name is blank", tableName, columnName);
                    }
                } else {
                    // if not match, set property name is null
                    item.setPropertyName(null);
                }
            });
        }
    }

}