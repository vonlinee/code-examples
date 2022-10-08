package codegen.xml;

@FunctionalInterface
public interface VisitableElement {
    <R> R accept(ElementVisitor<R> visitor);
}
