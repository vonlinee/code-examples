package io.pocket.base.rest;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * the result of operation
 * @param <T>
 * @author vonline
 */
public class OptResult<T> extends Result<T> implements Serializable {

    private OptResult(ResultDescription description) {
        super(description);
    }

    private OptResult(ResultDescription description, T data) {
        super(description);
        this.data = data;
    }

    private OptResult(int code, String message, T data) {
        super();
        if (this.description == null) {
            this.description = new ResultDescription(code, message) {
                @Override
                public boolean success() {
                    return false;
                }

                @Override
                public boolean failed() {
                    return false;
                }
            };
        }
        this.data = data;
    }

    @Override
    public String show() {
        StringJoiner sj = new StringJoiner(",", "{", "}");
        sj.add("\"timestamp\":\"" + timestamp + "\"");
        sj.add("\"code\":'" + description.code + "'");
        sj.add("\"message\":'" + description.message + "'");
        sj.add("\"data\":\"" + (data == null ? "" : data.toString()) + "'");
        return sj.toString();
    }

    public static <T> OptResult.Builder<T> builder() {
        return new Builder<>();
    }

    public static <T> OptResult<T> create(int code, String message, T data) {
        return new OptResult<>(code, message, data);
    }

    public static <T> OptResult<T> create(int code, String message) {
        return new OptResult<>(code, message, null);
    }

    public static <T> OptResult<T> create(String message) {
        return new OptResult<>(0, message, null);
    }

    /**
     * Builder Pattern
     * @param <T>
     * @author line
     */
    public static class Builder<T> {
        int code;
        String message = "";
        T data;
        ResultDescription description;

        private Builder() {
        }

        public Builder<T> description(ResultDescription description) {
            this.description = description;
            this.code = description.code;
            this.message = description.message;
            return this;
        }

        public Builder<T> description(int code, String message) {
            this.code = code;
            this.message = message;
            return this;
        }

        public Builder<T> code(int code) {
            this.code = code;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> description(int code, String message, T data) {
            this.code = code;
            this.message = message;
            this.data = data;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        @SuppressWarnings({"unchecked", "hiding"})
        public <T> OptResult<T> build() {
            return new OptResult<T>(Builder.this.code, Builder.this.message, (T) Builder.this.data);
        }
    }
}
