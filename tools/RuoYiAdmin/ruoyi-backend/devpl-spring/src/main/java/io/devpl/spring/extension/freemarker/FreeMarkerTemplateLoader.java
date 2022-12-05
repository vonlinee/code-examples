package io.devpl.spring.extension.freemarker;

import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.Reader;

/**
 * 模版文件加载器用来告诉 FreeMarker 引擎到什么地方去加载模版文件。 FreeMarker 自带了三种文件加载器，
 * 分别是：文件目录加载器、类路径加载器以及 Web 上下文加载器。当在 Web 环境中使用 FreemarkerServlet 来
 * 加载模版文件时，默认使用第三种加载器，并通过 Servlet 的配置 TemplatePath 来指定模版文件所存放的路径，
 * 该路径是相对于 Web 的根目录的。
 *
 * FreeMarker 自带的几个 TemplateLoader 分别是：
 * ClassTemplateLoader ：基于类路径的模版加载器
 * FileTemplateLoader ：基于文件目录的模版加载器
 * MultiTemplateLoader ：多种加载器的混合
 * StringTemplateLoader ：基于字符串的模版加载器
 * URLTemplateLoader ：基于 URL 的模版加载器
 * WebappTemplateLoader ：基于 Web 上下文的模版加载器
 *
 *
 * <p>
 * 在某种情况下，我们可能会希望把模版文件的源码进行加密处理，例如我们使用 DES 加密方式将模版源文件加密后进行存储，
 * 然后我们通过自行实现一个加密的模版文件加载器来读取这些模版文件，解密后交给 FreeMarker 引擎解释执行并得到执行的结果。
 * FreeMarker 为模版文件加载器定义了一个统一的接口 —— TemplateLoader ，该接口有以下四个方法：
 */
public class FreeMarkerTemplateLoader implements TemplateLoader {

    /**
     * 根据名称返回指定的模版资源
     * @param name
     * @return
     * @throws IOException
     */
    @Override
    public Object findTemplateSource(String name) throws IOException {
        return null;
    }

    /**
     * 返回模版资源最后一次修改的时间
     * @param templateSource
     * @return
     */
    @Override
    public long getLastModified(Object templateSource) {
        return 0;
    }

    /**
     * 返回读取模版资源的 Reader
     * @param templateSource
     * @param encoding
     * @return
     * @throws IOException
     */
    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return null;
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {
        // 关闭模版资源
    }
}
