package io.devpl.codegen.api.gen.template;

import io.devpl.codegen.api.Context;
import io.devpl.codegen.api.ContextAware;
import io.devpl.codegen.utils.RuntimeUtils;
import io.devpl.codegen.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * 模板引擎抽象类
 * 屏蔽具体的模板引擎差异性
 */
public abstract class AbstractTemplateEngine implements ContextAware {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 配置信息
     */
    private Context context;

    /**
     * 模板引擎初始化
     */
    public abstract void init(Context context);

    /**
     * 打开输出目录
     */
    public void open() {
        String outDir = context.getProjectConfiguration().getOutputDir();
        if (!StringUtils.hasText(outDir) || !new File(outDir).exists()) {
            log.error("未找到输出目录 {}", outDir);
        } else if (context.getProjectConfiguration().isOpenOutputDir()) {
            try {
                RuntimeUtils.openDirectory(outDir);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 将模板转化成为文件
     * @param arguments 模板参数
     * @param template  模板，可能是字符串模板，可能是指向文件模板的路径，可能是指向模板文件的URL，有些模板引擎API不支持通过输入流获取模板
     * @param writer    文件生成输出位置
     */
    public abstract void render(TemplateSource template, TemplateArguments arguments, Writer writer);

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 加载模板
     * @param templateName 模板名称
     * @return 模板文件
     */
    public abstract TemplateSource load(String templateName);
}
