package io.devpl.codegen.api;

import io.devpl.codegen.generator.template.impl.EntityTemplateArguments;
import io.devpl.codegen.jdbc.MetaInfo;
import io.devpl.codegen.mbpg.Column;
import io.devpl.codegen.mbpg.PropertyFill;
import io.devpl.codegen.mbpg.config.DataSourceConfig;
import io.devpl.codegen.mbpg.config.IKeyWordsHandler;
import io.devpl.codegen.mbpg.config.ProjectConfiguration;
import io.devpl.codegen.mbpg.config.rules.DataType;
import io.devpl.codegen.mbpg.config.rules.NamingStrategy;
import io.devpl.codegen.utils.StringUtils;

import java.util.Map;

/**
 * 表字段信息
 */
public class TableColumn {

    private boolean convert; // 是否生成@TableField
    private boolean keyFlag;
    /**
     * 主键是否为自增类型
     */
    private boolean keyIdentityFlag;
    private String name;
    private String type;
    private String propertyName;
    private DataType columnType;
    private String comment;
    private String fill;
    /**
     * 是否关键字
     * @since 3.3.2
     */
    private boolean keyWords;
    /**
     * 数据库字段（关键字含转义符号）
     * @since 3.3.2
     */
    private String columnName;
    /**
     * 自定义查询字段列表
     */
    private Map<String, Object> customMap;

    /**
     * 字段元数据信息
     * @since 3.5.0
     */
    private MetaInfo metaInfo;

    private final EntityTemplateArguments entity;

    private final DataSourceConfig dataSourceConfig;

    private final ProjectConfiguration globalConfig;

    /**
     * 构造方法
     * @param context 配置构建
     * @param name          数据库字段名称
     * @since 3.5.0
     */
    public TableColumn(Context context, String name) {
        this.name = name;
        this.columnName = name;
        this.entity = context.getStrategyConfig().entityArguments();
        this.dataSourceConfig = context.getDataSourceConfig();
        this.globalConfig = context.getGlobalConfig();
    }

    /**
     * 设置属性名称
     * @param propertyName 属性名
     * @param columnType   字段类型
     * @return this
     * @since 3.5.0
     */
    public TableColumn setPropertyName(String propertyName, DataType columnType) {
        this.columnType = columnType;
        if (entity.isBooleanColumnRemoveIsPrefix() && "boolean".equalsIgnoreCase(this.getPropertyType()) && propertyName.startsWith("is")) {
            this.convert = true;
            this.propertyName = StringUtils.removePrefixAfterPrefixToLower(propertyName, 2);
            return this;
        }
        // 下划线转驼峰策略
        if (NamingStrategy.UNDERLINE_TO_CAMEL.equals(this.entity.getColumnNaming())) {
            this.convert = !propertyName.equalsIgnoreCase(NamingStrategy.underlineToCamel(this.columnName));
        }
        // 原样输出策略
        if (NamingStrategy.NO_CHANGE.equals(this.entity.getColumnNaming())) {
            this.convert = !propertyName.equalsIgnoreCase(this.columnName);
        }
        if (entity.isTableFieldAnnotationEnable()) {
            this.convert = true;
        }
        this.propertyName = propertyName;
        return this;
    }

    public String getPropertyType() {
        if (null != columnType) {
            return columnType.getType();
        }
        return null;
    }

    /**
     * 按 JavaBean 规则来生成 get 和 set 方法后面的属性名称
     * 需要处理一下特殊情况：
     * <p>
     * 1、如果只有一位，转换为大写形式
     * 2、如果多于 1 位，只有在第二位是小写的情况下，才会把第一位转为小写
     * <p>
     * 我们并不建议在数据库对应的对象中使用基本类型，因此这里不会考虑基本类型的情况
     */
    public String getCapitalName() {
        if (propertyName.length() == 1) {
            return propertyName.toUpperCase();
        }
        if (Character.isLowerCase(propertyName.charAt(1))) {
            return Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
        }
        return propertyName;
    }

    /**
     * 获取注解字段名称
     * @return 字段
     * @since 3.3.2
     */
    public String getAnnotationColumnName() {
        if (keyWords) {
            if (columnName.startsWith("\"")) {
                return String.format("\\\"%s\\\"", name);
            }
        }
        return columnName;
    }

    /**
     * 是否为乐观锁字段
     * @return 是否为乐观锁字段
     * @since 3.5.0
     */
    public boolean isVersionField() {
        String propertyName = entity.getVersionPropertyName();
        String columnName = entity.getVersionColumnName();
        return StringUtils.isNotBlank(propertyName) && this.propertyName.equals(propertyName) || StringUtils.isNotBlank(columnName) && this.name.equalsIgnoreCase(columnName);
    }

    /**
     * 是否为逻辑删除字段
     * @return 是否为逻辑删除字段
     * @since 3.5.0
     */
    public boolean isLogicDeleteField() {
        String propertyName = entity.getLogicDeletePropertyName();
        String columnName = entity.getLogicDeleteColumnName();
        return StringUtils.isNotBlank(propertyName) && this.propertyName.equals(propertyName) || StringUtils.isNotBlank(columnName) && this.name.equalsIgnoreCase(columnName);
    }

    /**
     * 设置主键
     * @param autoIncrement 自增标识
     * @return this
     * @since 3.5.0
     */
    public TableColumn primaryKey(boolean autoIncrement) {
        this.keyFlag = true;
        this.keyIdentityFlag = autoIncrement;
        return this;
    }

    /**
     * @param type 类型
     * @return this
     */
    public TableColumn setType(String type) {
        this.type = type;
        return this;
    }

    public TableColumn setComment(String comment) {
        // TODO 暂时挪动到这
        this.comment = this.globalConfig.isSwagger() && StringUtils.isNotBlank(comment) ? comment.replace("\"", "\\\"") : comment;
        return this;
    }

    public TableColumn setColumnName(String columnName) {
        this.columnName = columnName;
        IKeyWordsHandler keyWordsHandler = dataSourceConfig.getKeyWordsHandler();
        if (keyWordsHandler != null && keyWordsHandler.isKeyWords(columnName)) {
            this.keyWords = true;
            this.columnName = keyWordsHandler.formatColumn(columnName);
        }
        return this;
    }

    public TableColumn setCustomMap(Map<String, Object> customMap) {
        this.customMap = customMap;
        return this;
    }

    public boolean isConvert() {
        return convert;
    }

    public boolean isKeyFlag() {
        return keyFlag;
    }

    public boolean isKeyIdentityFlag() {
        return keyIdentityFlag;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public DataType getColumnType() {
        return columnType;
    }

    public String getComment() {
        return comment;
    }

    public String getFill() {
        if (StringUtils.isBlank(fill)) {
            entity.getTableFillList().stream()
                    // 忽略大写字段问题
                    .filter(tf -> tf instanceof Column && tf.getName()
                            .equalsIgnoreCase(name) || tf instanceof PropertyFill && tf.getName().equals(propertyName))
                    .findFirst().ifPresent(tf -> this.fill = tf.getFieldFill().name());
        }
        return fill;
    }

    public boolean isKeyWords() {
        return keyWords;
    }

    public String getColumnName() {
        return columnName;
    }

    public Map<String, Object> getCustomMap() {
        return customMap;
    }

    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }
}
