package io.devpl.codegen.core.meta.xml;

public interface DocTypeVisitor<R> {

    R visit(PublicDocType docType);

    R visit(SystemDocType docType);
}
