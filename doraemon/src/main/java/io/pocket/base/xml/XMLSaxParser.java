package io.pocket.base.xml;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class XMLSaxParser {

    public static void parse(String path) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // 2.从解析工厂获取解析器
        SAXParser parse = factory.newSAXParser();
        // 3.得到解读器
        XMLReader reader = parse.getXMLReader();
        // 4.设置内容处理器
        reader.setContentHandler(new XmlContentHandler());
        // 5.读取xml的文档内容
        reader.parse(path);
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        parse("D:\\Develop\\Projects\\Github\\code-example\\code-generator\\pom.xml");
    }
}
