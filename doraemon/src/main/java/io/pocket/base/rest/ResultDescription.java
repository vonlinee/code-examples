package io.pocket.base.rest;

import java.util.function.Predicate;

/**
 * 自定义结果的枚举常量应继承此类，只存储状态码和信息，不被序列化
 * 用的时候只会用到此类中存储的数据，而不会用此类本身
 * TODO 重用单个ResultDescription对象,可能导致并发问题
 * @author line
 */
public abstract class ResultDescription {

    private static final Integer UNKNOWN_CODE = -128; //未知编码
    private static final String UNKNOWN_MESSAGE = ""; //未知编码

    protected int code;
    protected String message;

    public ResultDescription(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public final int code() {
        return code;
    }

    public final String message() {
        return message;
    }

    //判断结果是成功还是失败
    public abstract boolean success();

    public abstract boolean failed();

    /**
     * 简单的Web场景下的实现
     */
    private static class HttpOperationImpl extends ResultDescription {

        HttpOperationImpl(int code, String message) {
            super(code, message);
        }

        @Override
        public boolean success() {
            return code == 200;
        }

        @Override
        public boolean failed() {
            return code == 404;
        }
    }

    /**
     * 自定义实现
     */
    private static class CustomDescription extends ResultDescription {

        private final Predicate<Integer> rule;

        CustomDescription(int code, String message, Predicate<Integer> rule) {
            super(code, message);
            this.rule = rule;
        }

        @Override
        public boolean success() {
            return rule.test(code);
        }

        @Override
        public boolean failed() {
            return !success();
        }
    }

    /**
     * 空实现
     */
    private static class EmptyImpl extends ResultDescription {

        public EmptyImpl() {
            super(UNKNOWN_CODE, UNKNOWN_MESSAGE);
        }

        private EmptyImpl(int code, String message) {
            super(code, message);
        }

        @Override
        public boolean success() {
            return false;
        }

        @Override
        public boolean failed() {
            return false;
        }
    }

    public static ResultDescription empty() {
        return new EmptyImpl();
    }

    public static ResultDescription web(int code, String message) {
        return new HttpOperationImpl(code, message);
    }

    public static ResultDescription custom(int code, String message, Predicate<Integer> rule) {
        return new CustomDescription(code, message, rule);
    }
}
