package codegen.xml.render;

import codegen.xml.DocType;
import codegen.xml.Document;
import codegen.xml.XmlElement;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文档渲染
 */
public class DocumentRenderer {

    /**
     * 渲染文本 Document => String
     * @param document 文档对象
     * @return
     */
    public String render(Document document) {
        Stream<String> xmlHeader = renderXmlHeader();
        Stream<String> docType = renderDocType(document);
        // 渲染根标签
        Stream<String> rootElement = renderRootElement(document);
        return Stream.of(xmlHeader, docType, rootElement).flatMap(Function.identity())
                .collect(Collectors.joining(System.getProperty("line.separator"))); //$NON-NLS-1$
    }

    private Stream<String> renderXmlHeader() {
        return Stream.of("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); //$NON-NLS-1$
    }

    private Stream<String> renderDocType(Document document) {
        return Stream.of("<!DOCTYPE " //$NON-NLS-1$
                + document.getRootElement().getName()
                + document.getDocType().map(this::renderDocType).orElse("") //$NON-NLS-1$
                + ">"); //$NON-NLS-1$
    }

    private String renderDocType(DocType docType) {
        return " " + docType.accept(new DocTypeRenderer()); //$NON-NLS-1$
    }

    /**
     * 访问者模式
     * @param document
     * @return
     */
    private Stream<String> renderRootElement(Document document) {
        XmlElement rootElement = document.getRootElement();
        return rootElement.accept(new ElementRenderer());
    }
}
