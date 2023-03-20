package io.devpl.codegen.mbpg.template;

import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @see freemarker.template.TemplateHashModelEx2
 */
public interface TemplateDataModel extends TemplateHashModel {

    @Override
    TemplateModel get(String key) throws TemplateModelException;
}
