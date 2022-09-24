package io.devpl.sdk.restful;

/**
 * 不对外暴露
 *
 * @param <T> Result携带的数据类型
 * @since 0.0.1
 */
public interface RBuilder<T> extends RestfulRBuilder<Result<T>, RBuilder<T>> {

    RBuilder<T> setData(T data);

    default RBuilder<T> data(T data) {
        return setData(data);
    }
}