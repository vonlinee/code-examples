package io.maker.base.rest;

import java.io.Serializable;
import java.util.StringJoiner;

public class OptResult<T> extends Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

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
            this.description = ResultDescription.custom(code, message, statusCode -> statusCode == 1);
        }
        this.data = data;
    }

    @Override
    public String toString() {
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
        return create(code, message, null);
    }

    public static <T> OptResult<T> create(String message) {
        return create(0, message);
    }

    /**
     * Builder Pattern
     * @param <T>
     * @author line
     */
    public static class Builder<T> {
        private int code;
        private String message = "";
        private T data;
        private ResultDescription description;

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

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        @SuppressWarnings({"hiding"})
        public OptResult<T> build() {
            if (this.description != null) {
                return new OptResult<>(this.description.code, this.description.message, Builder.this.data);
            }
            return new OptResult<>(Builder.this.code, Builder.this.message, Builder.this.data);
        }
    }
}
