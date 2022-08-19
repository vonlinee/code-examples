package org.mybatis.generator.api.dom.xml;

import java.util.ArrayList;
import java.util.List;

public class XmlElement implements VisitableElement {

    /**
     * 嵌套层级
     */
    private int level;

    private enum CommentLocation {
        AT_START,
        EMBED_FIRSTLINE
    }

    /**
     * 子标签之间是否添加空行，一般只有第二层标签添加空行，即<insert> <update> <delete> <select>
     */
    private boolean blankBetweenChildren = false;

    public boolean isBlankBetweenChildren() {
        return blankBetweenChildren;
    }

    public void setBlankBetweenChildren(boolean blankBetweenChildren) {
        this.blankBetweenChildren = blankBetweenChildren;
    }

    /**
     * 注释
     */
    private String comment;

    /**
     * 注释位置
     */
    private CommentLocation commentLocation = CommentLocation.AT_START;

    /**
     * 是否有注释
     */
    private boolean hasComment;

    private final List<Attribute> attributes = new ArrayList<>();

    private final List<VisitableElement> elements = new ArrayList<>();

    private String name;

    public XmlElement(String name) {
        this.name = name;
    }

    /**
     * Copy constructor. Not a truly deep copy, but close enough for most purposes.
     * @param original the original
     */
    public XmlElement(XmlElement original) {
        super();
        attributes.addAll(original.attributes);
        elements.addAll(original.elements);
        this.name = original.name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public List<VisitableElement> getElements() {
        return elements;
    }

    public void addElement(VisitableElement element) {
        elements.add(element);
    }

    public void addElement(int index, VisitableElement element) {
        elements.add(index, element);
    }

    public String getName() {
        return name;
    }

    public boolean hasChildren() {
        return !elements.isEmpty();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public <R> R accept(ElementVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
