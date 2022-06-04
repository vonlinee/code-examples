package samples;

import org.mybatis.generator.api.XmlFormatter;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.render.DocumentRenderer;
import org.mybatis.generator.config.Context;

public class MyXmlFormatter implements XmlFormatter {

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
