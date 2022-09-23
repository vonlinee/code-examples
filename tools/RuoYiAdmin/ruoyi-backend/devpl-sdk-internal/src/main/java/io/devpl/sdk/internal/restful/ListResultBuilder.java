package io.devpl.sdk.internal.restful;

import java.util.List;

/**
 * 通过Builder模式构造ListResult对象
 * @param <E> ListResult携带的数据类型
 */
public interface ListResultBuilder<E> extends RestfulResultBuilder<List<E>, Result<List<E>>, ResultBuilder<List<E>>> {

    default ListResultBuilder<E> pageInfo(PageInfo pageInfo) {
        return setPageInfo(pageInfo);
    }

    ListResultBuilder<E> setPageInfo(PageInfo pageInfo);
}
