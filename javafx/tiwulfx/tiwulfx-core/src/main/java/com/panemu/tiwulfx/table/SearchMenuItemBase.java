package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableCriteria.Condition;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <C> the data type of column
 */
public abstract class SearchMenuItemBase<C> extends SeparatorMenuItem {

    private final ChoiceBox<Condition> operatorChoice = new ChoiceBox<>();
    private TableCriteria<C> searchCriteria;
    private final Button btnRemove = new Button(TiwulFXUtil.getLiteral("remove.filter"));

    protected abstract Node getInputControl();

    /**
     * @return unmoidifable list of all operators
     */
    protected abstract List<Condition> getOperators();

    protected abstract C getValue();

    public SearchMenuItemBase(final BaseColumn<?, C> column) {
        setHideOnClick(false);
        operatorChoice.setConverter(new StringConverter<>() {
            final Map<String, Condition> map = new HashMap<>();

            @Override
            public String toString(Condition object) {
                String literal = TiwulFXUtil.getLiteral(object.toString());
                map.put(literal, object);
                return literal;
            }

            @Override
            public Condition fromString(String string) {
                return map.get(string);
            }
        });
        operatorChoice.getItems().addAll(getOperators());
        operatorChoice.getSelectionModel().select(0);
        HBox hbox = new HBox(5);
        Node filterImage = TiwulFXUtil.getGraphicFactory().createFilterGraphic();
        Button searchButton = new Button();
        searchButton.getStyleClass().add("table-menu");
        searchButton.setGraphic(filterImage);
        searchButton.setOnAction(event -> {
            Condition operator = operatorChoice.getSelectionModel().getSelectedItem();
            searchCriteria = column.createSearchCriteria(operator, getValue());
            SearchMenuItemBase.this.getParentPopup().hide();
            column.setTableCriteria(searchCriteria);
        });
        btnRemove.setOnAction(event -> {
            SearchMenuItemBase.this.getParentPopup().hide();
            column.setTableCriteria(null);
        });
        hbox.getChildren().addAll(operatorChoice, getInputControl(), searchButton);
        hbox.setPrefHeight(-1.0);
        hbox.setAlignment(Pos.CENTER);
        final VBox vbox = new VBox(5);
        vbox.getStylesheets().add("tiwulfx.css");
        vbox.getStyleClass().add("search-menu-item");
        vbox.setOpacity(1);
        vbox.setAlignment(Pos.TOP_CENTER);
        Label lblTitle = new Label();

        lblTitle.textProperty().bind(new StringBinding() {
            {
                super.bind(column.textProperty());
            }

            @Override
            protected String computeValue() {
                return TiwulFXUtil.getLiteral("filter.param.column", column.getText());
            }
        });

        lblTitle.getStyleClass().add("searchMenuItemLabel");
        vbox.getChildren().add(lblTitle);
        vbox.getChildren().add(hbox);
        if (column.getTableCriteria() != null) {
            vbox.getChildren().add(btnRemove);
        }
        this.setContent(vbox);
        operatorChoice.getSelectionModel().selectedItemProperty().addListener(observable -> {
            ReadOnlyObjectProperty<?> obj = (ReadOnlyObjectProperty<?>) observable;
            Condition opt = (Condition) obj.get();
            getInputControl().setVisible(!opt.equals(Condition.is_null) && !opt.equals(Condition.is_not_null));
        });

        column.tableCriteriaProperty().addListener(observable -> {
            TableCriteria<?> crit = (TableCriteria<?>) ((SimpleObjectProperty<?>) observable).getValue();
            if (crit == null) {
                vbox.getChildren().remove(btnRemove);
            } else if (!vbox.getChildren().contains(btnRemove)) {
                vbox.getChildren().add(btnRemove);
            }
        });
        // fix the popup that intermittently doesn't show up
        operatorChoice.setFocusTraversable(false);
    }

    public void setSelectedOperator(Condition op) {
        operatorChoice.setValue(op);
    }

    public Condition getSelectedOperator() {
        return operatorChoice.getValue();
    }

}
