package io.devpl.toolkit.codegen;

import io.devpl.toolkit.mbp.NameConverter;

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
