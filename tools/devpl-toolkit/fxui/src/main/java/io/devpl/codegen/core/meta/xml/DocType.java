package io.devpl.codegen.core.meta.xml;

public interface DocType {
    <R> R accept(DocTypeVisitor<R> visitor);
}
