package io.devpl.codegen.mbpg.template;

import java.lang.annotation.*;

/**
 * 模板实体类
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface TemplateEntity {

    /**
     * 模板路径
     *
     * @return 模板名称
     */
    String template();
}
