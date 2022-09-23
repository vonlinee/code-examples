package io.devpl.sdk.internal.rest;

public interface ListResultBuilder<T> {

    void initialCapacity(int initialCapacity);

    void pageInfo(PageInfo pageInfo);
}
