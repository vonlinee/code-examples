package io.devpl.codegen.core.meta.xml;

@FunctionalInterface
public interface VisitableElement {
    <R> R accept(ElementVisitor<R> visitor);
}
