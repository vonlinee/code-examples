package io.devpl.codegen.mbpg.config;

public enum OutputFile implements TypeEnum<String, OutputFile> {

    ENTITY(ConstVal.ENTITY, ""),
    SERVICE(ConstVal.SERVICE, ""),
    SERVICE_IMPL(ConstVal.SERVICE_IMPL, ""),
    MAPPER(ConstVal.MAPPER, ""),
    XML(ConstVal.XML, ""),
    CONTROLLER(ConstVal.CONTROLLER, ""),
    /**
     * 已弃用，已重构自定义文件生成，3.5.4版本会删除
     */
    @Deprecated other("OTHER", ""),
    PARENT(ConstVal.PARENT, "");

    private final String type;
    private final String typeName;

    OutputFile(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public OutputFile getEnum() {
        return this;
    }
}
