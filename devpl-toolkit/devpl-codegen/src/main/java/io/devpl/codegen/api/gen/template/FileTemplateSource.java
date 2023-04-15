package io.devpl.codegen.api.gen.template;

/**
 * 文件模板
 */
public abstract class FileTemplateSource extends AbstractTemplateSource {

    private static final String FILE_TEMPLATE_PREFIX = "file://";

    @Override
    boolean isNameValid(String templateName) {
        return templateName != null && templateName.startsWith(FILE_TEMPLATE_PREFIX);
    }
}
