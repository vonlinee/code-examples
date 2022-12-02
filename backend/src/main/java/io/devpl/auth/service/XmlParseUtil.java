package io.devpl.auth.service;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 使用SAX解析XML
 * @author Xu Jiabao
 * @since 2022/4/16
 */
public class XmlParseUtil {

    /**
     * 解析XML结果
     * @param handler 处理器，实现回调函数
     * @param source XML输入源
     */
    public static void parse(ContentHandler handler, InputSource source) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        reader.setContentHandler(handler);
        reader.parse(source);
    }

}
