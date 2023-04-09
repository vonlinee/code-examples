package io.devpl.codegen.mbpg.config;

import io.devpl.codegen.mbpg.config.rules.DateTimeType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;

/**
 * 全局配置
 * @author hubin
 * @since 2016-12-02
 */
public class GlobalConfig {

    private GlobalConfig() {
    }

    protected static final Logger LOGGER = LoggerFactory.getLogger(GlobalConfig.class);

    /**
     * 生成文件的输出目录【 windows:D://  linux or mac:/tmp 】
     */
    private String outputDir = System.getProperty("os.name").toLowerCase().contains("windows") ? "D://" : "/tmp";

    /**
     * 默认输出目录
     */
    private String defaultOutputRootDir;

    /**
     * 是否覆盖已有文件（默认 false）（已迁移到策略配置中，3.5.4版本会删除此方法）
     */
    @Deprecated
    private boolean fileOverride;

    /**
     * 是否打开输出目录
     */
    private boolean openOutputDir = true;

    /**
     * 作者
     */
    private String author = "baomidou";

    /**
     * 开启 Kotlin 模式（默认 false）
     */
    private boolean kotlin;

    /**
     * 开启 swagger 模式（默认 false 与 springdoc 不可同时使用）
     */
    private boolean swagger;

    /**
     * 开启 springdoc 模式（默认 false 与 swagger 不可同时使用）
     */
    private boolean springdoc;

    /**
     * 时间类型对应策略
     */
    private DateTimeType dateType = DateTimeType.TIME_PACK;

    /**
     * 获取注释日期
     * @since 3.5.0
     */
    private Supplier<String> commentDate = () -> new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public String getOutputDir() {
        return outputDir;
    }

    /**
     * 是否覆盖已有文件（已迁移到策略配置中，3.5.4版本会删除此方法）
     */
    @Deprecated
    public boolean isFileOverride() {
        return fileOverride;
    }

    public boolean isOpenOutputDir() {
        return openOutputDir;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isKotlin() {
        return kotlin;
    }

    public boolean isSwagger() {
        // springdoc 设置优先于 swagger
        return !springdoc && swagger;
    }

    public boolean isSpringdoc() {
        return springdoc;
    }

    @NotNull
    public DateTimeType getDateType() {
        return dateType;
    }

    @NotNull
    public String getCommentDate() {
        return commentDate.get();
    }

    /**
     * 全局配置构建
     */
    public static class Builder {

        private final GlobalConfig globalConfig;

        public Builder() {
            this.globalConfig = new GlobalConfig();
        }

        /**
         * 覆盖已有文件（已迁移到策略配置中，3.5.4版本会删除此方法）
         */
        public Builder fileOverride() {
            LOGGER.warn("全局覆盖已有文件的配置已失效，已迁移到策略配置中");
            this.globalConfig.fileOverride = true;
            return this;
        }

        /**
         * 禁止打开输出目录
         */
        public Builder disableOpenDir() {
            this.globalConfig.openOutputDir = false;
            return this;
        }

        /**
         * 输出目录
         */
        public Builder outputDir(@NotNull String outputDir) {
            this.globalConfig.outputDir = outputDir;
            return this;
        }

        /**
         * 作者
         */
        public Builder author(@NotNull String author) {
            this.globalConfig.author = author;
            return this;
        }

        /**
         * 开启 kotlin 模式
         */
        public Builder enableKotlin() {
            this.globalConfig.kotlin = true;
            return this;
        }

        /**
         * 开启 swagger 模式
         */
        public Builder enableSwagger() {
            this.globalConfig.swagger = true;
            return this;
        }

        /**
         * 开启 springdoc 模式
         */
        public Builder enableSpringdoc() {
            this.globalConfig.springdoc = true;
            return this;
        }

        /**
         * 时间类型对应策略
         */
        public Builder dateType(@NotNull DateTimeType dateType) {
            this.globalConfig.dateType = dateType;
            return this;
        }

        /**
         * 注释日期获取处理
         * example: () -> LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
         * @param commentDate 获取注释日期
         * @return this
         * @since 3.5.0
         */
        public Builder commentDate(@NotNull Supplier<String> commentDate) {
            this.globalConfig.commentDate = commentDate;
            return this;
        }

        /**
         * 指定注释日期格式化
         * @param pattern 格式
         * @return this
         * @since 3.5.0
         */
        public Builder commentDate(@NotNull String pattern) {
            return commentDate(() -> new SimpleDateFormat(pattern).format(new Date()));
        }

        public GlobalConfig build() {
            return this.globalConfig;
        }
    }
}
