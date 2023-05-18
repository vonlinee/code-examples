package com.panemu.tiwulfx.table.annotation;

import java.lang.annotation.*;

/**
 * the column config of the TableView
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableViewModel {

    /**
     * 列尺寸策略
     * @return 映射到
     * @see javafx.scene.control.TableView#CONSTRAINED_RESIZE_POLICY
     * @see javafx.scene.control.TableView#UNCONSTRAINED_RESIZE_POLICY
     */
    int resizePolicy() default 1;
}