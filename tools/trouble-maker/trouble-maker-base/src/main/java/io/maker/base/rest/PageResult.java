package io.maker.base.rest;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class PageResult<T> extends Result<T> implements Serializable {

    private static final long serialVersionUID = 4134449411254581242L;

    private int pageIndex;    // 当前页
    private int pageSize;    // 每页的数量
    private int total;        // 总记录数
    private int pages;        // 总页数

    private String code;
    private String message;
    private List<T> rows;

    public PageResult() {
        super();
    }

    public PageResult(String code, String message) {
        super();
    }

    public void setRows(List<T> rows) {
        if (rows == null) {
            this.rows = new ArrayList<>(0);
        }
        this.rows = rows;
    }

    public List<T> getRows() {
        return rows;
    }

    public T getRow(int rowCount) {
        return rows.get(rowCount);
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * Builder Pattern
     * @param <T>
     * @author line
     */
    public static class Builder<T> {
        int code;
        String message;
        String timestamp;
        String errorMessage;
        List<T> rows;

        public Builder<T> description(ResultDescription description) {
            this.code = description.code;
            this.message = description.message;
            return this;
        }

        public Builder<T> description(int code, String message) {
            this.code = code;
            this.message = message;
            return this;
        }

        public Builder<T> errorMessage(String errorMsg) {
            this.errorMessage = errorMsg;
            return this;
        }

        public Builder<T> data(List<T> rows) {
            this.rows = rows;
            return this;
        }

        @SuppressWarnings({"hiding"})
        public PageResult<T> build() {
            return new PageResult<>();
        }
    }
}
