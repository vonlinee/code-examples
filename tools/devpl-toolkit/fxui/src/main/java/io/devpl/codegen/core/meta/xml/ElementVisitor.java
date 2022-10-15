package io.devpl.codegen.core.meta.xml;

public interface ElementVisitor<R> {

    R visit(TextElement element);

    R visit(XmlElement element);
}
