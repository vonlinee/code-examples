package codegen.generator;

import codegen.xml.Attribute;
import codegen.xml.Document;
import codegen.xml.XmlConstants;
import codegen.xml.XmlElement;

public class XmlMapperGenerator extends AbstractXmlGenerator {

    /**
     * MyBatis XML Mapper根标签
     * @return
     */
    protected XmlElement getSqlMapElement() {
        XmlElement answer = new XmlElement("mapper");
        answer.addAttribute(new Attribute("namespace", "namespace"));
        return answer;
    }

    @Override
    public Document getDocument() {
        Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID, XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
        XmlElement sqlMapElement = getSqlMapElement();
        document.setRootElement(sqlMapElement);
        return document;
    }
}
