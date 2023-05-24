package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TableCriteria.Condition;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxColumn<R> extends BaseColumn<R, Boolean> {

    private CheckBox searchInputControl;
    private SearchMenuItemBase<Boolean> searchMenuItem;
    private final Boolean[] valueOrder = new Boolean[]{Boolean.TRUE, Boolean.FALSE, null};
    private String trueLabel = TiwulFXUtil.getLiteral("label.true");
    private String falseLabel = TiwulFXUtil.getLiteral("label.false");

    public CheckBoxColumn() {
        this("");
    }

    public CheckBoxColumn(String propertyName) {
        this(propertyName, 100);
    }

    public CheckBoxColumn(String propertyName, double prefWidth) {
        super(propertyName, prefWidth);
        setRenderer();
        setStringConverter(stringConverter);
    }

    private void setRenderer() {

        Callback<TableColumn<R, Boolean>, TableCell<R, Boolean>> cb2 = new Callback<TableColumn<R, Boolean>, TableCell<R, Boolean>>() {
            @Override
            public TableCell<R, Boolean> call(TableColumn<R, Boolean> list) {
                return new CheckBoxTableCell<R>(CheckBoxColumn.this);

            }
        };

        setCellFactory(cb2);
    }

    @Override
    public MenuItem getSearchMenuItem() {
        if (searchMenuItem == null) {
            searchInputControl = new CheckBox();
            searchInputControl.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    searchInputControl.setText(stringConverter.toString(searchInputControl.isSelected()));
                }
            });

            searchMenuItem = new SearchMenuItemBase<Boolean>(this) {
                @Override
                protected Node getInputControl() {
                    return searchInputControl;
                }

                @Override
                protected List<Condition> getOperators() {
                    List<Condition> lst = new ArrayList<>();
                    lst.add(Condition.eq);
                    lst.add(Condition.is_null);
                    lst.add(Condition.is_not_null);
                    return lst;
                }

                @Override
                protected Boolean getValue() {
                    return searchInputControl.isSelected();
                }
            };
        }
        if (getDefaultSearchValue() != null) {
            searchInputControl.setText(stringConverter.toString(getDefaultSearchValue()));
            searchInputControl.setSelected(getDefaultSearchValue());
            if (searchMenuItem.getSelectedOperator() == Condition.is_null) {
                searchMenuItem.setSelectedOperator(Condition.eq);
            }
        } else {
            searchMenuItem.setSelectedOperator(Condition.is_null);
        }
        return searchMenuItem;
    }

    private final StringConverter<Boolean> stringConverter = new StringConverter<>() {
        @Override
        public String toString(Boolean value) {
            if (value == null) {
                return getNullLabel();
            }
            return value ? trueLabel : falseLabel;

        }

        @Override
        public Boolean fromString(String string) {
            if (string == null || string.equals(getNullLabel())) {
                return null;
            } else if (string.equals(trueLabel)) {
                return true;
            } else if (string.equals(falseLabel)) {
                return false;
            } else {
                return null;
            }
        }
    };

    public void setTrueLabel(String trueLabel) {
        this.trueLabel = trueLabel;
    }

    public void setFalseLabel(String falseLabel) {
        this.falseLabel = falseLabel;
    }

    public String getTrueLabel() {
        return trueLabel;
    }

    public String getFalseLabel() {
        return falseLabel;
    }

    public void setLabel(String trueLabel, String falseLabel, String nullLabel) {
        this.trueLabel = trueLabel;
        this.falseLabel = falseLabel;
        super.setNullLabel(nullLabel);
    }
}
