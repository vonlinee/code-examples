package io.devpl.codegen.core.meta.generator;

import io.devpl.codegen.core.meta.GeneratedFile;
import io.devpl.codegen.core.meta.core.Context;

import java.util.List;

/**
 * 将数据库表的信息处理形成要生成的文件信息 {@code GeneratedFile}
 */
public abstract class AbstractTableIntrospector implements DatabaseTableIntrospector<List<GeneratedFile>> {

    protected Context context;
    protected List<String> warnings;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
}
