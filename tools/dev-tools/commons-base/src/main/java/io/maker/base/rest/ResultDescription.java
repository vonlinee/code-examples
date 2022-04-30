package io.maker.base.rest;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 自定义结果的枚举常量应继承此类，只存储状态码和信息，不被序列化
 * 用的时候只会用到此类中存储的数据，而不会用此类本身
 * TODO 重用单个ResultDescription对象,可能导致并发问题
 * <p>
 * 不采用枚举实现，因为枚举的值是固定的，不能动态添加
 */
public abstract class ResultDescription implements Serializable {

    private static final Integer UNKNOWN_CODE = -128; //未知编码
    private static final String UNKNOWN_MESSAGE = "NONE"; //未知编码

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

    public final ResultDescription code(int code) {
        this.code = code;
        return this;
    }

    public final ResultDescription message(String message) {
        this.message = message;
        return this;
    }

    //判断结果是成功还是失败
    public abstract boolean isSuccess();

    public abstract boolean isFailed();

    /**
     * 简单的Web场景下的实现
     */
    private static class HttpOperationImpl extends ResultDescription {

        HttpOperationImpl(int code, String message) {
            super(code, message);
        }

        @Override
        public boolean isSuccess() {
            return code == 200;
        }

        @Override
        public boolean isFailed() {
            return code == 404;
        }
    }

    /**
     * 自定义实现
     */
    private static class CustomDescription extends ResultDescription {

        /**
         * 自定义判断状态码是成功还是失败，只针对此实例
         */
        private final Predicate<Integer> rule;

        /**
         * 自定义判断状态码是成功还是失败，针对所有自定义描述实例
         */
        private static Predicate<Integer> globalRule;

        CustomDescription(int code, String message, Predicate<Integer> rule) {
            super(code, message);
            this.rule = rule;
        }

        /**
         * 优先使用全局状态码规则进行校验
         * @return isSuccess
         */
        @Override
        public boolean isSuccess() {
            if (globalRule != null) return globalRule.test(code);
            return rule.test(code);
        }

        @Override
        public boolean isFailed() {
            return !isSuccess();
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
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailed() {
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

    public static ResultDescription custom(int code, String message, Predicate<Integer> rule, Predicate<Integer> globalRule) {
        CustomDescription.globalRule = globalRule;
        return new CustomDescription(code, message, rule);
    }

    public static void setGlobalCustomCodeRule(Predicate<Integer> globalRule) {
        CustomDescription.globalRule = globalRule;
    }

}
