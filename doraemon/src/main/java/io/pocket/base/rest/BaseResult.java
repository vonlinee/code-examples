package io.pocket.base.rest;

public class BaseResult extends Result<String> {

    public BaseResult(ResultDescription description) {
        super(description);
    }

    @Override
    protected String show() {
        return "null";
    }
}
