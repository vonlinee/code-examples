package io.devpl.business;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 列表数据验证工具类，并给出详细的校验结果
 * 对于方法的执行顺序有要求，同时代码里不做限制，由使用方保证，具体顺序为：raw -> assertTrue -> result
 */
public class FieldCheckTemplate<T> {

    /**
     * 导入成功条数
     */
    private int successCount;

    /**
     * 导入失败条数
     */
    private int failureCount;

    /**
     * 从Excel成功导入的数据，用于批量添加到数据库中
     */
    private List<T> successfulDataList;

    /**
     * 导入失败信息，每行一个字符串
     */
    private List<String> errMsg;

    /**
     * 异常信息
     */
    private String exceptionMessage;

    // 原始数据列表
    private final transient List<T> rawData;
    private transient List<Function<T, ?>> getters;
    private transient List<Predicate<Object>> conditions;
    private transient List<String> messages;

    FieldCheckTemplate(List<T> rawDataList) {
        this.rawData = rawDataList;
    }

    /**
     * 不能重复加入某个字段，同时不做校验，比较麻烦且浪费性能
     * @param getter    提供字段值
     * @param condition 字段应满足的条件
     * @param message   提示信息
     * @param <V>       实体类类型
     * @return FieldValidator
     */
    public final <V> FieldCheckTemplate<T> assertTrue(Function<T, V> getter, Predicate<V> condition, String message) {
        // 数据为空，不用校验
        if (rawData == null || rawData.isEmpty()) {
            return this;
        }
        if (getter == null || condition == null) {
            throw new NullPointerException();
        }
        getGetters().add(getter);
        @SuppressWarnings("unchecked") final Predicate<Object> _condition = (Predicate<Object>) condition;
        getConditions().add(_condition);
        getMessages().add(message);
        return this;
    }

    public FieldCheckTemplate<T> result() {
        if (rawData == null || rawData.isEmpty()) {
            exceptionMessage = "数据为空";
            return this;
        }
        int conditionCount = getters.size();
        if (conditions == null) {
            successfulDataList = rawData;
            successCount = rawData.size();
            return this;
        }
        for (int i = 0; i < rawData.size(); i++) {
            final StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("第").append(i).append("行:");
            // 本条数据是否校验通过
            boolean isPassed = true;
            try {
                for (int j = 0; j < conditionCount; j++) {
                    final Object value = getters.get(j).apply(rawData.get(i));
                    if (!conditions.get(j).test(value)) {
                        isPassed = false;
                        messageBuilder.append(messages.get(j)).append(";");
                    }
                }
            } catch (Exception exception) {
                // 出现任何异常终止校验循环，返回一个空结果
                this.successCount = 0;
                this.failureCount = 0;
                this.successfulDataList = null;
                this.exceptionMessage = exception.getMessage();
                return this;
            }
            if (isPassed) {
                successCount++;
                getSuccessfulDataList().add(rawData.get(i));
            } else {
                failureCount++;
                getErrMsg().add(messageBuilder.toString());
            }
        }
        return this;
    }

    /**
     * 使用原始数据进行遍历
     * @param rawDataList 待校验的数据
     * @param <T>         数据类型
     * @return FieldCheckTemplate
     */
    public static <T> FieldCheckTemplate<T> raw(List<T> rawDataList) {
        return new FieldCheckTemplate<>(rawDataList);
    }

    public List<T> getSuccessfulDataList() {
        return this.successfulDataList == null ? this.successfulDataList = new LinkedList<>() : this.successfulDataList;
    }

    public List<String> getErrMsg() {
        return this.errMsg == null ? this.errMsg = new LinkedList<>() : this.errMsg;
    }

    private List<Function<T, ?>> getGetters() {
        return this.getters == null ? this.getters = new LinkedList<>() : this.getters;
    }

    private List<Predicate<Object>> getConditions() {
        return this.conditions == null ? this.conditions = new LinkedList<>() : this.conditions;
    }

    private List<String> getMessages() {
        return this.messages == null ? this.messages = new LinkedList<>() : this.messages;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }
}
