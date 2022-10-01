package io.devpl.sdk.rest;

import java.util.List;

public interface ListRBuilder<E> extends RestfulRBuilder<ListResult<E>, ListRBuilder<E>> {

    ListRBuilder<E> setData(List<E> data);

    default ListRBuilder<E> data(List<E> data) {
        return setData(data);
    }

    default ListRBuilder<E> pageInfo(PageInfo pageInfo) {
        return setPageInfo(pageInfo);
    }

    ListRBuilder<E> setPageInfo(PageInfo pageInfo);
}
