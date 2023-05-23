package com.panemu.tiwulfx.control.anno;

import java.lang.annotation.*;

/**
 * the column config of the TableView
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableViewColumn {

    /**
     * name of the target column
     * @return name
     */
    String name();

    /**
     * width: min pref max
     * @return pref width
     */
    double[] width() default {-1.0, -1.0, -1.0};

    /**
     * 是否可排序
     * @return 是否可排序
     */
    boolean sortable() default true;

    /**
     * 是否可过滤
     * @return 是否可过滤
     */
    boolean filterable() default true;
}