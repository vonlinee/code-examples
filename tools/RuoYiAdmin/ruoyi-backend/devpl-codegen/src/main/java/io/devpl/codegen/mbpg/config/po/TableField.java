package io.devpl.codegen.mbpg.config.po;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.devpl.codegen.mbpg.config.DataSourceConfig;
import io.devpl.codegen.mbpg.config.GlobalConfig;
import io.devpl.codegen.mbpg.config.IKeyWordsHandler;
import io.devpl.codegen.mbpg.config.builder.CodeGenConfiguration;
import io.devpl.codegen.mbpg.config.builder.Entity;
import io.devpl.codegen.mbpg.config.rules.IColumnType;
import io.devpl.codegen.mbpg.config.rules.NamingStrategyEnum;
import io.devpl.codegen.mbpg.fill.Column;
import io.devpl.codegen.mbpg.fill.Property;
import io.devpl.codegen.mbpg.jdbc.DatabaseMetaDataWrapper;
import io.devpl.codegen.mbpg.jdbc.JdbcType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * 表字段信息
 */
public class TableField {

    private boolean convert; // 是否生成@TableField
    private boolean keyFlag;
    /**
     * 主键是否为自增类型
     */
    private boolean keyIdentityFlag;
    private String name;
    private String type;
    private String propertyName;
    private IColumnType columnType;
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

    private final Entity entity;

    private final DataSourceConfig dataSourceConfig;

    private final GlobalConfig globalConfig;

    /**
     * 构造方法
     * @param configBuilder 配置构建
     * @param name          数据库字段名称
     * @since 3.5.0
     */
    public TableField(@NotNull CodeGenConfiguration configBuilder, @NotNull String name) {
        this.name = name;
        this.columnName = name;
        this.entity = configBuilder.getStrategyConfig().entity();
        this.dataSourceConfig = configBuilder.getDataSourceConfig();
        this.globalConfig = configBuilder.getGlobalConfig();
    }

    /**
     * 设置属性名称
     * @param propertyName 属性名
     * @param columnType   字段类型
     * @return this
     * @since 3.5.0
     */
    public TableField setPropertyName(@NotNull String propertyName, @NotNull IColumnType columnType) {
        this.columnType = columnType;
        if (entity.isBooleanColumnRemoveIsPrefix() && "boolean".equalsIgnoreCase(this.getPropertyType()) && propertyName.startsWith("is")) {
            this.convert = true;
            this.propertyName = StringUtils.removePrefixAfterPrefixToLower(propertyName, 2);
            return this;
        }
        // 下划线转驼峰策略
        if (NamingStrategyEnum.UNDERLINE_TO_CAMEL.equals(this.entity.getColumnNaming())) {
            this.convert = !propertyName.equalsIgnoreCase(NamingStrategyEnum.underlineToCamel(this.columnName));
        }
        // 原样输出策略
        if (NamingStrategyEnum.NO_CHANGE.equals(this.entity.getColumnNaming())) {
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
    public TableField primaryKey(boolean autoIncrement) {
        this.keyFlag = true;
        this.keyIdentityFlag = autoIncrement;
        return this;
    }

    /**
     * @param type 类型
     * @return this
     */
    public TableField setType(String type) {
        this.type = type;
        return this;
    }

    public TableField setComment(String comment) {
        // TODO 暂时挪动到这
        this.comment = this.globalConfig.isSwagger() && StringUtils.isNotBlank(comment) ? comment.replace("\"", "\\\"") : comment;
        return this;
    }

    public TableField setColumnName(String columnName) {
        this.columnName = columnName;
        IKeyWordsHandler keyWordsHandler = dataSourceConfig.getKeyWordsHandler();
        if (keyWordsHandler != null && keyWordsHandler.isKeyWords(columnName)) {
            this.keyWords = true;
            this.columnName = keyWordsHandler.formatColumn(columnName);
        }
        return this;
    }

    public TableField setCustomMap(Map<String, Object> customMap) {
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

    public IColumnType getColumnType() {
        return columnType;
    }

    public String getComment() {
        return comment;
    }

    public String getFill() {
        if (StringUtils.isBlank(fill)) {
            entity
                    .getTableFillList()
                    .stream()
                    // 忽略大写字段问题
                    .filter(tf -> tf instanceof Column && tf
                            .getName()
                            .equalsIgnoreCase(name) || tf instanceof Property && tf.getName().equals(propertyName))
                    .findFirst()
                    .ifPresent(tf -> this.fill = tf.getFieldFill().name());
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

    /**
     * 元数据信息
     * @author nieqiurong 2021/2/8
     * @since 3.5.0
     */
    public static class MetaInfo {
        private String name;

        private int length;

        private boolean nullable;

        private String remarks;

        private String defaultValue;

        private int scale;

        private JdbcType jdbcType;

        public MetaInfo(DatabaseMetaDataWrapper.Column column) {
            if (column != null) {
                this.name = column.getName();
                this.length = column.getLength();
                this.nullable = column.isNullable();
                this.remarks = column.getRemarks();
                this.defaultValue = column.getDefaultValue();
                this.scale = column.getScale();
                this.jdbcType = column.getJdbcType();
            }
        }

        public int getLength() {
            return length;
        }

        public boolean isNullable() {
            return nullable;
        }

        public String getRemarks() {
            return remarks;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public int getScale() {
            return scale;
        }

        public JdbcType getJdbcType() {
            return jdbcType;
        }

        @Override
        public String toString() {
            return "MetaInfo{" + "length=" + length + ", nullable=" + nullable + ", remarks='" + remarks + '\'' + ", defaultValue='" + defaultValue + '\'' + ", scale=" + scale + ", jdbcType=" + jdbcType + '}';
        }
    }
}