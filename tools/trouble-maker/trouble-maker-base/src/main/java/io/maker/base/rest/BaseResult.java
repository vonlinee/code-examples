package io.maker.base.rest;

public class BaseResult extends Result<String> {

    public BaseResult(ResultDescription description) {
        super(description);
    }

    @Override
    protected String explain() {
        return "null";
    }
}
