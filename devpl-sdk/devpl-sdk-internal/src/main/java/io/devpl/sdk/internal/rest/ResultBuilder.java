package io.devpl.sdk.internal.rest;

/**
 * Builder模式
 * 不使用泛型，失去了在编译器就进行检查的能力
 * @param
 */
public interface ResultBuilder {

    <B extends ResultBuilder> B code(int code);

    <B extends ResultBuilder> B message(String message);

    <B extends ResultBuilder> B stacktrace(String stacktrace);

    <B extends ResultBuilder> B description(String description);

    <B extends ResultBuilder> B data(Object data);

    /**
     * 只有ListResult支持此方法
     * @param pageInfo
     * @param <B>
     * @return
     */
    <B extends ResultBuilder> B pageInfo(PageInfo pageInfo);

    <R extends Result> R build();
}