package io.devpl.codegen.api;

import io.devpl.codegen.api.gen.AbstractGenerator;
import io.devpl.codegen.api.gen.GeneratedFile;
import io.devpl.codegen.api.gen.template.TemplateBasedGenerator;
import io.devpl.codegen.api.gen.template.impl.EntityTemplateArguments;
import io.devpl.codegen.jdbc.meta.TableMetadata;
import io.devpl.codegen.utils.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 表信息，关联到当前字段信息
 */
public class IntrospectedTable {

    protected Context context;

    private TableMetadata metadata;

    protected final List<AbstractGenerator> generators = new ArrayList<>();

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
    private final List<IntrospectedColumn> columns = new ArrayList<>();

    /**
     * 是否有主键
     */
    private boolean havePrimaryKey;

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
        this.entity = context.getStrategyConfig().entityArguments();
        this.name = name;
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
        this.importPackages.add("com.baomidou.mybatisplus.annotation.TableName");
        if (this.isHavePrimaryKey()) {
            // 指定需要 IdType 场景
            this.importPackages.add("com.baomidou.mybatisplus.annotation.IdType");
            this.importPackages.add("com.baomidou.mybatisplus.annotation.TableId");
        }
        this.columns.forEach(field -> {
            if (field.isPrimaryKey()) {
                // 主键
                if (field.isAutoIncrement()) {
                    importPackages.add("com.baomidou.mybatisplus.annotation.TableId");
                    importPackages.add("com.baomidou.mybatisplus.annotation.IdType");
                }
            } else {
                // 普通字段
                importPackages.add("com.baomidou.mybatisplus.annotation.TableField");
            }

            // 填充字段
            importPackages.add("com.baomidou.mybatisplus.annotation.TableField");
            // TODO 好像default的不用处理也行,这个做优化项目.
            importPackages.add("com.baomidou.mybatisplus.annotation.FieldFill");
            if (field.isVersionField()) {
                this.importPackages.add("com.baomidou.mybatisplus.annotation.Version");
            }
            if (field.isLogicDeleteField()) {
                this.importPackages.add("com.baomidou.mybatisplus.annotation.TableLogic");
            }
        });
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

    public List<IntrospectedColumn> getColumns() {
        return columns;
    }

    public boolean isHavePrimaryKey() {
        return havePrimaryKey;
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
