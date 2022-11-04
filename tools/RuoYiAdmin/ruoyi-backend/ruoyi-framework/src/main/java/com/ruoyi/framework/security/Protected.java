package com.ruoyi.framework.security;

import java.lang.annotation.*;

/**
 * 保护方法级别的权限控制
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Protected {

}
