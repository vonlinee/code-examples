package io.devpl.sdk.rest;

import io.devpl.sdk.DelegationList;

import java.util.List;

/**
 * 携带多条数据的列表
 * @param <E>
 */
public class ListResult<E> extends RestfulResultTemplate implements ListRBuilder<E>, DelegationList<E> {

    private List<E> data;

    private PageInfo pageInfo;

    public ListResult() {
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
    public ListRBuilder<E> setThrowable(Throwable throwable) {
        this.throwable = throwable;
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

    @Override
    protected void toJSONString(StringBuilder result) {

    }
}
