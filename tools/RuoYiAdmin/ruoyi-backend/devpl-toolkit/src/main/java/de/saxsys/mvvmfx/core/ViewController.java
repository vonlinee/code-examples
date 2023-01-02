package de.saxsys.mvvmfx.core;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ViewController {

    /**
     * View对应的ViewModel类型，不通过泛型指定
     * @return ViewModel Class
     */
    Class<? extends ViewModel> viewModel();
}
