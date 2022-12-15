package io.devpl.codegen.mbpg.config;

public enum OutputFile {
    entity, service, serviceImpl, mapper, xml, controller,
    /**
     * 已弃用，已重构自定义文件生成，3.5.4版本会删除
     */
    @Deprecated other, parent;
}
