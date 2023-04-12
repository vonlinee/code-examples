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

    // 自定义文件生成，需要指定模板
    CUSTOM("Custom", "");

    private final String type;
    private final String typeName;

    /**
     * 模板路径，可在运行时进行修改
     */
    private String template;

    OutputFile(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    OutputFile(String type, String typeName, String template) {
        this.type = type;
        this.typeName = typeName;
        this.template = template;
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
