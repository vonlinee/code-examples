package io.devpl.toolkit.fxui.controller;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.utils.StageHelper;
import io.devpl.toolkit.fxui.model.MetaField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignI;
import org.kordamp.ikonli.materialdesign2.MaterialDesignW;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 字段元信息管理
 */
@FxmlLocation(location = "static/fxml/MetaFields.fxml", title = "字段元信息管理")
public class MetaFieldManageController extends FxmlView {

    @FXML
    public TableView<MetaField> tbvMetaFields;
    @FXML
    public TableColumn<MetaField, Object> tblcOperations;
    @FXML
    public TableColumn<MetaField, String> tblcFieldName;
    @FXML
    public TableColumn<MetaField, String> tblcFieldDescription;
    public Button btnImport;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnImport.setGraphic(new FontIcon(MaterialDesignI.IMPORT));
        btnImport.setTooltip(new Tooltip("Import Fields"));

        tbvMetaFields.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblcOperations.setCellFactory(param -> {
            FontIcon fontIcon = new FontIcon();
            fontIcon.setIconCode(MaterialDesignW.WINDOW_CLOSE);
            TableCell<MetaField, Object> cell = new TableCell<>() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(fontIcon);
                    }
                }
            };
            cell.setAlignment(Pos.CENTER);
            fontIcon.setOnMouseClicked(event -> {
                ObservableList<Integer> selectedIndices = tbvMetaFields.getSelectionModel().getSelectedIndices();
                System.out.println(selectedIndices);
                for (Integer selectedIndex : selectedIndices) {
                    if (selectedIndex != null) {
                        tbvMetaFields.getItems().remove(selectedIndex.intValue());
                    }
                }
            });
            return cell;
        });

        tblcFieldName.setCellValueFactory(new PropertyValueFactory<>("fieldName"));
        tblcFieldDescription.setCellValueFactory(new PropertyValueFactory<>("fieldDescription"));

        MetaField metaField = new MetaField();
        metaField.setFieldName("name");
        metaField.setFieldDescription("描述文本");
        tbvMetaFields.getItems().add(metaField);
    }

    @FXML
    public void showFieldImportPane(ActionEvent actionEvent) {
        StageHelper.show(MetaFieldImportController.class);
    }
}
