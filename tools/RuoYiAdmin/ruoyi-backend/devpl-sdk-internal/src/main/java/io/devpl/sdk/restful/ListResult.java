package io.devpl.sdk.restful;

import io.devpl.sdk.DelegatedList;

import java.util.List;

public class ListResult<E> extends ResultfulResultTemplate implements ListRBuilder<E>, DelegatedList<E> {

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
        if (data == null) this.data = List.of();
        if (pageInfo == null) this.pageInfo = PageInfo.UNKNOWN;
        return this;
    }

    @Override
    public ListRBuilder<E> setCode(int code) {
        this.code = code;
        return this;
    }

    @Override
    public ListRBuilder<E> setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public ListRBuilder<E> setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
        return this;
    }

    @Override
    public ListRBuilder<E> setToast(String toastMessage) {
        this.toast = toastMessage;
        return this;
    }

    @Override
    public ListRBuilder<E> setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
        return this;
    }

    @Override
    public ListRBuilder<E> setData(List<E> data) {
        this.data = data;
        return this;
    }

    @Override
    public ListRBuilder<E> setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        return this;
    }

    @Override
    public List<E> delegator() {
        return this.data == null ? List.of() : this.data;
    }
}
