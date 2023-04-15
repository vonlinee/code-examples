package io.devpl.codegen.api.gen;

import io.devpl.codegen.api.Context;
import io.devpl.codegen.api.IntrospectedTable;

import java.util.List;

/**
 * 生成器
 * 1.决定生成哪些文件
 * 2.生成文件
 */
public abstract class AbstractGenerator {

    /**
     * 确定生成哪些文件
     * @param context 上下文
     * @param table   表信息
     * @return 生成的文件
     */
    public abstract List<GeneratedFile> calculateGeneratedFiles(Context context, IntrospectedTable table);
}
