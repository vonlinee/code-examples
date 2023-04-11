package io.devpl.codegen.mbpg.template;

import io.devpl.codegen.mbpg.config.ContextAware;
import io.devpl.codegen.mbpg.config.PropertyHolder;
import io.devpl.codegen.mbpg.config.Context;
import io.devpl.codegen.mbpg.config.po.TableInfo;

import java.util.Map;

/**
 * 模板数据
 */
public abstract class TemplateArguments extends PropertyHolder implements ContextAware {

    private Context context;

    public abstract Map<String, Object> initialize(TableInfo tableInfo);

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}
