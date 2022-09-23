package io.devpl.sdk.restful;

import java.util.List;

public class ListResult<E> extends ResultfulResultTemplate implements ListResultBuilder<E> {

    private List<E> data;

    private PageInfo pageInfo;

    ListResult() {
        super();
    }

    ListResult(int code, String message, List<E> data, String toast) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
        this.toast = toast;
    }

    @Override
    public ListResult<E> build() {
        return this;
    }

    @Override
    public ListResultBuilder<E> setCode(int code) {
        this.code = code;
        return this;
    }

    @Override
    public ListResultBuilder<E> setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public ListResultBuilder<E> setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
        return this;
    }

    @Override
    public ListResultBuilder<E> setToast(String toastMessage) {
        this.toast = toastMessage;
        return this;
    }

    @Override
    public ListResultBuilder<E> setData(List<E> data) {
        this.data = data;
        return this;
    }

    @Override
    public ListResultBuilder<E> setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        return this;
    }
}
