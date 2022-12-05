package io.devpl.spring.extension.freemarker;

import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class Hash implements TemplateHashModel {
    @Override
    public TemplateModel get(String key) throws TemplateModelException {
        return null;
    }

    @Override
    public boolean isEmpty() throws TemplateModelException {
        return false;
    }
}
