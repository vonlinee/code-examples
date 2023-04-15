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
 * 表信息
 */
public class IntrospectedTable {

    protected Context context;
    private TableMetadata metadata;

    protected final List<AbstractGenerator> generators = new ArrayList<>();

    /**
     * 表字段
     */
    private final List<IntrospectedColumn> columns = new ArrayList<>();

    /**
     * 构造方法
     * @param context 配置构建
     * @param tbmd    表名
     * @since 3.5.0
     */
    public IntrospectedTable(Context context, TableMetadata tbmd) {
        this.context = context;
        this.metadata = tbmd;
    }

    public String getName() {
        return metadata.getTableName();
    }

    public List<IntrospectedColumn> getColumns() {
        return columns;
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

    public void setContext(Context context) {
        this.context = context;
    }
}
