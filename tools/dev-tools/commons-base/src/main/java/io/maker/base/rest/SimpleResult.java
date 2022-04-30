package io.maker.base.rest;

import java.util.StringJoiner;

/**
 * 不携带数据的结果
 */
public final class SimpleResult extends Result<String> {

    private SimpleResult(int code, String message) {
        if (this.description == null) {
            this.description = ResultDescription.custom(code, message, code1 -> code1 == 1);
        }
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "{", "}");
        sj.add("\"timestamp\":\"" + timestamp + "\"");
        sj.add("\"code\":\"" + description.code + "\"");
        sj.add("\"message\":\"" + description.message + "\"");
        sj.add("\"data\":\"" + (data == null ? "" : data) + "\"");
        return sj.toString();
    }
}
