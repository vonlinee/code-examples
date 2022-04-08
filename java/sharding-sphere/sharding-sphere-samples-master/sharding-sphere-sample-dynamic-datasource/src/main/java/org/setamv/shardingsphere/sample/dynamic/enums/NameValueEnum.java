package org.setamv.shardingsphere.sample.dynamic.enums;

/**
 * 名称、值对的枚举接口
 *
 * @author setamv
 * @date 2021-04-15
 */
public interface NameValueEnum {

    /**
     * 获取枚举值
     * @return 枚举值
     */
    short getValue();

    /**
     * 获取枚举名称
     * @return
     */
    String getName();
}
