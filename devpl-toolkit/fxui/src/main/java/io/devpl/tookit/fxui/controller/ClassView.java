package io.devpl.tookit.fxui.controller;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.tookit.fxui.view.filestructure.FieldType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@FxmlLocation(location = "layout/class.fxml")
public class ClassView extends FxmlView {

    @FXML
    public TableView<FieldDeclaration> tbvFieldInfo;
    @FXML
    public Button btnAddOne;

    @FXML
    TableColumn<FieldDeclaration, String> tblcFieldName;
    @FXML
    TableColumn<FieldDeclaration, String> tblcFieldType;
    @FXML
    TableColumn<FieldDeclaration, String> tblcFieldComment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblcFieldName.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FieldDeclaration, String> param) {
                return new SimpleStringProperty(getFieldName(param.getValue()));
            }
        });
        tblcFieldName.setCellFactory(new Callback<TableColumn<FieldDeclaration, String>, TableCell<FieldDeclaration, String>>() {
            @Override
            public TableCell<FieldDeclaration, String> call(TableColumn<FieldDeclaration, String> param) {
                TextFieldTableCell<FieldDeclaration, String> tableCell = new TextFieldTableCell<>(new DefaultStringConverter());
                tableCell.setAlignment(Pos.CENTER);
                return tableCell;
            }
        });
        tblcFieldName.setEditable(true);
        tblcFieldType.setCellValueFactory(param -> new SimpleStringProperty(getFieldType(param.getValue())));
        tblcFieldType.setEditable(true);
        tblcFieldType.setCellFactory(param -> {
            ChoiceBoxTableCell<FieldDeclaration, String> tableCell = new ChoiceBoxTableCell<>(new StringConverter<String>() {
                @Override
                public String toString(String object) {
                    return object;
                }

                @Override
                public String fromString(String string) {
                    return string;
                }
            });
            tableCell.getItems().addAll(FieldType.getAllTypeNames());
            return tableCell;
        });

        btnAddOne.setOnAction(event -> {
            final FieldDeclaration fd = new FieldDeclaration();
            tbvFieldInfo.getItems().add(fd);
        });
    }

    private String getFieldName(FieldDeclaration fieldDeclaration) {
        final NodeList<VariableDeclarator> variables = fieldDeclaration.getVariables();
        if (variables.size() > 0) {
            VariableDeclarator variableDeclarator = variables.get(0);
            final SimpleName name = variableDeclarator.getName();
            return name.getIdentifier();
        }
        return "UNKNOWN";
    }

    public String getFieldType(FieldDeclaration fieldDeclaration) {
        final Optional<VariableDeclarator> first = fieldDeclaration.getVariables().getFirst();
        if (first.isPresent()) {
            return first.get().toString();
        }
        return FieldType.STRING.name();
    }
}
