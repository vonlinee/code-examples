package io.devpl.codegen.mbpg.template;

import io.devpl.codegen.mbpg.config.ContextAware;
import io.devpl.codegen.mbpg.config.builder.Context;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * 模板信息
 */
public abstract class Template implements ContextAware {

    private Context context;

    private TemplateArguments arguments;

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
        URL url = uri.toURL();
        return url.openStream();
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}
