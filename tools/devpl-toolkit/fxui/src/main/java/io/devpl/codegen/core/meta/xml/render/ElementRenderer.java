package io.devpl.codegen.core.meta.xml.render;

import io.devpl.codegen.core.meta.xml.*;
import org.mybatis.generator.internal.util.CustomCollectors;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ElementRenderer implements ElementVisitor<Stream<String>> {

    private final AttributeRenderer attributeRenderer = new AttributeRenderer();

    @Override
    public Stream<String> visit(TextElement element) {
        String content = element.getContent();
        return Stream.of(content);
    }

    @Override
    public Stream<String> visit(XmlElement element) {
        if (element.hasChildren()) {
            // 渲染子标签
            return renderWithChildren(element);
        } else {
            return renderWithoutChildren(element);
        }
    }

    private Stream<String> renderWithoutChildren(XmlElement element) {
        return Stream.of("<" //$NON-NLS-1$
                + element.getName()
                + renderAttributes(element)
                + " />"); //$NON-NLS-1$
    }

    public Stream<String> renderWithChildren(XmlElement element) {
        Stream<String> open = renderOpen(element);
        // TODO 控制子标签之间的排版，是否有空行等
        Stream<String> children = renderChildren(element);
        Stream<String> close = renderClose(element);
        return Stream.of(open, children, close).flatMap(s -> s);
    }

    private String renderAttributes(XmlElement element) {
        return element.getAttributes().stream()
                .sorted(Comparator.comparing(Attribute::getName))
                .map(attributeRenderer::render)
                .collect(CustomCollectors.joining(" ", " ", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    private Stream<String> renderOpen(XmlElement element) {
        return Stream.of("<" //$NON-NLS-1$
                + element.getName()
                + renderAttributes(element)
                + ">"); //$NON-NLS-1$
    }

    /**
     * 渲染子标签
     * @param element
     * @return
     */
    private Stream<String> renderChildren(XmlElement element) {
        List<VisitableElement> elements = element.getElements();

        Stream<VisitableElement> elementStream = elements.stream();
        Stream<String> stringStream = elementStream.flatMap(this::renderChild);
        Stream<String> stream = stringStream.map(this::indent);

        List<String> streamCopy = stream.collect(Collectors.toList());
        return streamCopy.stream();
    }

    private Stream<String> renderChild(VisitableElement child) {
        return child.accept(this);
    }

    private String indent(String s) {
        return "  " + s; //$NON-NLS-1$
    }

    private Stream<String> renderClose(XmlElement element) {
        return Stream.of("</" //$NON-NLS-1$
                + element.getName()
                + ">"); //$NON-NLS-1$
    }
}
