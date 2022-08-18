/*
 *    Copyright 2006-2020 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.api.dom.xml.render;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mybatis.generator.api.dom.xml.DocType;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class DocumentRenderer {

    public String render(Document document) {
        Stream<String> xmlHeader = renderXmlHeader();
        Stream<String> docType = renderDocType(document);
        // 根标签
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
