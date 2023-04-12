package io.devpl.codegen.mbpg.template;

import io.devpl.codegen.mbpg.config.ContextAware;
import io.devpl.codegen.mbpg.config.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URI;

/**
 * 每一个模板文件对应一个TemplateFile实例
 * 包含模板文件信息，模板所需的参数
 */
public abstract class Template implements ContextAware {

    private Context context;

    private String name;

    /**
     * 初始化模板
     */
    public abstract void initialize();

    /**
     * 加载模板流
     *
     * @return 模板文件流
     */
    public InputStream load(URI uri) throws IOException {
        return uri.toURL().openStream();
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    public abstract void write(TemplateArguments arguments, Writer writer) throws IOException;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
