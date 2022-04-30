package io.maker.base.rest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.maker.base.collection.ValueMap;

public final class PageResult<T> extends Result<List<T>> implements Serializable {

    private static final long serialVersionUID = 4134449411254581242L;

    public PageResult() {
        super();
    }

    public static void main(String[] args) {
		
    	PageResult<ValueMap<String>> result = PageResult.<ValueMap<String>>builder().build();
    	
    	List<ValueMap<String>> data = result.getData();
    	
	}
    
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }
    
    public static <T> Builder<T> builder(Class<T> clazz) {
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
        List<T> rows;

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

        public Builder<T> data(List<T> rows) {
            this.rows = rows;
            return this;
        }

        @SuppressWarnings({"hiding"})
        public PageResult<T> build() {
            return new PageResult<>();
        }
    }
}
