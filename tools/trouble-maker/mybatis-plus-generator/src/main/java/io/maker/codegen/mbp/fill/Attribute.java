package io.maker.codegen.mbp.fill;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Attribute implements Serializable, Comparable<Attribute> {

    private static final long serialVersionUID = 1L;

    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Attribute(int order, String name, String value) {
        this.order = order;
        this.name = name;
        this.value = value;
    }

    private int order;
    private String name;
    private String value;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    /**
     * @param name not null/not empty
     */
    public void setName(String name) {
        if (name == null || name.length() == 0) {
            name = "?";
        }
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Attribute [order=" + order + ", name=" + name + ", value=" + value + "]";
    }

    @Override
    public int compareTo(Attribute o) {
        if (o == null) return 0;
        // 顺序相等时比较名称首字母
        if (o.order == this.order) {
            if (o.name.length() <= 0 || this.name.length() <= 0) {
                return 0;
            }
            char c1 = o.name.charAt(0);
            char c2 = this.name.charAt(0);
            return Character.compare(c1, c2);
        }
        return 0;
    }
}
