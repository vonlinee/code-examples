package io.devpl.codegen.mbpg.config;

/**
 * 输出文件类型
 */
public enum OutputFile implements TypeEnum<String, OutputFile> {

    ENTITY("Entity", ""),
    SERVICE("Service", ""),
    SERVICE_IMPL("ServiceImpl", ""),
    MAPPER("Mapper", ""),
    XML("Xml", ""),
    CONTROLLER("Controller", ""),
    PARENT("Parent", ""),
    CUSTOM("Custom", ""); // 自定义文件生成

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
