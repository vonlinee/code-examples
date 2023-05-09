package components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

/**
 * 字段信息
 */
@TableViewModel
public class FieldInfo {

    @TableViewColumn(name = "修饰符")
    private final StringProperty modifier = new SimpleStringProperty();

    @TableViewColumn(name = "数据类型")
    private final ObjectProperty<Date> dataType = new SimpleObjectProperty<>();

    @TableViewColumn(name = "名称")
    private final StringProperty name = new SimpleStringProperty();

    @TableViewColumn(name = "备注")
    private final StringProperty remarks = new SimpleStringProperty();

    public String getModifier() {
        return modifier.get();
    }

    public StringProperty modifierProperty() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier.set(modifier);
    }

    public Date getDataType() {
        return dataType.get();
    }

    public ObjectProperty<Date> dataTypeProperty() {
        return dataType;
    }

    public void setDataType(Date dataType) {
        this.dataType.set(dataType);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getRemarks() {
        return remarks.get();
    }

    public StringProperty remarksProperty() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }
}
