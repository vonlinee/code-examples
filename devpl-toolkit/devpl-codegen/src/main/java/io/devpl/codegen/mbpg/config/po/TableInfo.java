package io.devpl.codegen.mbpg.config.po;

import com.baomidou.mybatisplus.annotation.*;
// import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.devpl.codegen.mbpg.config.ProjectConfiguration;
import io.devpl.codegen.mbpg.config.StrategyConfig;
import io.devpl.codegen.mbpg.config.Context;
import io.devpl.codegen.mbpg.template.impl.EntityTemplateArguments;
import io.devpl.codegen.mbpg.config.rules.DataType;
import io.devpl.codegen.mbpg.util.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 表信息，关联到当前字段信息
 *
 * @author YangHu, lanjerry
 * @since 2016/8/30
 */
public class TableInfo {

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
     * 是否转换
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
    private final List<TableField> fields = new ArrayList<>();

    /**
     * 是否有主键
     */
    private boolean havePrimaryKey;

    /**
     * 公共字段
     */
    private final List<TableField> commonFields = new ArrayList<>();

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
     *
     * @param context 配置构建
     * @param name    表名
     * @since 3.5.0
     */
    public TableInfo(@NotNull Context context, @NotNull String name) {
        this.strategyConfig = context.getStrategyConfig();
        this.globalConfig = context.getGlobalConfig();
        this.entity = context.getStrategyConfig().entity();
        this.name = name;
    }

    /**
     * @since 3.5.0
     */
    protected TableInfo setConvert() {
        if (strategyConfig.startsWithTablePrefix(name) || entity.isTableFieldAnnotationEnable()) {
            this.convert = true;
        } else {
            this.convert = !entityName.equalsIgnoreCase(name);
        }
        return this;
    }

    public String getEntityPath() {
        return entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
    }

    /**
     * @param entityName 实体名称
     * @return this
     */
    public TableInfo setEntityName(@NotNull String entityName) {
        this.entityName = entityName;
        // TODO 先放置在这里
        setConvert();
        return this;
    }

    /**
     * 添加字段
     *
     * @param field 字段
     * @since 3.5.0
     */
    public void addField(@NotNull TableField field) {
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
     * @param pkgs 包空间
     * @return this
     * @since 3.5.0
     */
    public TableInfo addImportPackages(@NotNull String... pkgs) {
        return addImportPackages(Arrays.asList(pkgs));
    }

    public TableInfo addImportPackages(@NotNull List<String> pkgList) {
        importPackages.addAll(pkgList);
        return this;
    }

    /**
     * 转换filed实体为 xml mapper 中的 base column 字符串信息
     */
    public String getFieldNames() {
        // TODO 感觉这个也啥必要,不打算公开set方法了
        if (StringUtils.isBlank(fieldNames)) {
            this.fieldNames = this.fields.stream().map(TableField::getColumnName).collect(Collectors.joining(", "));
        }
        return this.fieldNames;
    }

    /**
     * 导包处理
     *
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
                // this.importPackages.add(Model.class.getCanonicalName());
            }
        }
        if (entity.isSerialVersionUID() || entity.isActiveRecord()) {
            this.importPackages.add(Serializable.class.getCanonicalName());
        }
        if (this.isConvert()) {
            this.importPackages.add(TableName.class.getCanonicalName());
        }
        IdType idType = entity.getIdType();
        if (null != idType && this.isHavePrimaryKey()) {
            // 指定需要 IdType 场景
            this.importPackages.add(IdType.class.getCanonicalName());
            this.importPackages.add(TableId.class.getCanonicalName());
        }
        this.fields.forEach(field -> {
            DataType columnType = field.getColumnType();
            if (null != columnType && null != columnType.getQualifiedName()) {
                importPackages.add(columnType.getQualifiedName());
            }
            if (field.isKeyFlag()) {
                // 主键
                if (field.isConvert() || field.isKeyIdentityFlag()) {
                    importPackages.add(TableId.class.getCanonicalName());
                }
                // 自增
                if (field.isKeyIdentityFlag()) {
                    importPackages.add(IdType.class.getCanonicalName());
                }
            } else if (field.isConvert()) {
                // 普通字段
                importPackages.add(com.baomidou.mybatisplus.annotation.TableField.class.getCanonicalName());
            }
            if (null != field.getFill()) {
                // 填充字段
                importPackages.add(com.baomidou.mybatisplus.annotation.TableField.class.getCanonicalName());
                // TODO 好像default的不用处理也行,这个做优化项目.
                importPackages.add(FieldFill.class.getCanonicalName());
            }
            if (field.isVersionField()) {
                this.importPackages.add(Version.class.getCanonicalName());
            }
            if (field.isLogicDeleteField()) {
                this.importPackages.add(TableLogic.class.getCanonicalName());
            }
        });
    }

    /**
     * 处理表信息(文件名与导包)
     *
     * @since 3.5.0
     */
    public void processTable() {
        String entityName = entity.getNameConvert().entityNameConvert(this);
        this.setEntityName(entity.getConverterFileName().convert(entityName));
        this.mapperName = strategyConfig.mapper().getConverterMapperFileName().convert(entityName);
        this.xmlName = strategyConfig.mapper().getConverterXmlFileName().convert(entityName);
        this.serviceName = strategyConfig.service().getConverterServiceFileName().convert(entityName);
        this.serviceImplName = strategyConfig.service().getConverterServiceImplFileName().convert(entityName);
        this.controllerName = strategyConfig.controller().getConverterFileName().convert(entityName);
        this.importPackage();
    }

    public TableInfo setComment(String comment) {
        // TODO 暂时挪动到这
        this.comment = this.globalConfig.isSwagger() && StringUtils.isNotBlank(comment) ? comment.replace("\"", "\\\"") : comment;
        return this;
    }

    public TableInfo setHavePrimaryKey(boolean havePrimaryKey) {
        this.havePrimaryKey = havePrimaryKey;
        return this;
    }

    @NotNull
    public Set<String> getImportPackages() {
        return importPackages;
    }

    public boolean isConvert() {
        return convert;
    }

    public TableInfo setConvert(boolean convert) {
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

    public String getMapperName() {
        return mapperName;
    }

    public String getXmlName() {
        return xmlName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceImplName() {
        return serviceImplName;
    }

    public String getControllerName() {
        return controllerName;
    }

    @NotNull
    public List<TableField> getFields() {
        return fields;
    }

    public boolean isHavePrimaryKey() {
        return havePrimaryKey;
    }

    @NotNull
    public List<TableField> getCommonFields() {
        return commonFields;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TableInfo{");
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
}
