package org.mybatis.generator.api.dom;

import org.mybatis.generator.api.XmlFormatter;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.render.DocumentRenderer;
import org.mybatis.generator.config.Context;

/**
 * This class is the default formatter for generated XML.  This class will use the
 * built in document renderer. 使用内置的文档渲染
 *
 * @author Jeff Butler
 *
 */
public class DefaultXmlFormatter implements XmlFormatter {
    protected Context context;

    @Override
    public String getFormattedContent(Document document) {
        return new DocumentRenderer().render(document);
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}
