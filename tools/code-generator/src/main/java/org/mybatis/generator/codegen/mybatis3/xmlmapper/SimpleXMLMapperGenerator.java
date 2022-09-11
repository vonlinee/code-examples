package org.mybatis.generator.codegen.mybatis3.xmlmapper;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.DeleteByPrimaryKeyElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.InsertElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.ResultMapWithoutBLOBsElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SimpleSelectAllElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SimpleSelectByPrimaryKeyElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.UpdateByPrimaryKeyWithoutBLOBsElementGenerator;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleXMLMapperGenerator extends AbstractXmlGenerator {

    private static final Log logger = LogFactory.getLog(SimpleXMLMapperGenerator.class);

    public SimpleXMLMapperGenerator() {
        super();
    }

    /**
     * <mapper></mapper>标签的内容
     *
     * @return
     */
    protected XmlElement getSqlMapElement() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString("Progress.12", table.toString())); //$NON-NLS-1$
        // 根标签
        XmlElement answer = new XmlElement("mapper"); //$NON-NLS-1$

        answer.setBlankBetweenChildren(true);
        logger.info("初始化<mapper></mapper>");

        String namespace = introspectedTable.getMyBatis3SqlMapNamespace();
        answer.addAttribute(new Attribute("namespace", namespace)); //$NON-NLS-1$
        // 添加注释
        CommentGenerator commentGenerator = context.getCommentGenerator();
        commentGenerator.addRootComment(answer);
        // ResultMap标签
        addResultMapElement(answer);

        // 添加XML Mapper标签
        addDeleteByPrimaryKeyElement(answer);
        addInsertElement(answer);
        addUpdateByPrimaryKeyElement(answer);
        addSelectByPrimaryKeyElement(answer);
        addSelectAllElement(answer);

        return answer;
    }

    protected void addResultMapElement(XmlElement parentElement) {
        // 是否生成BaseResultMap
        if (introspectedTable.getRules().generateBaseResultMap()) {
            AbstractXmlElementGenerator elementGenerator = new ResultMapWithoutBLOBsElementGenerator(true);
            initializeAndExecuteGenerator(elementGenerator, parentElement);

        }
    }

    protected void addSelectByPrimaryKeyElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractXmlElementGenerator elementGenerator = new SimpleSelectByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addSelectAllElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new SimpleSelectAllElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }

    protected void addDeleteByPrimaryKeyElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateDeleteByPrimaryKey()) {
            AbstractXmlElementGenerator elementGenerator = new DeleteByPrimaryKeyElementGenerator(true);
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addInsertElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateInsert()) {
            AbstractXmlElementGenerator elementGenerator = new InsertElementGenerator(true);
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateByPrimaryKeyElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeySelective()) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByPrimaryKeyWithoutBLOBsElementGenerator(true);
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void initializeAndExecuteGenerator(AbstractXmlElementGenerator elementGenerator, XmlElement parentElement) {
        elementGenerator.setContext(context);
        elementGenerator.setIntrospectedTable(introspectedTable);
        elementGenerator.setProgressCallback(progressCallback);
        elementGenerator.setWarnings(warnings);
        elementGenerator.addElements(parentElement);
    }

    @Override
    public Document getDocument() {
        Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID, XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
        // 根标签
        XmlElement sqlMapElement = getSqlMapElement();
        document.setRootElement(sqlMapElement);
        // 执行插件生命周期
        if (!context.getPlugins().sqlMapDocumentGenerated(document, introspectedTable)) {
            document = null;
        }
        return document;
    }
}
