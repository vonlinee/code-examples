package io.devpl.codegen.core.meta.xml;

/**
 * 区别于MyBatis的XML文档和普通的XML文档，做一些定制化的功能
 */
public class MyBatisXmlElement extends XmlElement {

    /**
     * 子标签之间是否添加空行，一般只有第二层标签添加空行，即<insert> <update> <delete> <select>
     */
    private boolean blankBetweenChildren = false;

    /**
     * 注释
     */
    private String comment;

    /**
     * 是否有注释
     */
    private boolean hasComment;

    public MyBatisXmlElement(String name) {
        super(name);
    }

    /**
     * Copy constructor. Not a truly deep copy, but close enough for most purposes.
     * @param original the original
     */
    public MyBatisXmlElement(XmlElement original) {
        super(original);
    }

    public boolean isBlankBetweenChildren() {
        return blankBetweenChildren;
    }

    public void setBlankBetweenChildren(boolean blankBetweenChildren) {
        this.blankBetweenChildren = blankBetweenChildren;
    }
}
