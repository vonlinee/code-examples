package io.devpl.sdk.internal.rest;

import java.io.Serializable;

@SuppressWarnings("unchecked")
public class ListResult<E> extends Result implements ResultBuilder, Serializable {

    private static final long serialVersionUID = -497037305528002807L;

    private PageInfo pageInfo;

    public ListResult(E placeholder) {
        super();
    }

    public static <E> ResultBuilder builder(E type) {
        return new ListResult<>(type);
    }

    @Override
    public <B extends ResultBuilder> B code(int code) {
        this.setCode(code);
        return (B) this;
    }

    @Override
    public <B extends ResultBuilder> B message(String message) {
        this.setMessage(message);
        return (B) this;
    }

    @Override
    public <B extends ResultBuilder> B stacktrace(String stacktrace) {
        this.setStacktrace(stacktrace);
        return (B) this;
    }

    @Override
    public <B extends ResultBuilder> B description(String description) {
        this.setDescription(description);
        return (B) this;
    }

    @Override
    public <B extends ResultBuilder> B data(Object data) {
        this.setData(data);
        return (B) this;
    }

    @Override
    public <B extends ResultBuilder> B pageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        return (B) this;
    }

    @Override
    public <R extends Result> R build() {
        return (R) this;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("timestamp=").append(getTimestamp()).append(",");
        sb.append("data=").append(getData()).append(",");
        sb.append("message=").append(getMessage()).append(",");
        sb.append("stacktrace=").append(getStacktrace()).append(",");
        sb.append("description=").append(getDescription()).append(",");
        return sb.toString();
    }
}

