package io.devpl.codegen.api.gen.template;

public abstract class AbstractTemplateSource implements TemplateSource {

    private String templateName;

    /**
     * 模板名称是否有效
     * @param templateName 模板名称
     * @return 模板名称是否有效
     */
    abstract boolean isNameValid(String templateName);

    @Override
    public void setName(String name) {
        if (isNameValid(name)) {
            this.templateName = name;
        }
    }

    @Override
    public String getName() {
        return templateName;
    }
}
