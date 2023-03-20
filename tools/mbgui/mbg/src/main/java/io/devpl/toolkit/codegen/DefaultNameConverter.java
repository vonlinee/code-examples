package io.devpl.toolkit.codegen;

import io.devpl.toolkit.mbp.NameConverter;

/**
 * @since created on 2022年8月5日
 */
public class DefaultNameConverter implements NameConverter {
    /**
     * 自定义Service类文件的名称规则
     */
    @Override
    public String serviceNameConvert(String tableName) {
        return this.entityNameConvert(tableName) + "Service";
    }

    /**
     * 自定义Controller类文件的名称规则
     */
    @Override
    public String controllerNameConvert(String tableName) {
        return this.entityNameConvert(tableName) + "Action";
    }
}
