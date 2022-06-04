package org.mybatis.generator.codegen;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Context;

public abstract class AbstractGenerator {

    /**
     * 上下文，包含配置选项
     */
    protected Context context;

    /**
     * 表信息
     */
    protected IntrospectedTable introspectedTable;

    /**
     * 警告信息
     */
    protected List<String> warnings;

    /**
     * 进度回调
     */
    protected ProgressCallback progressCallback;

    protected AbstractGenerator() {
        super();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setIntrospectedTable(IntrospectedTable introspectedTable) {
        this.introspectedTable = introspectedTable;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }
}
