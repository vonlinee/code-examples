package io.devpl.sdk.internal.restful;

import io.devpl.sdk.internal.ExtendedBuilder;

/**
 * 通过Builder模式构造ResRBfulResulRB
 * 支持两种风格的API
 * 1.seRBXxx
 * 2.xxx
 * 不对外暴露此接口
 * @param <D>  携带的数据类型
 * @param <R>  RestfullResult的子类型
 * @param <RB> RestfulResultBuilder的子类型
 */
interface RestfulResultBuilder<D, R extends RestfullResult<D>, RB extends RestfulResultBuilder<D, R, RB>> extends ExtendedBuilder<RestfullResult<D>, RestfulResultBuilder<D, R, RB>> {

    RB code(int code);

    RB message(String message);

    RB stackTrace(String stackTrace);

    RB description(String description);

    RB throwable(Throwable throwable);

    RB data(D data);

    RB setCode(int code);

    RB setMessage(String message);

    RB setStackTrace(String stackTrace);

    RB setDescription(String description);

    RB setThrowable(Throwable throwable);

    RB setData(D data);

    /**
     * 子类不用重写此方法
     * @param type Class<V> 类型T的子类型对应的Class对象
     * @param <V>
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    default <V extends RestfullResult<D>> V build(Class<V> type) {
        return (V) this.build();
    }
}
