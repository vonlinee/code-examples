package io.devpl.webui.rest;

public class EntityResult<T> extends ResultTemplate.Builder {

    private T data;

    public static <T> EntityResult<T> builder() {
        return new EntityResult<>();
    }

    @Override
    public ResultTemplate build() {
        EntityResultTemplate template = new EntityResultTemplate();
        template.setData(this.data);
        template.setCode(this.code);
        template.setDescription(this.description);
        template.setStacktrace(this.stackTrace);
        template.setMessage(this.message);
        return template;
    }
}
