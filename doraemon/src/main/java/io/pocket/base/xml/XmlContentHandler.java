package io.pocket.base.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlContentHandler extends DefaultHandler {

    /**
     * @param
     * @return void
     * @author lastwhisper
     * @desc 文档解析开始时调用，该方法只会调用一次
     */
    @Override
    public void startDocument() throws SAXException {
        System.out.println("----解析文档开始----");
    }

    /**
     * @param uri        xml文档的命名空间
     * @param localName  标签的名字
     * @param qName      带命名空间的标签的名字
     * @param attributes 标签的属性集
     * @author lastwhisper
     * @desc 每当遇到起始标签时调用
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println("标签<" + qName + ">解析开始");
    }

    /**
     * @param ch     当前读取到的TextNode(文本节点)的字节数组
     * @param start  字节开始的位置，为0则读取全部
     * @param length 当前TextNode的长度
     * @author lastwhisper
     * @desc 解析标签内的内容的时候调用
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch, start, length).trim();
        if (contents.length() > 0) {
            System.out.println("内容为-->" + contents);
        } else {
            System.out.println("内容为-->" + "空");
        }
    }

    /**
     * @param uri       xml文档的命名空间
     * @param localName 标签的名字
     * @param qName     带命名空间的标签的名字
     * @author lastwhisper
     * @desc 每当遇到结束标签时调用
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println("标签</" + qName + ">解析结束");
    }

    /**
     * @param
     * @author lastwhisper
     * @desc 文档解析结束后调用，该方法只会调用一次
     */
    @Override
    public void endDocument() throws SAXException {
        System.out.println("----解析文档结束----");
    }
}
