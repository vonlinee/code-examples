package io.devpl.toolkit.fxui.framework.core;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface FXMLView {

    String fxmlLocation();
}