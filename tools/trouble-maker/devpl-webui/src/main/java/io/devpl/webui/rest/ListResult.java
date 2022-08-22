package io.devpl.webui.rest;

import java.util.List;

public class ListResult<T> extends ResultTemplate.Builder {

    private int pageIndex = 0;

    private int pageSize = -1;

    private List<T> data;

    public static <T> ListResult<T> builder() {
        return new ListResult<>();
    }

    public ListResult<T> status(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public ListResult<T> pageInfo(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        return this;
    }

    public ListResult<T> description(String description) {
        this.description = description;
        return this;
    }

    public ListResult<T> data(List<T> data) {
        this.data = data;
        return this;
    }

    @Override
    public ResultTemplate build() {
        ListResultTemplate template = new ListResultTemplate();
        template.code = this.code;
        template.message = this.message;
        template.setData(this.data);
        return template;
    }
}
