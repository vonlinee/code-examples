package io.devpl.codegen.fxui.utils.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class XML {

    private static final SAXReader SAX_READER = new SAXReader();

    public static void fromString(String xmlFragment) {
        InputSource in = new InputSource(new StringReader(xmlFragment));
        in.setEncoding(StandardCharsets.UTF_8.name());
        Document document = readDocument(in);// 如果是xml文件这里直接写xml文件的绝对路径
        Element root = document.getRootElement();// 获取根节点
        List<Element> elements = root.elements();// 查找子节点
        for (Element element : elements) {// 读取到第一个子节点

        }
    }

    private static Document readDocument(InputSource inputSource) {
        try {
            return SAX_READER.read(inputSource);
        } catch (Exception exception) {
            throw new RuntimeException("failed to read document!");
        }
    }

    public static void main(String[] args) throws DocumentException {
        String xml = "        <dependency>\n" +
                "            <groupId>org.dom4j</groupId>\n" +
                "            <artifactId>dom4j</artifactId>\n" +
                "            <version>2.1.3</version>\n" +
                "        </dependency>";
        InputSource in = new InputSource(new StringReader(xml));


    }
}
