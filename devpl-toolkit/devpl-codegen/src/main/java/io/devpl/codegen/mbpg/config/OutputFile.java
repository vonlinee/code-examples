package io.devpl.codegen.mbpg.config;

/**
 * 输出文件类型
 * 模板命名方式
 */
public enum OutputFile implements TypeEnum<String, OutputFile> {

    ENTITY_KOTLIN("Entity", "", "/templates/entity.kt"),
    ENTITY_JAVA("Entity", "", "/templates/entity.java"),
    SERVICE("Service", "", "/templates/service.java"),
    SERVICE_IMPL("ServiceImpl", "", "/templates/serviceImpl.java"),
    MAPPER("Mapper", "", "/templates/mapper.java"),
    XML("Xml", "", "/templates/mapper.xml"),
    CONTROLLER("Controller", "", "/templates/controller.java"),
    PARENT("Parent", "", null),
    // 自定义文件生成，需要指定模板
    CUSTOM("Custom", "", "");

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

    public String getTemplate() {
        return template;
    }
}
