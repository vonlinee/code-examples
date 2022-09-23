package io.devpl.sdk.internal.rest;

import io.devpl.sdk.internal.DelegatedList;

import java.util.List;

public class ListResult<E> extends RestfullResult<E> implements ListResultBuilder<List<E>>, DelegatedList<E> {

    private List<E> data;
    private PageInfo pageInfo;

    @Override
    public List<E> delegator() {
        return null;
    }

    @Override
    public void initialCapacity(int initialCapacity) {

    }

    @Override
    public void pageInfo(PageInfo pageInfo) {

    }

    protected E getData() {
        return null;
    }

    @Override
    public int compareTo(RestfullResult<E> o) {
        return 0;
    }
}
