package utils;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(value = RetentionPolicy.SOURCE)
public @interface GenericParam {

    Class<?> value();
}
