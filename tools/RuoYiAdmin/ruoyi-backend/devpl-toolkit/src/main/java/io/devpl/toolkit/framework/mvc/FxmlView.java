package io.devpl.toolkit.framework.mvc;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FxmlView {

    /**
     * FXML路径
     * @return FXML路径
     */
    String location() default "";

    Class<? extends ViewModel> vm() default ViewModel.class;
}
