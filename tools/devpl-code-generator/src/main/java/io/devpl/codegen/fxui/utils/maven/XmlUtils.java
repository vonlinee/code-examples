package io.devpl.codegen.fxui.utils.maven;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class XmlUtils {

    /**
     * 一个xml字符串转对象，忽略xml命名空间
     * @param xml   XML字符串
     * @param msgVo 根元素实体
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlToBean(String xml, Class<T> msgVo) throws JAXBException, SAXException, ParserConfigurationException {
        if (msgVo == null) {
            return null;
        }
        JAXBContext context = JAXBContext.newInstance(msgVo);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Source source = trunSource(xml);
        return (T) unmarshaller.unmarshal(source);
    }

    /**
     * xml字符串集合转对象，忽略xml命名空间
     * @param xmlList xml字符串集合
     * @param msgVo   根元素实体
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> xmlToBean(List<String> xmlList, Class<T> msgVo) throws JAXBException, SAXException, ParserConfigurationException {
        if (msgVo == null) {
            return Collections.EMPTY_LIST;
        }
        List<T> beanList = new LinkedList<T>();
        JAXBContext context = JAXBContext.newInstance(msgVo);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        for (String xmlStr : xmlList) {
            Source source = trunSource(xmlStr);
            beanList.add((T) unmarshaller.unmarshal(source));
        }
        return beanList;
    }

    private static Source trunSource(String xmlStr) throws SAXException, ParserConfigurationException {
        StringReader reader = new StringReader(xmlStr);
        SAXParserFactory sax = SAXParserFactory.newInstance();
        sax.setNamespaceAware(false);
        XMLReader xmlReader = sax.newSAXParser().getXMLReader();
        Source source = new SAXSource(xmlReader, new InputSource(reader));
        return source;
    }

    public static void main(String[] args) throws JAXBException, ParserConfigurationException, SAXException {
        final MavenDependency mavenDependency = xmlToBean("        <dependency>\n" +
                "            <groupId>ch.qos.logback</groupId>\n" +
                "            <artifactId>logback-classic</artifactId>\n" +
                "            <version>1.4.0</version>\n" +
                "        </dependency>", MavenDependency.class);

        System.out.println(mavenDependency);
    }

}