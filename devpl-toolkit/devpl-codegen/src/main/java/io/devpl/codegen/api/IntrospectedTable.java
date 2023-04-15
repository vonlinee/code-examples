package io.devpl.codegen.api;

import io.devpl.codegen.generator.AbstractGenerator;
import io.devpl.codegen.generator.GeneratedFile;
import io.devpl.codegen.generator.template.TemplateBasedGenerator;
import io.devpl.codegen.generator.template.impl.EntityTemplateArguments;
import io.devpl.codegen.mbpg.config.ProjectConfiguration;
import io.devpl.codegen.mbpg.config.StrategyConfig;
import io.devpl.codegen.mbpg.config.rules.DataType;
import io.devpl.codegen.utils.StringUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 表信息，关联到当前字段信息
 */
@Data
public class IntrospectedTable {

    protected Context context;

    protected final List<AbstractGenerator> generators = new ArrayList<>();

    /**
     * 策略配置
     */
    private final StrategyConfig strategyConfig;

    /**
     * 全局配置信息
     */
    private final ProjectConfiguration globalConfig;

    /**
     * 包导入信息
     */
    private final Set<String> importPackages = new TreeSet<>();

    /**
     * 是否转换 表名
     */
    private boolean convert;

    /**
     * 表名称
     */
    private String name;

    /**
     * 表注释
     */
    private String comment;

    /**
     * 实体名称
     */
    private String entityName;

    /**
     * mapper名称
     */
    private String mapperName;

    /**
     * xml名称
     */
    private String xmlName;

    /**
     * service名称
     */
    private String serviceName;

    /**
     * serviceImpl名称
     */
    private String serviceImplName;

    /**
     * controller名称
     */
    private String controllerName;

    /**
     * 表字段
     */
    private final List<TableColumn> fields = new ArrayList<>();

    /**
     * 是否有主键
     */
    private boolean havePrimaryKey;

    /**
     * 公共字段
     */
    private final List<TableColumn> commonFields = new ArrayList<>();

    /**
     * 字段名称集
     */
    private String fieldNames;

    /**
     * 实体
     */
    private final EntityTemplateArguments entity;

    /**
     * 构造方法
     * @param context 配置构建
     * @param name    表名
     * @since 3.5.0
     */
    public IntrospectedTable(Context context, String name) {
        this.strategyConfig = context.getStrategyConfig();
        this.globalConfig = context.getGlobalConfig();
        this.entity = context.getStrategyConfig().entityArguments();
        this.name = name;
    }

    /**
     * @since 3.5.0
     */
    protected IntrospectedTable setConvert() {
        if (strategyConfig.startsWithTablePrefix(name) || entity.isTableFieldAnnotationEnable()) {
            this.convert = true;
        } else {
            this.convert = !entityName.equalsIgnoreCase(name);
        }
        return this;
    }

    /**
     * @param entityName 实体名称
     * @return this
     */
    public IntrospectedTable setEntityName(String entityName) {
        this.entityName = entityName;
        // TODO 先放置在这里
        setConvert();
        return this;
    }

    /**
     * 添加字段
     * @param field 字段
     * @since 3.5.0
     */
    public void addField(TableColumn field) {
        if (entity.matchIgnoreColumns(field.getColumnName())) {
            // 忽略字段不在处理
            return;
        } else if (entity.matchSuperEntityColumns(field.getColumnName())) {
            this.commonFields.add(field);
        } else {
            this.fields.add(field);
        }
    }

