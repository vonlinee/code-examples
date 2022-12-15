package org.mybatis.generator.config.xml;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * 解决方案：https://blog.csdn.net/chjttony/article/details/7720873
 * Implementation of <code>org.xml.sax.EntityResolver</code> that loads
 * entities (for example dtd files) from the classpath.
 */
public class ClasspathEntityResolver implements EntityResolver {

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        if (systemId != null) {
            int index = systemId.lastIndexOf('/');
            if (index != -1) {
                systemId = systemId.substring(index + 1);
            }
            systemId = "/" + systemId;
            InputStream istr = Thread.currentThread().getContextClassLoader().getResourceAsStream(systemId);
            if (istr != null) {
                return new InputSource(istr);
            }
        }
        return null;
    }
}