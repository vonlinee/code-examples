package org.mybatis.generator.api.dom.xml.render;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mybatis.generator.api.dom.xml.DocType;
import org.mybatis.generator.api.dom.xml.Document;

public class DocumentRenderer {

    /**
     * 将文档直接渲染成字符串
     *
     * @param document
     * @return
     */
    public String render(Document document) {
        Stream<Stream<String>> stream = Stream.of(renderXmlHeader(), renderDocType(document), renderRootElement(document));
        Stream<String> stream1 = stream.flatMap(Function.identity());

        List<String> list = new ArrayList<>();

        stream1.forEach(s -> {
            if (s.contains("</resultMap") || s.contains("</sql") || s.contains("</insert")
                    || s.contains("</update") || s.contains("</delete") || s.contains("</select")) {
                list.add(s + "\n"); // 换行
            } else {
                list.add(s);
            }
        });

        // String documentString = stream1.collect(Collectors.joining(System.getProperty("line.separator")));//$NON-NLS-1$
        // System.out.println(documentString);
        return String.join("\n", list);
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

    private Stream<String> renderRootElement(Document document) {
        return document.getRootElement().accept(new ElementRenderer());
    }
}
