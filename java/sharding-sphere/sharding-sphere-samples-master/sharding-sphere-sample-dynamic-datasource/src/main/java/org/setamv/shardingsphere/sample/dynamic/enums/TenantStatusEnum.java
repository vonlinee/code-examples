package org.setamv.shardingsphere.sample.dynamic.enums;

import lombok.Getter;

/**
 * 租户状态枚举。1-启用；2-停用；
 *
 * @author setamv
 * @date 2021-04-15
 */
@Getter
public enum TenantStatusEnum implements NameValueEnum {

    /**
     * 启动状态
     */
    ENABLED((short)1, "启用"),

    /**
     * 停用状态
     */
    DISABLED((short)1, "停用");

    private short value;
    private String name;

    TenantStatusEnum(short value, String name) {
        this.value = value;
        this.name = name;
    }
}
