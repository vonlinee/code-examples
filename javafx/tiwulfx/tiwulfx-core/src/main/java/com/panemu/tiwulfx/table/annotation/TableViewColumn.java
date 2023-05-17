package com.panemu.tiwulfx.table.annotation;

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
     * max height
     * @return max height
     */
    double maxHeight() default -1.0;

    /**
     * pref width
     * @return pref width
     */
    double prefWidth() default -1.0;
}