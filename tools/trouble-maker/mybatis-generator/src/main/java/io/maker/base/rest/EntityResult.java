package io.maker.base.rest;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 单条返回结果
 * @param <T>
 * @author line
 */
public final class EntityResult<T> extends Result<T> implements Serializable {

    private static final long serialVersionUID = 4134449411254581242L;

    private String code;
    private String message;

    public EntityResult() {
        super();
    }

    public EntityResult(String code, String message) {
        super();
    }

    /**
     * @param description Must Not Null
     */
    public EntityResult(ResultDescription description) {
        super();
    }

    @Override
    protected String show() {
        return null;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * Builder Pattern
     * @param <T>
     * @author line
     */
    public static class Builder<T> {
        int code;
        String message;
        String timestamp;
        String errorMessage;
        T data;

        public Builder<T> description(ResultDescription description) {
            this.code = description.code;
            this.message = description.message;
            return this;
        }

        public Builder<T> description(int code, String message) {
            this.code = code;
            this.message = message;
            return this;
        }

        public Builder<T> errorMessage(String errorMsg) {
            this.errorMessage = errorMsg;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        @SuppressWarnings({"unchecked", "hiding"})
        public <T> EntityResult<T> build() {
            return new EntityResult<>();
        }
    }
}
