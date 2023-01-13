package io.devpl.toolkit.fxui.controller;

import io.devpl.toolkit.framework.mvc.FxmlView;
import io.devpl.toolkit.fxui.common.Constants;
import io.devpl.toolkit.fxui.model.props.ColumnCustomConfiguration;
import io.devpl.toolkit.framework.Alerts;
import io.devpl.toolkit.framework.mvc.AbstractViewController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
@FxmlView(location = "static/fxml/tableColumnConfigs.fxml")
public class TableColumnConfigsController extends AbstractViewController {

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
    public void cancel(ActionEvent event) {
        getStage(event).close();
    }

    @FXML
    public void confirm(ActionEvent event) {
        try {
            // 1. generator bean property name
            genPropertyNameByColumnNamePrefix();
            getStage(event).close(); // close window
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

    private void genPropertyNameByColumnNamePrefix() {
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