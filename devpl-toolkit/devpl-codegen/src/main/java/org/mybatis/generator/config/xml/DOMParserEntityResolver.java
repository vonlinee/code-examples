package org.mybatis.generator.config.xml;

import java.io.InputStream;

import org.mybatis.generator.codegen.XmlConstants;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class DOMParserEntityResolver implements EntityResolver {

    public DOMParserEntityResolver() {
        super();
    }

    // 解决方案：https://blog.csdn.net/chjttony/article/details/7720873
    @Override
    public InputSource resolveEntity(String publicId, String systemId) {
        if (XmlConstants.MYBATIS_GENERATOR_CONFIG_PUBLIC_ID
                .equalsIgnoreCase(publicId)) {
            // 加载本地的DTD
            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream(
                            "org/mybatis/generator/config/xml/mybatis-generator-config_1_0.dtd"); //$NON-NLS-1$
            return new InputSource(is);
        } else {
            return null;
        }
    }
}
