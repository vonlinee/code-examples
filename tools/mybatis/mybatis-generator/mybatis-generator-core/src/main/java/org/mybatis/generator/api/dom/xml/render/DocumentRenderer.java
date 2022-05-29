package org.mybatis.generator.api.dom.xml.render;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mybatis.generator.api.dom.xml.DocType;
import org.mybatis.generator.api.dom.xml.Document;

/**
 * XML文档生成
 *
 * TODO 多个子标签之间空一行，优化文本显示
 * TODO 改成Stream流和不用Stream流方式
 */
public class DocumentRenderer {

    /**
     * 返回最终生成的字符串文本
     * @param document 文档对象
     * @return
     */
    public String render(Document document) {
        Stream<String> header = renderXmlHeader();
        Stream<String> doctype = renderDocType(document);
        //
        Stream<String> rootElement = renderRootElement(document);
        String doc = Stream.of(header, doctype, rootElement).flatMap(Function.identity())
                .collect(Collectors.joining(System.getProperty("line.separator")));//$NON-NLS-1$
        return doc;
    }

    private Stream<String> renderXmlHeader() {
        return Stream.of("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); //$NON-NLS-1$
    }

    /**
     * 渲染文档类型
     * @param document 文档
     * @return
     */
    private Stream<String> renderDocType(Document document) {
        return Stream.of("<!DOCTYPE " //$NON-NLS-1$
                + document.getRootElement().getName()
                + document.getDocType().map(this::renderDocType).orElse("") //$NON-NLS-1$
                + ">"); //$NON-NLS-1$
    }

    private String renderDocType(DocType docType) {
        return " " + docType.accept(new DocTypeRenderer()); //$NON-NLS-1$
    }

    private Stream<String> renderRootElement(Document document) {
        return document.getRootElement().accept(new ElementRenderer());
    }
}