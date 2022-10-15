package codegen.xml;

public interface DocType {
    <R> R accept(DocTypeVisitor<R> visitor);
}
