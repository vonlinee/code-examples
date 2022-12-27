package io.devpl.toolkit.fxui.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据库表格
 */
public class DBTableListModel {

    private static final AtomicInteger idSeed = new AtomicInteger(0);

    private final IntegerProperty id = new SimpleIntegerProperty(idSeed.incrementAndGet());
    // 是否选中
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    // 表名
    private final StringProperty tableName = new SimpleStringProperty();
    // 表注释信息
    private final StringProperty tableComment = new SimpleStringProperty();

    private final ObjectProperty<LocalDateTime> createTime = new SimpleObjectProperty<>();

    public boolean isSelected() {
        return selected.get();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public String getTableName() {
        return tableName.get();
    }

    public StringProperty tableNameProperty() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName.set(tableName);
    }

    public String getTableComment() {
        return tableComment.get();
    }

    public StringProperty tableCommentProperty() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment.set(tableComment);
    }

    public LocalDateTime getCreateTime() {
        return createTime.get();
    }

    public ObjectProperty<LocalDateTime> createTimeProperty() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime.set(createTime);
    }
}
