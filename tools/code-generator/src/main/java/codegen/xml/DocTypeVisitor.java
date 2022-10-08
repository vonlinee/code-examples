package codegen.xml;

public interface DocTypeVisitor<R> {

    R visit(PublicDocType docType);

    R visit(SystemDocType docType);
}
