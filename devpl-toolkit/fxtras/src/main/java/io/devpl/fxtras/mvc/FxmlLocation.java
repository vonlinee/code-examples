package io.devpl.fxtras.mvc;

import java.lang.annotation.*;

/**
 * 用于绑定FxmlView和FXML文件信息
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FxmlLocation {

    /**
     * FXML路径
     * @return FXML相对路径
     */
    String location() default "";

    /**
     * 绑定到某个Stage时的标题
     * @return 标题
     */
    String title() default "";

    Class<? extends ViewModel> vm() default ViewModel.class;
}