    /**
     * 导包处理
     * @since 3.5.0
     */
    public void importPackage() {
        String superEntity = entity.getSuperClass();
        if (StringUtils.isNotBlank(superEntity)) {
            // 自定义父类
            this.importPackages.add(superEntity);
        } else {
            if (entity.isActiveRecord()) {
                // 无父类开启 AR 模式
                this.importPackages.add("com.baomidou.mybatisplus.Mode");
            }
        }
        if (entity.isSerialVersionUID() || entity.isActiveRecord()) {
            this.importPackages.add(Serializable.class.getCanonicalName());
        }
        if (this.isConvert()) {
            this.importPackages.add("com.baomidou.mybatisplus.annotation.TableName");
        }
        if (this.isHavePrimaryKey()) {
            // 指定需要 IdType 场景
            this.importPackages.add("com.baomidou.mybatisplus.annotation.IdType");
            this.importPackages.add("com.baomidou.mybatisplus.annotation.TableId");
        }
        this.fields.forEach(field -> {
            DataType columnType = field.getColumnType();
            if (null != columnType && null != columnType.getQualifiedName()) {
                importPackages.add(columnType.getQualifiedName());
            }
            if (field.isKeyFlag()) {
                // 主键
                if (field.isConvert() || field.isKeyIdentityFlag()) {
                    importPackages.add("com.baomidou.mybatisplus.annotation.TableId");
                }
                // 自增
                if (field.isKeyIdentityFlag()) {
                    importPackages.add("com.baomidou.mybatisplus.annotation.IdType");
                }
            } else if (field.isConvert()) {
                // 普通字段
                importPackages.add("com.baomidou.mybatisplus.annotation.TableField");
            }
            if (null != field.getFill()) {
                // 填充字段
                importPackages.add("com.baomidou.mybatisplus.annotation.TableField");
                // TODO 好像default的不用处理也行,这个做优化项目.
                importPackages.add("com.baomidou.mybatisplus.annotation.FieldFill");
            }
            if (field.isVersionField()) {
                this.importPackages.add("com.baomidou.mybatisplus.annotation.Version");
            }
            if (field.isLogicDeleteField()) {
                this.importPackages.add("com.baomidou.mybatisplus.annotation.TableLogic");
            }
        });
    }

    /**
     * 处理表信息(文件名与导包)
     * @since 3.5.0
     */
    public void processTable() {
        INameConvert nameConvert = entity.getNameConvert();
        if (nameConvert == null) {
            nameConvert = new DefaultNameConvert(strategyConfig);
        }
        String entityName = nameConvert.entityNameConvert(this);
        this.setEntityName("");
        this.mapperName = strategyConfig.mapperArguments().getConverterMapperFileName().convert(entityName);
        this.xmlName = strategyConfig.mapperArguments().getConverterXmlFileName().convert(entityName);
        this.serviceName = strategyConfig.service().getConverterServiceFileName().convert(entityName);
        this.serviceImplName = strategyConfig.service().getConverterServiceImplFileName().convert(entityName);
        this.controllerName = strategyConfig.controllerArguments().getConverterFileName().convert(entityName);
        this.importPackage();
    }

    public IntrospectedTable setComment(String comment) {
        // TODO 暂时挪动到这
        this.comment = this.globalConfig.isSwagger() && StringUtils.isNotBlank(comment) ? comment.replace("\"", "\\\"") : comment;
        return this;
    }

    public IntrospectedTable setHavePrimaryKey(boolean havePrimaryKey) {
        this.havePrimaryKey = havePrimaryKey;
        return this;
    }

    public boolean isConvert() {
        return convert;
    }

    public IntrospectedTable setConvert(boolean convert) {
        this.convert = convert;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getEntityName() {
        return entityName;
    }

    public List<TableColumn> getFields() {
        return fields;
    }

    public boolean isHavePrimaryKey() {
        return havePrimaryKey;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IntrospectedTable{");
        sb.append("strategyConfig=").append(strategyConfig);
        sb.append(", globalConfig=").append(globalConfig);
        sb.append(", importPackages=").append(importPackages);
        sb.append(", convert=").append(convert);
        sb.append(", name='").append(name).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", entityName='").append(entityName).append('\'');
        sb.append(", mapperName='").append(mapperName).append('\'');
        sb.append(", xmlName='").append(xmlName).append('\'');
        sb.append(", serviceName='").append(serviceName).append('\'');
        sb.append(", serviceImplName='").append(serviceImplName).append('\'');
        sb.append(", controllerName='").append(controllerName).append('\'');
        sb.append(", fields=").append(fields);
        sb.append(", havePrimaryKey=").append(havePrimaryKey);
        sb.append(", commonFields=").append(commonFields);
        sb.append(", fieldNames='").append(fieldNames).append('\'');
        sb.append(", entity=").append(entity);
        sb.append('}');
        return sb.toString();
    }

    /**
     * 确定生成的文件
     * @param progressCallback 进度回调
     */
    public List<GeneratedFile> calculateGeneratedFiles(ProgressCallback progressCallback) {
        List<GeneratedFile> generatedFiles = new ArrayList<>();
        for (AbstractGenerator generator : generators) {
            generatedFiles.addAll(generator.calculateGeneratedFiles(context, this));
        }
        return generatedFiles;
    }

    /**
     * 初始化
     */
    public void initialize() {
        generators.add(new TemplateBasedGenerator());
    }
}
