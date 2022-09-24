package io.devpl.sdk.restful;

import io.devpl.sdk.ExtendedBuilder;

/**
 * 提供构建RestfulResult的常用方法
 *
 * @param <R>  Builder要构造的Result类型
 * @param <RB> RestfulResultBuilder的子类型
 */
interface RestfulRBuilder<R, RB extends RestfulRBuilder<R, RB>> extends ExtendedBuilder<R, RB> {

    default RB code(int code) {
        return setCode(code);
    }

    default RB message(String message) {
        return setMessage(message);
    }

    default RB stackTrace(String stackTrace) {
        return setStackTrace(stackTrace);
    }

    default RB toast(String toastMessage) {
        return setToast(toastMessage);
    }

    default RB moreInfo(String moreInfo) {
        return setMoreInfo(moreInfo);
    }

    RB setCode(int code);

    RB setMessage(String message);

    RB setStackTrace(String stackTrace);

    RB setToast(String toastMessage);

    RB setMoreInfo(String moreInfo);

    default RB status(Status status) {
        return setCode(status.getCode()).setMessage(status.getMessage());
    }

    default RB setStatus(Status status) {
        return setCode(status.getCode()).setMessage(status.getMessage());
    }
}
