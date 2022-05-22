package org.mybatis.generator.api.dom.xml;

/**
 * 访问者模式
 */
@FunctionalInterface
public interface VisitableElement {
    <R> R accept(ElementVisitor<R> visitor);
}
